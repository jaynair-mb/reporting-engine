package com.jpmc.reportsystem.service.writer;

import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Console implementation of DataWriter.
 * This would be printing all the data on the console
 * 
 * @author jnair1
 *
 */
public class ConsoleDataWriter implements DataWriter {
	
	/**
	 * logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ConsoleDataWriter.class);

	/**
	 * writing onto console
	 */
	@Override
	public void write(String data) {
		
		LOGGER.info(data);
	}

	/**
	 * to close the resource
	 */
	@Override
	public void close() throws OperationNotSupportedException {
		// TODO Auto-generated method stub
		
	}

}
