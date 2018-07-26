package com.jpmc.reportsystem.service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;
import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * This service is responsible for calculating the ranks for the entities.
 * 
 * @author jnair1
 *
 */
public interface RankingService {

	/**
	 * <p>
	 * Iterates through the list to identify the maximums trade amount against a
	 * particular entity
	 * 
	 */
	public Map<String, Integer> evaluateRanking(List<ClientInstructions> clientInstructions,
			Predicate<ClientInstructions> predicate) throws ReportingSystemException;
}
