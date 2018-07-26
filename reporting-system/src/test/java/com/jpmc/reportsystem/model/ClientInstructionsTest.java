package com.jpmc.reportsystem.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for ClientInstructionsTest datamodel
 * 
 * @author jnair1
 *
 */
public final class ClientInstructionsTest {
	
	
	/**
	 * Invalid currency
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tradeAmountTest_WithInvalidCurrency() {
		new ClientInstructions("foo", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(200), Currency.getInstance("SGP"), LocalDate.now(), LocalDate.now());
	}

	/**
	 * with null values, the tradeamount would be zero
	 */
	@Test
	public void tradeAmountTest_WithNullValues() {
		ClientInstructions clientInstructions = new ClientInstructions(null, null, null, null, null, null, null,
				null);
		Assert.assertEquals(BigDecimal.ONE, clientInstructions.getTradeAmount());
	}

	/**
	 * with negative values, the tradeamount would be zero
	 */
	@Test
	public void tradeAmountTest_WithNegativeValues() {
		ClientInstructions clientInstructions = new ClientInstructions(null, null, BigDecimal.valueOf(-1),
				BigDecimal.valueOf(-1), BigInteger.valueOf(-10), null, null, null);
		Assert.assertEquals(BigDecimal.ONE, clientInstructions.getTradeAmount());
	}
	
	/**
	 * with ZERO values, the tradeamount would be zero
	 */
	@Test
	public void tradeAmountTest_WithZeroValues() {
		ClientInstructions clientInstructions = new ClientInstructions(null, null, BigDecimal.valueOf(0),
				BigDecimal.valueOf(0), BigInteger.valueOf(0), null, null, null);
		Assert.assertEquals(BigDecimal.ONE, clientInstructions.getTradeAmount());
	}
	
	/**
	 * with real values for FOO, the tradeamount would be computed based on input entered
	 */
	@Test
	public void tradeAmountTest_WithFooRealValues() {
		ClientInstructions clientInstructions = new ClientInstructions("foo", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(200), Currency.getInstance("GBP"), LocalDate.now(), LocalDate.now());
		Assert.assertEquals(BigDecimal.valueOf(10025), clientInstructions.getTradeAmount().setScale(0));
	}
	
	/**
	 * with real values, the tradeamount would be computed based on input entered
	 */
	@Test
	public void tradeAmountTest_WithBarRealValues() {
		ClientInstructions clientInstructions = new ClientInstructions("bar", Indicator.BUY, BigDecimal.valueOf(0.22),
				BigDecimal.valueOf(150.5), BigInteger.valueOf(450), Currency.getInstance("AED"), LocalDate.now(), LocalDate.now());
		Assert.assertEquals(BigDecimal.valueOf(14899.5), clientInstructions.getTradeAmount().setScale(1));
	}
	
	/**
	 * compare two same ClientInstructions objects
	 */
	@Test
	public void clientInstructionsTest_ForSameObjects() {
		LocalDate instructionDate = LocalDate.now();
		LocalDate settlementDate = LocalDate.now();
		ClientInstructions clientInstructions = new ClientInstructions("bar", Indicator.BUY, BigDecimal.valueOf(0.22),
				BigDecimal.valueOf(150.5), BigInteger.valueOf(450), Currency.getInstance("AED"), instructionDate, settlementDate);
		ClientInstructions clientInstructions1 = new ClientInstructions("bar", Indicator.BUY, BigDecimal.valueOf(0.22),
				BigDecimal.valueOf(150.5), BigInteger.valueOf(450), Currency.getInstance("AED"), instructionDate, settlementDate);
		Assert.assertTrue(clientInstructions.equals(clientInstructions1));
	}
	
	/**
	 * compare two ClientInstructions objects, one with null
	 */
	@Test
	public void clientInstructionsTest_ForNull() {
		LocalDate instructionDate = LocalDate.now();
		LocalDate settlementDate = LocalDate.now();
		ClientInstructions clientInstructions = new ClientInstructions("bar", Indicator.BUY, BigDecimal.valueOf(0.22),
				BigDecimal.valueOf(150.5), BigInteger.valueOf(450), Currency.getInstance("AED"), instructionDate, settlementDate);
		Assert.assertFalse(clientInstructions.equals(null));
	}
	
	/**
	 * compare two ClientInstructions objects, inequal methods with different dates
	 */
	@Test
	public void clientInstructionsTest_ForInequality() {
		ClientInstructions clientInstructions = new ClientInstructions("bar", Indicator.BUY, BigDecimal.valueOf(0.22),
				BigDecimal.valueOf(150.5), BigInteger.valueOf(450), Currency.getInstance("AED"), LocalDate.now(), LocalDate.now());
		ClientInstructions clientInstructions1 = new ClientInstructions("bar", Indicator.BUY, BigDecimal.valueOf(0.22),
				BigDecimal.valueOf(150.5), BigInteger.valueOf(450), Currency.getInstance("AED"), LocalDate.now(), LocalDate.now());
		Assert.assertFalse(clientInstructions.equals(clientInstructions1));
	}

}
