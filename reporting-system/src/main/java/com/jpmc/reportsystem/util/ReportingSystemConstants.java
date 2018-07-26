package com.jpmc.reportsystem.util;

/**
 * Constants for reporting-system application
 * 
 * By default all the constants are public and static but I like colors hence added them
 * 
 * @author jnair1
 *
 */
public interface ReportingSystemConstants {

	public static String EXCEPTION_INVALID_DAY_OF_WEEK = "exceptions.invalid.dayofweek";
	public static String EXCEPTION_EMPTY_CLIENT_INSTRUCTIONS = "exceptions.empty.clientinstructions";
	
	public static String EXCEPTION_READER_INVALID_INPUT = "exceptions.reader.invalidinput"; 
	public static String EXCEPTION_READER_INVALID_CONNECTION = "exceptions.reader.invalidconnection";
	public static String EXCEPTION_DEPENDENCY_INJECTION = "exceptions.dependencies";
	
	public static String DATA_CHUNK_SIZE = "chunksize";
	
	public static String CURRENCY_AED = "AED";
	public static String CURRENCY_SAR = "SAR";
	
	public static String STATUS_SUCCESS = "SUCCESS";
	public static String STATUS_FAILURE = "FAILURE";
	
}
