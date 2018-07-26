package com.jpmc.reportsystem.operatingstrategies;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;

/**
 * Tests for AEDOperationalWindow
 * @author jnair1
 *
 */
public class AEDPartnerOperationalWindowTest {
	
	/**
	 * test for extracting the list of windows for AED
	 */
	@Test
	public void getAEDOperatingWindowTest_Success() {
		
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		Map<DayOfWeek, Boolean> aedWindow = aedOperationalWindow.getOperatingWindow();
				
		Assert.assertNotNull(aedWindow);
		Assert.assertEquals(true, aedWindow.get(DayOfWeek.SUNDAY));
		Assert.assertEquals(true, aedWindow.get(DayOfWeek.MONDAY));
		Assert.assertEquals(true, aedWindow.get(DayOfWeek.TUESDAY));
		Assert.assertEquals(true, aedWindow.get(DayOfWeek.WEDNESDAY));
		Assert.assertEquals(true, aedWindow.get(DayOfWeek.THURSDAY));
		Assert.assertEquals(false, aedWindow.get(DayOfWeek.FRIDAY));
		Assert.assertEquals(false, aedWindow.get(DayOfWeek.SATURDAY));
		Assert.assertNull(aedWindow.get("INVALID_DAY"));
	}
	
	/**
	 * test for finding the next operational date with day as null.
	 * We do not have validations in place for to prevent this. Expectation is the caller would be 
	 * performing the null check
	 */
	@Test(expected=NullPointerException.class)
	public void findNextOperationalDayTest_WithNullPointerException() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		aedOperationalWindow.findNextOperationalDay(null);
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as sunday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForSunday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 22));
		
		Assert.assertEquals(DayOfWeek.SUNDAY, nextOpearationalDate.getDayOfWeek());
	}

	/**
	 * test for finding the next operational day if its a weekend with date passed as monday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForMonday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 23));
		
		Assert.assertEquals(DayOfWeek.MONDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as tuesday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForTuesday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 24));
		
		Assert.assertEquals(DayOfWeek.TUESDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as wednesday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForWednesday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 25));
		
		Assert.assertEquals(DayOfWeek.WEDNESDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as thursday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForThursday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 26));
		
		Assert.assertEquals(DayOfWeek.THURSDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as friday.
	 * Friday is a weekend and next operational day for AED is SUNDAY
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForFriday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 27));
		
		Assert.assertEquals(DayOfWeek.SUNDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as saturday.
	 * Saturday is a weekend and next operational day for AED is SUNDAY
	 */
	@Test
	public void findNextOperationalDayTest_ForSaturday() {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = aedOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 28));
		
		Assert.assertEquals(DayOfWeek.SUNDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test to find whether the selected day is a weekend or not
	 * Entering the day as null, system should throw NPE
	 * @throws ReportingSystemException 
	 */
	@Test(expected=NullPointerException.class)
	public void isSelectedDayWeekendTest_WithNullPointerException() throws ReportingSystemException {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		aedOperationalWindow.isSelectedDayWeekday(null);
	}

	/**
	 * test to find whether the selected day is a weekday or not
	 * Entering the day as FRIDAY
	 * @throws ReportingSystemException 
	 */
	@Test
	public void isSelectedDayWeekendTest_ForWeekend() throws ReportingSystemException {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		boolean isWeekend = aedOperationalWindow.isSelectedDayWeekday(LocalDate.of(2018, 07, 27));
		// FRIDAY being a weekend for AED partner, returned value is false as it is a weekend
		Assert.assertTrue(!isWeekend);
	}
	
	/**
	 * test to find whether the selected day is a weekday or not
	 * Entering the day as SUNDAY
	 * @throws ReportingSystemException 
	 */
	@Test
	public void isSelectedDayWeekendTest_ForWeekday() throws ReportingSystemException {
		PartnerOperationalWindow aedOperationalWindow = AEDPartnerOperationalWindow.getInstance();
		boolean isWeekday = aedOperationalWindow.isSelectedDayWeekday(LocalDate.of(2018, 07, 22));
		// SUNDAY being a weekday for AED partner, returned value is true as it is a weekday
		Assert.assertTrue(isWeekday);
	}
}
