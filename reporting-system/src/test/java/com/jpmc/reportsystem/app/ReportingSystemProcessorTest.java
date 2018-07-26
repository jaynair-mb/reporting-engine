package com.jpmc.reportsystem.app;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpmc.reportsystem.service.CalculationServiceImpl;
import com.jpmc.reportsystem.service.DataManipulationServiceImpl;
import com.jpmc.reportsystem.service.RankingServiceImpl;
import com.jpmc.reportsystem.service.reader.CSVDataReader;
import com.jpmc.reportsystem.service.reader.DataReader;
import com.jpmc.reportsystem.service.writer.ConsoleDataWriter;

/**
 * tests for ReportingSystemProcessor as most of the individual components have
 * well written test cases it would not be having any major tests.
 * 
 * ReportingSystemProcessor is just an orchestration layer, where it would only
 * be calling the required dependencies and has no business logic
 * 
 * @author jnair1
 *
 */
public class ReportingSystemProcessorTest {

	private ReportingSystemProcessor processor;
	private DataReader dataReader;

	/**
	 * Initialize the processor with all the dependent objects. Ideally there
	 * should be a factory responsible for loading the appropriate
	 * implementation instead of manually creating the object
	 * 
	 * @throws IOException
	 */
	@Before
	public void setup() throws IOException {
		// csv implementation
		dataReader = new CSVDataReader();
		processor = new ReportingSystemProcessor(new CalculationServiceImpl(), new RankingServiceImpl(),
				new DataManipulationServiceImpl(), dataReader, new ConsoleDataWriter());
	}

	/**
	 * processing would fail as there is no connection for the processor to
	 * process
	 * 
	 * @throws Exception
	 */
	@Test
	public void process_WithoutConnection() {
		String status = processor.process();
		Assert.assertEquals("FAILURE", status);
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void  process_FAIL() {
		ReportingSystemProcessor processor = new ReportingSystemProcessor(null, null, null, null, null);
		Assert.assertEquals("FAILURE", processor.process());
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void  process_closeDataReader_DuringException() throws Exception {
		ReportingSystemProcessor processor = new ReportingSystemProcessor(null, null, null, dataReader, null);
		boolean isConnectionSuccess = dataReader.addConnectionDetails("src/main/resources", "client-instructions.csv");
		Assert.assertTrue(isConnectionSuccess);
		Assert.assertEquals("FAILURE", processor.process());
	}
	
	/**
	 * tests for processing with all valid data
	 * 
	 * @throws Exception
	 */
	@Test
	public void process_WithConnection() throws Exception {
		boolean isConnectionSuccess = dataReader.addConnectionDetails("src/main/resources", "client-instructions.csv");
		Assert.assertTrue(isConnectionSuccess);
		Assert.assertTrue(dataReader.isConnectedEstablished());

		processor.process();
	}
	
	
	

}
