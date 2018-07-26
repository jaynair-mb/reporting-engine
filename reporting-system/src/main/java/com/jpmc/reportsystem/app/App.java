package com.jpmc.reportsystem.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.service.CalculationServiceImpl;
import com.jpmc.reportsystem.service.DataManipulationServiceImpl;
import com.jpmc.reportsystem.service.RankingServiceImpl;
import com.jpmc.reportsystem.service.reader.CSVDataReader;
import com.jpmc.reportsystem.service.reader.DataReader;
import com.jpmc.reportsystem.service.writer.ConsoleDataWriter;

/**
 * 
 * @author jnair1
 *
 */
public class App {
	
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		LOGGER.info("start");
		DataReader dataReader = new CSVDataReader();
		dataReader.addConnectionDetails("src/main/resources", "client-instructions.csv");
		
		ReportingSystemProcessor processor = new ReportingSystemProcessor(new CalculationServiceImpl(),
				new RankingServiceImpl(), new DataManipulationServiceImpl(), dataReader,
				new ConsoleDataWriter());
		processor.process();

	}
}
