package com.jpmc.reportsystem.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.model.Indicator;

/**
 * Tests written for CalculationServiceImpl
 * 
 * @author jnair1
 *
 */
public class CalculationServiceImplTest {

	private CalculationService calculationService = new CalculationServiceImpl();

	/**
	 * calculate the amount for all the incoming all the input dates for selling
	 * are the same
	 */
	@Test
	public void calculateSettlementTest_ForBuy_SameDates() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));

		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		Map<LocalDate, Optional<BigDecimal>> settlements = calculationService.calculateTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY));

		Assert.assertEquals(settlements.size(),  1);
		Assert.assertTrue(settlements.get(LocalDate.of(2018, 7, 27)).isPresent());
		Assert.assertEquals(settlements.get(LocalDate.of(2018, 7, 27)).get().setScale(0), BigDecimal.valueOf(10025));
	}

	/**
	 * calculate the amount for all the outgoing all the input dates for buying
	 * are the same
	 */
	@Test
	public void calculateSettlementTest_ForSell_SameDates() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));

		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		Map<LocalDate, Optional<BigDecimal>> settlements = calculationService.calculateTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.SELL));
		Assert.assertEquals(settlements.size(), 1);
		Assert.assertTrue(settlements.get(LocalDate.of(2018, 7, 28)).isPresent());
		Assert.assertEquals(settlements.get(LocalDate.of(2018, 7, 28)).get().setScale(0), BigDecimal.valueOf(40050));

	}

	/**
	 * calculate the amount for all the incoming with different dates
	 */
	@Test
	public void calculateSettlementTest_ForBuy_DifferentDates() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		Map<LocalDate, Optional<BigDecimal>> settlements = calculationService.calculateTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY));

		Assert.assertEquals(settlements.size(), 2);

		Assert.assertTrue(settlements.get(LocalDate.of(2018, 7, 27)).isPresent());
		Assert.assertTrue(settlements.get(LocalDate.of(2018, 7, 28)).isPresent());

		Assert.assertEquals(settlements.get(LocalDate.of(2018, 7, 27)).get().setScale(1), BigDecimal.valueOf(5012.50));
		Assert.assertEquals(settlements.get(LocalDate.of(2018, 7, 28)).get().setScale(1), BigDecimal.valueOf(5012.50));

	}

	/**
	 * calculate the amount for all the outgoing with different dates
	 */
	@Test
	public void calculateSettlementTest_ForSell_DifferentDates() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo6", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(200.25), BigInteger.valueOf(200), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		Map<LocalDate, Optional<BigDecimal>> settlements = calculationService.calculateTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.SELL));

		Assert.assertEquals(settlements.size(), 2);

		Assert.assertTrue(settlements.get(LocalDate.of(2018, 7, 27)).isPresent());
		Assert.assertTrue(settlements.get(LocalDate.of(2018, 7, 28)).isPresent());

		Assert.assertEquals(settlements.get(LocalDate.of(2018, 7, 27)).get().setScale(0), BigDecimal.valueOf(20025));
		Assert.assertEquals(settlements.get(LocalDate.of(2018, 7, 28)).get().setScale(0), BigDecimal.valueOf(20025));
	}

	/**
	 * Only selected dates data would be calculated and returned back for buying
	 */
	@Test
	public void calculateDayWiseSettlement_ForBuy_SelectedDate() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		BigDecimal settlementAmount = calculationService.calculateDayWiseTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY), LocalDate.of(2018, 7, 27));
		Assert.assertEquals(settlementAmount.setScale(0), BigDecimal.valueOf(10025));
	}
	
	/**
	 * Only selected dates data would be calculated and returned back for selling
	 */
	@Test
	public void calculateDayWiseSettlement_ForSell_SelectedDate() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 26)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		BigDecimal settlementAmount = calculationService.calculateDayWiseTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.SELL), LocalDate.of(2018, 7, 28));
		Assert.assertEquals(settlementAmount.setScale(1), BigDecimal.valueOf(5012.5));
	}
	
	/**
	 * test to ensure system works fine even if the requested date is not available in the system
	 */
	@Test
	public void calculateDayWiseSettlement_NoDataAvailableForSelectedDate() {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 26)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));

		// buy
		BigDecimal settlementAmount = calculationService.calculateDayWiseTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY), LocalDate.of(2018, 7, 21));
		Assert.assertEquals(settlementAmount.setScale(0), BigDecimal.valueOf(0));
		//sell
		settlementAmount = calculationService.calculateDayWiseTotalAmount(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.SELL), LocalDate.of(2018, 7, 21));
		Assert.assertEquals(settlementAmount.setScale(0), BigDecimal.valueOf(0));
	}

}
