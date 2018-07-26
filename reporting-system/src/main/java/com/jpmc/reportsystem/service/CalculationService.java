package com.jpmc.reportsystem.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * Calculation service interface that holds all the calculation needs to be done
 * on different client instructions
 * 
 * @author jnair1
 *
 */
public interface CalculationService {

	/**
	 * <p>
	 * Takes the list of client-instructions and works on the predicates to sum up the trade amounts
	 * This would compute on the entire dataset without any date restriction
	 * 
	 * @param clientInstructions
	 * @param predicate
	 * @return
	 */
	public Map<LocalDate, Optional<BigDecimal>> calculateTotalAmount(List<ClientInstructions> clientInstructions,
			Predicate<ClientInstructions> predicate);
	
	/**
	 * <p>
	 * Takes the list of client-instructions and works on the predicates to sum up the trade amounts
	 * This would compute on the specific dataset for the selected date 
	 * 
	 * @param clientInstructions
	 * @param predicate
	 * @param selectedDate
	 * @return
	 */
	public BigDecimal calculateDayWiseTotalAmount(List<ClientInstructions> clientInstructions,
			Predicate<ClientInstructions> predicate, LocalDate selectedDate);
}
