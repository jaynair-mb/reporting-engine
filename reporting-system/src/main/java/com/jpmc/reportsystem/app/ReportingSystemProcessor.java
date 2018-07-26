package com.jpmc.reportsystem.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;
import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.model.Indicator;
import com.jpmc.reportsystem.service.CalculationService;
import com.jpmc.reportsystem.service.DataManipulationService;
import com.jpmc.reportsystem.service.RankingService;
import com.jpmc.reportsystem.service.RankingServiceImpl;
import com.jpmc.reportsystem.service.reader.DataReader;
import com.jpmc.reportsystem.service.writer.DataWriter;
import com.jpmc.reportsystem.util.ReportingSystemConstants;
import com.jpmc.reportsystem.util.ReportingSystemResourceUtil;

/**
 * This would be the entry point of application This is the backbone of the
 * reporting-service application. It would act as a process-orchestrator,
 * responsible for invoking the relevant modules.
 * 
 * @author jnair1
 *
 */
public class ReportingSystemProcessor {

	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ReportingSystemProcessor.class);

	/**
	 * calculation service
	 */
	private CalculationService calculationService;
	/**
	 * outgoing ranking service
	 */
	private RankingService outgoingRankingService;
	/**
	 * incoming ranking service
	 */
	private RankingService incomingRankingService;
	/**
	 * manipulation service
	 */
	private DataManipulationService manipulationService;
	/**
	 * data reader object
	 */
	private DataReader dataReader;
	/**
	 * data writer object
	 */
	private DataWriter dataWriter;

	public CalculationService getCalculationService() {
		return calculationService;
	}

	public DataManipulationService getManipulationService() {
		return manipulationService;
	}

	/**
	 * constructor injection of beans
	 * 
	 * @param calculationService
	 * @param rankingService
	 * @param manipulationService
	 */
	public ReportingSystemProcessor(CalculationService calculationService, RankingService rankingService,
			DataManipulationService manipulationService, DataReader dataReader, DataWriter dataWriter) {

		this.calculationService = calculationService;
		this.incomingRankingService = rankingService;
		this.outgoingRankingService = new RankingServiceImpl();
		this.manipulationService = manipulationService;
		this.dataReader = dataReader;
		this.dataWriter = dataWriter;
	}

	/**
	 * predicate configured to filter out only the incoming data
	 */
	private static final Predicate<ClientInstructions> incomingPredicate = buyingPredicate -> buyingPredicate
			.getIndicator().equals(Indicator.BUY);
	/**
	 * predicate configured to filter out only the outgoing data
	 */
	private static final Predicate<ClientInstructions> outgoingPredicate = sellingPredicate -> sellingPredicate
			.getIndicator().equals(Indicator.SELL);

	/**
	 * 
	 * this method would be orchestrating all the dependant services to gather
	 * all the data. Once done, it would compute and would be printing it using
	 * the selected writer
	 * 
	 * @param readerType
	 * @param writerType
	 * @throws Exception
	 */
	public String process() {

		String status = null;
		LOGGER.info("Orchestration started");
		try {
			//validate the injections
			validateDependencies();
			
			// check whether the connection is properly established before the
			// processor starts with its orchestration
			if (dataReader.isConnectedEstablished()) {
				// read till there is no more data available with the reader
				while (!dataReader.isDone()) {

					List<ClientInstructions> instructions = dataReader.read();
					// validate the settlement date before we start with actual
					// processing
					instructions.forEach(p -> getManipulationService().updateSettlementDates(p));
					calculateOutgoings(instructions);
					calculateIncomings(instructions);
					calculateOutgoingRankings(instructions);
					calculateIncomingRankings(instructions);
				}
				// generate reports for the dataset
				generateReports();
				status = ReportingSystemConstants.STATUS_SUCCESS;
			} else {
				LOGGER.error("Connection not established, please open a connection");
				status = ReportingSystemConstants.STATUS_FAILURE;
			}

			// handle the exception and close the resources
		} catch (Exception exception) {
			status = ReportingSystemConstants.STATUS_FAILURE;
			LOGGER.error(exception.getMessage(), exception);
			try {
				if(dataReader != null)
					dataReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LOGGER.info("Orchestration completed with status={}", status);
		return status;
	}

	/**
	 * validate all the dependent objects for null This is not required, ideally
	 * when spring is used. If there is an issue with dependency injection,
	 * beans will not be created and message would be displayed. This is an
	 * extra check to prevent system failure as dependencies are manually
	 * injected
	 * @throws ReportingSystemException 
	 */
	private void validateDependencies() throws ReportingSystemException {

		if (calculationService == null || manipulationService == null || incomingRankingService == null
				|| outgoingRankingService == null || dataReader == null || dataWriter == null)
			throw new ReportingSystemException(
					ReportingSystemResourceUtil.getValue(ReportingSystemConstants.EXCEPTION_DEPENDENCY_INJECTION));

	}

	/**
	 * trigger generation of all reports
	 */
	private void generateReports() {
		LOGGER.debug("Report generation trigerred");
		generateIncomingReport();
		generateOutgoingReport();
		generateIncomingRankingReport();
		generateOutgoingRankingReport();
		LOGGER.debug("Report generation completed");
	}

	/**
	 * method to generate report for outgoing
	 */
	private void generateOutgoingReport() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n----------------------------------------\n")
				.append("         Outgoing Daily Amount          \n")
				.append("----------------------------------------\n")
				.append("      Date       |    Trade Amount      \n")
				.append("-----------------+----------------------\n");
		allOutgoings.entrySet().forEach(
				key -> stringBuilder.append(key.getKey() + "       |      " + key.getValue().get() + "\n"));
		dataWriter.write(stringBuilder.toString());
	}

	/**
	 * method to generate report for incoming
	 */
	private void generateIncomingReport() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n----------------------------------------\n")
				.append("         Incoming Daily Amount          \n")
				.append("----------------------------------------\n")
				.append("      Date       |    Trade Amount      \n")
				.append("-----------------+----------------------\n");
		allIncomings.entrySet().forEach(
				key -> stringBuilder.append(key.getKey() + "       |      " + key.getValue().get() + "\n"));
		dataWriter.write(stringBuilder.toString());
	}

	/**
	 * method to generate report for incoming ranking
	 */
	private void generateIncomingRankingReport() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n----------------------------------------\n")
				.append("         Incoming Ranking                 \n")
				.append("----------------------------------------\n")
				.append("      Entity     |    Rank              \n")
				.append("-----------------+----------------------\n");
		allIncomingRankings.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue())
				.forEach(key -> stringBuilder
						.append("        " + key.getKey() + "     |    " + key.getValue() + "              \n"));
		dataWriter.write(stringBuilder.toString());
	}

	/**
	 * method to generate report for outgoing ranking
	 */
	private void generateOutgoingRankingReport() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n----------------------------------------\n")
				.append("         Outgoing Ranking                 \n")
				.append("----------------------------------------\n")
				.append("      Entity     |    Rank              \n")
				.append("-----------------+----------------------\n");
		allOutgoingRankings.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue())
				.forEach(key -> stringBuilder
						.append("        " + key.getKey() + "     |    " + key.getValue() + "              \n"));
		dataWriter.write(stringBuilder.toString());
	}

	/**
	 * call ranking service and get all the ranks for incoming data. Once done
	 * merge the same into main dataset
	 * 
	 * @param instructions
	 * @throws ReportingSystemException
	 */
	private void calculateIncomingRankings(List<ClientInstructions> instructions) throws ReportingSystemException {
		allIncomingRankings = incomingRankingService.evaluateRanking(instructions, incomingPredicate);

	}

	/**
	 * call ranking service and get all the ranks for outgoing data. Once done
	 * merge the same into main dataset
	 * 
	 * @param instructions
	 * @throws ReportingSystemException
	 */
	private void calculateOutgoingRankings(List<ClientInstructions> instructions) throws ReportingSystemException {
		allOutgoingRankings = outgoingRankingService.evaluateRanking(instructions, outgoingPredicate);
	}

	/**
	 * call calculation service and calculate the amount for incoming data. Once
	 * done merge the same into main dataset
	 * 
	 * @param instructions
	 * @throws ReportingSystemException
	 */
	private void calculateIncomings(List<ClientInstructions> instructions) {
		getCalculationService().calculateTotalAmount(instructions, incomingPredicate).entrySet().stream()
				.forEach(p -> allIncomings.merge(p.getKey(), p.getValue(), ReportingSystemProcessor::addition));
	}

	/**
	 * call calculation service and calculate the amount for outgoing data. Once
	 * done merge the same into main dataset
	 * 
	 * @param instructions
	 * @throws ReportingSystemException
	 */
	private void calculateOutgoings(List<ClientInstructions> instructions) {
		getCalculationService().calculateTotalAmount(instructions, outgoingPredicate).entrySet().stream()
				.forEach(p -> allOutgoings.merge(p.getKey(), p.getValue(), ReportingSystemProcessor::addition));
	}

	/**
	 * adds two big integers
	 * 
	 * @param existing
	 * @param fetched
	 * @return
	 */
	private static Optional<BigDecimal> addition(Optional<BigDecimal> existing, Optional<BigDecimal> fetched) {
		return Optional.of(existing.get().add(fetched.get()));
	}

	/**
	 * maps to store the actual value for printing
	 */
	private Map<LocalDate, Optional<BigDecimal>> allOutgoings = new HashMap<>();
	private Map<LocalDate, Optional<BigDecimal>> allIncomings = new HashMap<>();
	private Map<String, Integer> allIncomingRankings = new HashMap<>();
	private Map<String, Integer> allOutgoingRankings = new HashMap<>();
}
