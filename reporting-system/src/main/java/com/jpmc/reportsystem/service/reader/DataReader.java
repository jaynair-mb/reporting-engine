package com.jpmc.reportsystem.service.reader;

import java.util.List;

import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * interface exposed for DataReader services
 * 
 * @author jnair1
 *
 */
public interface DataReader {
	
	/**
	 * to read all the data through the configured datareader
	 * @return
	 * @throws Exception
	 */
	public List<ClientInstructions> read() throws Exception;
	
	/**
	 * method to indicate whether the reader is done with the processing or not
	 * @return
	 * @throws Exception
	 */
	public boolean isDone() throws Exception;
	
	/**
	 * method to close the reader and releasing of the resources
	 * @throws Exception
	 */
	public void close() throws Exception;
	
	/**
	 * method to attach a connection to the reader using the input configuration
	 * @param url
	 * @param resourceName
	 * @return
	 * @throws Exception
	 */
	public boolean addConnectionDetails(String url, String resourceName) throws Exception;
	
	/**
	 * method to determine whether the connection was successfully established or not
	 * @return
	 * @throws Exception
	 */
	public boolean isConnectedEstablished() throws Exception;

}
