package com.jpmc.reportsystem.service.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;
import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * Tests for the CSVReader
 * 
 * @author jnair1
 *
 */
public class CSVDataReaderTest {



	/**
	 * read fail scenario without connection
	 * 
	 * @throws Exception
	 */
	@Test(expected = ReportingSystemException.class)
	public void readRecords_FAIL() throws Exception {
		DataReader dataReader = new CSVDataReader();
		List<ClientInstructions> instructions = new ArrayList<>();
		while (!dataReader.isDone()) {
			instructions.addAll(dataReader.read());
		}
	}

	/**
	 * read success scenario with connection
	 * 
	 * @throws Exception
	 */
	@Test
	public void readRecords_SUCCESS() throws Exception {
		DataReader dataReader = new CSVDataReader();
		List<ClientInstructions> instructions = new ArrayList<>();
		boolean isConnectionAvailable = dataReader.addConnectionDetails("src/main/resources",
				"client-instructions.csv");

		while (!dataReader.isDone()) {
			instructions.addAll(dataReader.read());
		}

		Assert.assertTrue(isConnectionAvailable);
		Assert.assertEquals("Foo1", instructions.get(0).getEntity());
	}

	/**
	 * connection not established scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void isConnectedEstablished_FAIL() throws Exception {
		DataReader dataReader = new CSVDataReader();
		boolean isConnectionAvailable = dataReader.isConnectedEstablished();

		Assert.assertFalse(isConnectionAvailable);
	}

	/**
	 * connection established scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void isConnectedEstablished_SUCCESS() throws Exception {
		DataReader dataReader = new CSVDataReader();
		dataReader.addConnectionDetails("src/main/resources", "client-instructions.csv");
		boolean isConnectionAvailable = dataReader.isConnectedEstablished();

		Assert.assertTrue(isConnectionAvailable);
	}

	/**
	 * add connection failure scenario
	 * 
	 * @throws Exception
	 */
	@Test(expected = ReportingSystemException.class)
	public void addUrl_WithEmptyString() throws Exception {
		DataReader dataReader = new CSVDataReader();
		dataReader.addConnectionDetails("", "");
	}

	/**
	 * add connection failure scenario
	 * 
	 * @throws Exception
	 */
	@Test(expected = ReportingSystemException.class)
	public void addUrl_WithNull() throws Exception {
		DataReader dataReader = new CSVDataReader();
		dataReader.addConnectionDetails(null, null);
	}

	/**
	 * add connection failure scenario with wrong parameters
	 * 
	 * @throws Exception
	 */
	@Test(expected = IOException.class)
	public void addUrl_WithInvalidResource() throws Exception {
		DataReader dataReader = new CSVDataReader();
		dataReader.addConnectionDetails("src/main/resources", "invalid-client-instructions.csv");
	}

	/**
	 * add connection success scenario
	 * 
	 * @throws Exception
	 */
	@Test
	public void addUrl_WithProperURLAndResourceName() throws Exception {

		DataReader dataReader = new CSVDataReader();
		boolean isConnectionAvailable = dataReader.addConnectionDetails("src/main/resources",
				"client-instructions.csv");
		Assert.assertTrue(isConnectionAvailable);
	}
}
