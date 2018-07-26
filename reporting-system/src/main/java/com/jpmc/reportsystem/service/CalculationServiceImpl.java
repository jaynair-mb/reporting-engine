package com.jpmc.reportsystem.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * Calculation service implementation that holds all the calculation needs to be
 * done on different client instructions
 * 
 * @author jnair1
 *
 */
public class CalculationServiceImpl implements CalculationService {
	
	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(CalculationServiceImpl.class);
	
	/**
	 * <p>
	 * Takes the list of client-instructions and works on the predicates to sum
	 * up the trade amounts This would compute on the entire dataset without any
	 * date restriction
	 * 
	 * @param clientInstructions
	 *            - dataset of instructions
	 * @param predicate
	 *            - predicate that would be applied to filter. This makes the
	 *            method more generic and can be reused
	 * @return settlement - datewise settlement with trade amount summation
	 */
	@Override
	public Map<LocalDate, Optional<BigDecimal>> calculateTotalAmount(List<ClientInstructions> clientInstructions,
			Predicate<ClientInstructions> predicate) {
		
		LOGGER.debug("calculating total amount for entire dataset provided");
		
		// this needs to be paginated if the size increases in future. Only
		// pages would be retrieved, instead of entire dataset

		Map<LocalDate, Optional<BigDecimal>> settlement = clientInstructions.stream().filter(predicate)
				.collect(Collectors.groupingBy(ClientInstructions::getSettlementDate,
						Collectors.mapping(ClientInstructions::getTradeAmount, Collectors.reducing(BigDecimal::add))));

		return settlement;
	}

	/**
	 * <p>
	 * Takes the list of client-instructions and works on the predicates to sum
	 * up the trade amounts This would compute on the specific dataset for the
	 * selected date
	 * 
	 * @param clientInstructions
	 *            - dataset of instructions
	 * @param predicate
	 *            - predicate that would be applied to filter. This makes the
	 *            method more generic and can be reused
	 * @return amount - trade amount summation
	 */
	public BigDecimal calculateDayWiseTotalAmount(List<ClientInstructions> clientInstructions,
			Predicate<ClientInstructions> predicate, LocalDate selectedDate) {
		LOGGER.debug("calculating total amount for selectedDate={}", selectedDate);
		
		double settlementAmount = clientInstructions.stream()
				.filter(p -> selectedDate.isEqual(p.getSettlementDate()))
				.filter(predicate)
				.flatMapToDouble(ins -> DoubleStream.of(ins.getTradeAmount().doubleValue())).sum();

		LOGGER.debug("calculated total amount for settlementAmount={}", settlementAmount);
		return BigDecimal.valueOf(settlementAmount);
	}
}
