package com.jpmc.reportsystem.util;

import java.util.ResourceBundle;

/**
 * Property Resource Utility class This is to read the properties file and get
 * the values associated with a particular key
 * 
 * @author jnair1
 *
 */
public class ReportingSystemResourceUtil {

	/**
	 * resource bundle object
	 */
	private static final ResourceBundle resource = ResourceBundle.getBundle("reporting-system-application");

	/**
	 * retrieves the value of the property passes as input.
	 * 
	 * @param String
	 *            property value.
	 */
	public static String getValue(String propertyName) {

		return resource.getString(propertyName);

	}

}
