package com.jpmc.reportsystem.service.reader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * Message version of reader implementation. This can be used a as
 * messageListener. This is just a stub without implementation for demonstration
 * purpose
 * 
 * @author jnair1
 *
 */
public class MessageDataReader implements DataReader {

	/**
	 * logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(MessageDataReader.class);


	/**
	 * method to read
	 */
	@Override
	public List<ClientInstructions> read() throws Exception {
		LOGGER.info("reading in progress");
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * to notify that the processing is done
	 */
	@Override
	public boolean isDone() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * close the resource
	 */
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * add a connection
	 */
	@Override
	public boolean addConnectionDetails(String url, String resourceName) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * to check whether the connection was successful or not
	 */
	@Override
	public boolean isConnectedEstablished() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
