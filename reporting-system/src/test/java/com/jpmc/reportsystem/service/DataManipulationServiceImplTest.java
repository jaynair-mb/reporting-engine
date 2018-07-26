package com.jpmc.reportsystem.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.model.Indicator;

/**
 * tests to validate the methods of DataManipulationServiceImpl
 * 
 * @author jnair1
 *
 */
public class DataManipulationServiceImplTest {

	/**
	 * test for updating the settlement date settlement date is a valid weekday,
	 * hence no changes would happen to the settlement date
	 */
	@Test
	public void updateSettlementDates_WithWeekday_ForAED() {

		DataManipulationServiceImpl serviceImpl = new DataManipulationServiceImpl();
		ClientInstructions instruction = serviceImpl.updateSettlementDates(new ClientInstructions("foo", Indicator.BUY,
				BigDecimal.valueOf(0.50), BigDecimal.valueOf(100.25), BigInteger.valueOf(100),
				Currency.getInstance("AED"), LocalDate.now(), LocalDate.of(2018, 7, 24)));

		System.out.println(instruction.getComments());
		Assert.assertEquals(instruction.getSettlementDate(), LocalDate.of(2018, 7, 24));

	}

	/**
	 * test for updating the settlement date settlement date is a weekend,
	 * system would change the date to a weekday
	 */
	@Test
	public void updateSettlementDates_WithWeekend_ForAED() {

		DataManipulationServiceImpl serviceImpl = new DataManipulationServiceImpl();
		ClientInstructions instruction = serviceImpl.updateSettlementDates(new ClientInstructions("foo", Indicator.BUY,
				BigDecimal.valueOf(0.50), BigDecimal.valueOf(100.25), BigInteger.valueOf(100),
				Currency.getInstance("AED"), LocalDate.now(), LocalDate.of(2018, 7, 27)));

		System.out.println(instruction.getComments());
		Assert.assertEquals(instruction.getSettlementDate(), LocalDate.of(2018, 7, 29));

	}

	/**
	 * test for updating the settlement date passing null value which obviously
	 * would throw NPE. No null values would be passed for computation. In a
	 * normal scenrio, settlement dates cannot be null. If it all it is then we
	 * would handle it before the method is called
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void updateSettlementDates_WithNull_ForAED() {

		DataManipulationServiceImpl serviceImpl = new DataManipulationServiceImpl();
		serviceImpl.updateSettlementDates(
				new ClientInstructions("foo", Indicator.BUY, BigDecimal.valueOf(0.50), BigDecimal.valueOf(100.25),
						BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(), null));

	}
	
	/**
	 * test for updating the settlement date settlement date is a valid weekday,
	 * hence no changes would happen to the settlement date
	 */
	@Test
	public void updateSettlementDates_WithWeekday_ForDefault() {

		DataManipulationServiceImpl serviceImpl = new DataManipulationServiceImpl();
		ClientInstructions instruction = serviceImpl.updateSettlementDates(new ClientInstructions("foo", Indicator.BUY,
				BigDecimal.valueOf(0.50), BigDecimal.valueOf(100.25), BigInteger.valueOf(100),
				Currency.getInstance("GBP"), LocalDate.now(), LocalDate.of(2018, 7, 24)));

		System.out.println(instruction.getComments());
		Assert.assertEquals(instruction.getSettlementDate(), LocalDate.of(2018, 7, 24));

	}

	/**
	 * test for updating the settlement date settlement date is a weekend,
	 * system would change the date to a weekday
	 */
	@Test
	public void updateSettlementDates_WithWeekend_ForDefault() {

		DataManipulationServiceImpl serviceImpl = new DataManipulationServiceImpl();
		ClientInstructions instruction = serviceImpl.updateSettlementDates(new ClientInstructions("foo", Indicator.BUY,
				BigDecimal.valueOf(0.50), BigDecimal.valueOf(100.25), BigInteger.valueOf(100),
				Currency.getInstance("INR"), LocalDate.now(), LocalDate.of(2018, 7, 28)));

		System.out.println(instruction.getComments());
		Assert.assertEquals(instruction.getSettlementDate(), LocalDate.of(2018, 7, 30));

	}

}
