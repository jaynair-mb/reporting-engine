package com.jpmc.reportsystem.util;

import java.util.MissingResourceException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for PropertyFileReader utility
 * @author jnair1
 *
 */
public class ReportingSystemResourceUtilTest {
	
	@Test
	public void getValueTest() {
		String expectedValue = "Client instructions are null and cannot be processed";
		String actualValue = ReportingSystemResourceUtil.getValue(ReportingSystemConstants.EXCEPTION_EMPTY_CLIENT_INSTRUCTIONS);
		
		Assert.assertNotNull(actualValue);
		Assert.assertEquals(expectedValue, actualValue);
	}
	
	@Test(expected=MissingResourceException.class)
	public void getValueTest_ForNoData() {
		ReportingSystemResourceUtil.getValue("Blank");
	}
		

}
