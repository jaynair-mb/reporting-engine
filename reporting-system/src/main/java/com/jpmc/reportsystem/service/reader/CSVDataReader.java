package com.jpmc.reportsystem.service.reader;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;
import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.model.Indicator;
import com.jpmc.reportsystem.util.ReportingSystemConstants;
import com.jpmc.reportsystem.util.ReportingSystemResourceUtil;

/**
 * Reads csv file from the specified location using {@code BufferedReader}
 * 
 * <p>
 * This class provides an efficient way of reading the data as it will not read
 * the complete set, instead in batches. This will boost the performance of the
 * application as less data would be store in-memory
 * 
 * <p>
 * Also transforms the read data into the {@code ClientInstructions}, so the
 * calling application has to only deal with the set of instructions, without
 * worrying about the underlying implementation
 * 
 * @author jnair1
 *
 */
public class CSVDataReader implements DataReader {

	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(CSVDataReader.class);

	/**
	 * reader flag to indicate whether the reader is done with the reading
	 * operation or not
	 */
	private boolean isDone = false;

	/**
	 * number of lines that needs to be processed at a single time. This is read
	 * from properties file, making the decision configurable
	 */
	private int batchFileCount = Integer
			.parseInt(ReportingSystemResourceUtil.getValue(ReportingSystemConstants.DATA_CHUNK_SIZE));

	private BufferedReader source;

	/**
	 * method to add a new connection with the url and the resource name This
	 * would throw exception in case of invalid input. In case of valid data,
	 * this would create the source for further processing
	 * 
	 * 
	 * @param url
	 *            - url to the resource where it is located
	 * @param resourceName
	 *            - name of the resource that needs to be connected to
	 * @return addConnection status
	 */
	@Override
	public boolean addConnectionDetails(String url, String resourceName) throws Exception {
		LOGGER.debug("adding connection with url={} and resourceName={}", url, resourceName);
		
		if (url == null || url.isEmpty() || resourceName == null || resourceName.isEmpty())
			throw new ReportingSystemException(
					ReportingSystemResourceUtil.getValue(ReportingSystemConstants.EXCEPTION_READER_INVALID_INPUT));

		Path path = Paths.get(url, resourceName);
		source = new BufferedReader(Files.newBufferedReader(path, Charset.forName("UTF-8")));
		
		LOGGER.debug("connection successfully added with url={} and resourceName={}", url, resourceName);
		return true;
	}

	/**
	 * this is to check the status of the connection that was established.
	 */
	public boolean isConnectedEstablished() throws Exception {
		if (null == source)
			return false;
		return true;
	}

	/**
	 * read the data from the file based on the batch count specified. Once done
	 * it would parse and convert the data into List of
	 * {@code ClientInstructions}
	 * 
	 * @return instructions- list of parsed instructions
	 * @throws Exception
	 */
	@Override
	public List<ClientInstructions> read() throws Exception {
		LOGGER.debug("reading in progress, is connectionAvailable={}", isConnectedEstablished());
		
		// data processing should not happen if the connection is not valid
		if (!isConnectedEstablished())
			throw new ReportingSystemException(
					ReportingSystemResourceUtil.getValue(ReportingSystemConstants.EXCEPTION_READER_INVALID_CONNECTION));

		List<ClientInstructions> instructions = new ArrayList<ClientInstructions>();

		// limiting the read to the value configured within properties file
		instructions = source.lines().skip(0).limit(batchFileCount).map(line -> populateClientInstructions(line))
				.collect(Collectors.toList());

		// stream would return a blank list if it has reached the end of
		// processing.manually checking and populating the done flag and closing
		// the resource
		if (instructions.isEmpty()) {
			LOGGER.debug("Reader reached the end of file, no more data to read. is EoF={} and connectionClosed={} ", isDone(), !isConnectedEstablished());
			close();
		}

		return instructions;
	}

	/**
	 * indicates whether the reader has reached the end of processing or not
	 */
	@Override
	public boolean isDone() {
		return isDone;
	}

	/**
	 * close the resource for memory utilization. Currently there is no explicit
	 * call required, reader is closing it own its own, once there is nothing
	 * more to be read
	 * 
	 * @throws Exception 
	 */
	@Override
	public void close() throws Exception {
		LOGGER.debug("Closing the connection");
		setDone(Boolean.TRUE);
		if(isConnectedEstablished())
			source.close();
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	/**
	 * Method to convert comma separated String in to ClientInstructions
	 * 
	 * This needs to be moved outside this class and has to part of a utility
	 * class
	 * 
	 * @param lineRead
	 * @return
	 */
	private ClientInstructions populateClientInstructions(String lineRead) {
		LOGGER.debug("parsing the read data to pojo");
		
		String[] rawInstructions = lineRead.split(",");

		ClientInstructions instruction = new ClientInstructions(
				// entity
				rawInstructions[0],
				// indicator
				Indicator.valueOf(rawInstructions[1].toUpperCase()),
				// agreedFx
				BigDecimal.valueOf(Double.valueOf(rawInstructions[2])),
				// unitPrice
				BigDecimal.valueOf(Double.valueOf(rawInstructions[7])),
				// units
				BigInteger.valueOf(Integer.valueOf(rawInstructions[6])),
				// currency
				Currency.getInstance(rawInstructions[3]),
				// instructionDate
				LocalDate.parse(rawInstructions[4],DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				// settlementDate
				LocalDate.parse(rawInstructions[5],DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		return instruction;
	}

}
