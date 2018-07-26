package com.jpmc.reportsystem.operatingstrategies;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;

/**
 * Tests for otherOperationalWindow
 * @author jnair1
 *
 */
public class DefaultPartnerOperationalWindowTest {
	
	/**
	 * test for extracting the list of windows for AED
	 */
	@Test
	public void getAEDOperatingWindowTest_Success() {
		
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		Map<DayOfWeek, Boolean> otherWindow = otherOperationalWindow.getOperatingWindow();
				
		Assert.assertNotNull(otherWindow);
		Assert.assertEquals(true, otherWindow.get(DayOfWeek.MONDAY));
		Assert.assertEquals(true, otherWindow.get(DayOfWeek.TUESDAY));
		Assert.assertEquals(true, otherWindow.get(DayOfWeek.WEDNESDAY));
		Assert.assertEquals(true, otherWindow.get(DayOfWeek.THURSDAY));
		Assert.assertEquals(true, otherWindow.get(DayOfWeek.FRIDAY));
		Assert.assertEquals(false, otherWindow.get(DayOfWeek.SATURDAY));
		Assert.assertEquals(false, otherWindow.get(DayOfWeek.SUNDAY));
		Assert.assertNull(otherWindow.get("INVALID_DAY"));
	}
	
	/**
	 * test for finding the next operational date with day as null.
	 * We do not have validations in place for to prevent this. Expectation is the caller would be 
	 * performing the null check
	 */
	@Test(expected=NullPointerException.class)
	public void findNextOperationalDayTest_WithNullPointerException() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.findNextOperationalDay(null);
		Assert.assertNull(nextOpearationalDate);		
	}

	/**
	 * test for finding the next operational day if its a weekend with date passed as monday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForMonday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 23));
		
		Assert.assertEquals(DayOfWeek.MONDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as tuesday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForTuesday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 24));
		
		Assert.assertEquals(DayOfWeek.TUESDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as wednesday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForWednesday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 25));
		
		Assert.assertEquals(DayOfWeek.WEDNESDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as thursday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForThursday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 26));
		
		Assert.assertEquals(DayOfWeek.THURSDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as friday.
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForFriday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 27));
		
		Assert.assertEquals(DayOfWeek.FRIDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as saturday.
	 * Saturday is a weekend and next operational day is MONDAY
	 */
	@Test
	public void findNextOperationalDayTest_ForSaturday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 28));
		
		Assert.assertEquals(DayOfWeek.MONDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test for finding the next operational day if its a weekend with date passed as sunday.
	 * Sunday is a weekend and next operational day is MONDAY
	 * 
	 */
	@Test
	public void findNextOperationalDayTest_ForSunday() {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		LocalDate nextOpearationalDate = otherOperationalWindow.
				findNextOperationalDay(LocalDate.of(2018, 07, 22));
		
		Assert.assertEquals(DayOfWeek.MONDAY, nextOpearationalDate.getDayOfWeek());
	}
	
	/**
	 * test to find whether the selected day is a weekend or not
	 * Entering the day as null, system should throw NPE
	 * @throws ReportingSystemException 
	 */
	@Test(expected=NullPointerException.class)
	public void isSelectedDayWeekendTest_WithNullPointerException() throws ReportingSystemException {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		otherOperationalWindow.isSelectedDayWeekday(null);
	}
	
	/**
	 * test to find whether the selected day is a weekday or not
	 * Entering the day as SATURDAY
	 * @throws ReportingSystemException 
	 */
	@Test
	public void isSelectedDayWeekendTest_ForWeekend() throws ReportingSystemException {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		boolean isWeekend = otherOperationalWindow.isSelectedDayWeekday(LocalDate.of(2018, 07, 28));
		// SATURDAY being a weekend for other partner, returned value is false as it is not a weekday
		Assert.assertTrue(!isWeekend);
	}
	
	/**
	 * test to find whether the selected day is a weekday or not
	 * Entering the day as MONDAY
	 * @throws ReportingSystemException 
	 */
	@Test
	public void isSelectedDayWeekendTest_ForWeekday() throws ReportingSystemException {
		PartnerOperationalWindow otherOperationalWindow = DefaultPartnerOperationalWindow.getInstance();
		boolean isWeekday = otherOperationalWindow.isSelectedDayWeekday(LocalDate.of(2018, 07, 30));
		// MONDAY being a weekday for other partner, returned value is true as it is a weekday
		Assert.assertTrue(isWeekday);
	}
}
