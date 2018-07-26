package com.jpmc.reportsystem.exceptions;

/**
 * Custom exception for any erroneous activity reported by the application.
 * 
 * @author jnair1
 *
 */
public class ReportingSystemException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3881100959646479111L;

	/**
     * Constructs an <code>ReportingSystemException</code> with the
     * specified detail message.
     *
     * @param   message   the detail message.
     */
	public ReportingSystemException(String message) {
		super(message);
	}
	    
}
