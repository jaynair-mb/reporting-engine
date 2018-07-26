package com.jpmc.reportsystem.service.writer;

import javax.naming.OperationNotSupportedException;

/**
 * interface for DataWriter. Can be expanded like reader but keeping it basic
 * due to time limitation
 * 
 * @author jnair1
 *
 */
public interface DataWriter {

	/**
	 * method to write the data
	 * 
	 * @param data
	 */
	public void write(String data);

	/**
	 * method to close the resource
	 * 
	 * @throws OperationNotSupportedException
	 */
	public void close() throws OperationNotSupportedException;
}
