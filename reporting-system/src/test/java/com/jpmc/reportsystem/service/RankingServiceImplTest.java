package com.jpmc.reportsystem.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;
import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.model.Indicator;

public class RankingServiceImplTest {

	private RankingService rankingService = new RankingServiceImpl();

	/**
	 * evaluate ranking tests for buy transactions
	 * @throws ReportingSystemException 
	 */
	@Test
	public void evaluateRanking_ForBuy() throws ReportingSystemException {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo1", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 25)));
		clientInstructions.add(new ClientInstructions("foo2", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo2", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo3", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo3", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 29)));

		Map<String, Integer> dataMap = rankingService.evaluateRanking(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY));
		
		Assert.assertEquals(3, dataMap.size());
		Assert.assertEquals(dataMap.get("foo1").intValue(), 2);
		Assert.assertEquals(dataMap.get("foo2").intValue(), 3);
		Assert.assertEquals(dataMap.get("foo3").intValue(),1);
	}
	
	/**
	 * evaluate ranking tests for sell transactions
	 * @throws ReportingSystemException 
	 */
	@Test
	public void evaluateRanking_ForSell() throws ReportingSystemException {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo1", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 25)));
		clientInstructions.add(new ClientInstructions("foo1", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 26)));
		clientInstructions.add(new ClientInstructions("foo1", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(101.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo2", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(400), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo2", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo3", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 29)));
		clientInstructions.add(new ClientInstructions("foo4", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(108.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 28)));
		clientInstructions.add(new ClientInstructions("foo5", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(1000.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 29)));

		Map<String, Integer> dataMap = rankingService.evaluateRanking(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.SELL));
		
		Assert.assertEquals(4, dataMap.size());
		Assert.assertEquals(dataMap.get("foo1").intValue(), 2);
		Assert.assertEquals(dataMap.get("foo2").intValue(), 1);
		Assert.assertEquals(dataMap.get("foo3").intValue(),4);
		Assert.assertEquals(dataMap.get("foo4").intValue(),3);
	}
	
	
	/**
	 * evaluate ranking tests for sell transactions with no sell transactions
	 * @throws ReportingSystemException 
	 */
	@Test
	public void evaluateRanking_ForSell_WithNoSellTransactions() throws ReportingSystemException {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo1", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 25)));
		clientInstructions.add(new ClientInstructions("foo1", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo2", Indicator.BUY, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 29)));

		Map<String, Integer> dataMap = rankingService.evaluateRanking(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.SELL));
		
		Assert.assertNotNull(dataMap);
		Assert.assertEquals(0, dataMap.size());
	}
	
	/**
	 * evaluate ranking tests for buy transactions with no buy transactions
	 * @throws ReportingSystemException 
	 */
	@Test
	public void evaluateRanking_ForBuy_WithNoBuyTransactions() throws ReportingSystemException {
		List<ClientInstructions> clientInstructions = new ArrayList<>();
		clientInstructions.add(new ClientInstructions("foo1", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 25)));
		clientInstructions.add(new ClientInstructions("foo1", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 27)));
		clientInstructions.add(new ClientInstructions("foo2", Indicator.SELL, BigDecimal.valueOf(0.50),
				BigDecimal.valueOf(100.25), BigInteger.valueOf(100), Currency.getInstance("AED"), LocalDate.now(),
				LocalDate.of(2018, 7, 29)));

		Map<String, Integer> dataMap = rankingService.evaluateRanking(clientInstructions,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY));
		
		Assert.assertEquals(0, dataMap.size());
	}
	
	/**
	 * evaluate ranking tests for buy transactions with null data
	 * @throws ReportingSystemException 
	 */
	@Test(expected=ReportingSystemException.class)
	public void evaluateRanking_ForBuy_WithNullInstructions() throws ReportingSystemException {

		rankingService.evaluateRanking(null,
				buyingPredicate -> buyingPredicate.getIndicator().equals(Indicator.BUY));
	}

}
