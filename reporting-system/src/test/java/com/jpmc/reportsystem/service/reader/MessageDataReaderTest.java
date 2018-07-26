package com.jpmc.reportsystem.service.reader;

import java.io.IOException;

import org.junit.Test;

/**
 * Tests for the MessageDataReader
 * 
 * actual implementation is not done. just a mock structure
 * 
 * @author jnair1
 *
 */
public class MessageDataReaderTest {

	@Test
	public void read_test() throws IOException, Exception {
		new MessageDataReader().read();
		
	}
	
	@Test
	public void addConnectionDetails_test() throws IOException, Exception {
		new MessageDataReader().addConnectionDetails(null, null);
		
	}
	@Test
	public void isConnectedEstablished_test() throws IOException, Exception {
		new MessageDataReader().isConnectedEstablished();
		
	}
	@Test
	public void close_test() throws IOException, Exception {
		new MessageDataReader().close();;
		
	}
	@Test
	public void isDone_test() throws IOException, Exception {
		new MessageDataReader().isDone();
		
	}
	
	
	
	
	
	
	
	
}
