package com.jpmc.reportsystem.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.exceptions.ReportingSystemException;
import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.util.ReportingSystemConstants;
import com.jpmc.reportsystem.util.ReportingSystemResourceUtil;

/**
 * This service is responsible for calculating the ranks for the entities.
 * 
 * Assignment Requirement - Ranking of entities based on incoming and outgoing
 * amount. Eg: If entity foo instructs the highest amount for a buy instruction,
 * then foo is rank 1 for outgoing
 * 
 * @author jnair1
 *
 */
public class RankingServiceImpl implements RankingService {

	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(RankingServiceImpl.class);
	private static int rank = 1;
	
	/**
	 * holds the ranks of all the entities 
	 */
	Map<String, BigDecimal> records = new HashMap<>();

	/**
	 * 
	 * <p>
	 * Iterates through the list to identify the maximums trade amount against a
	 * particular entity.
	 * 
	 * @param clientInstructions
	 *            - instructions sent to compute
	 * @param predicate
	 *            - predicate used to filter out the incoming and outgoing.
	 *            Requirement is to have display rank based on incoming and
	 *            outgoing transactions
	 * 
	 * @return entityTransactions - contains the entityname along with the total
	 *         transacted amount. There is no grouping logic here this would
	 *         only be giving the total amount, displaying it as per need would
	 *         be the callers responsibility
	 * 
	 * @throws ReportingSystemException
	 *             null values are not allowed
	 */
	@Override
	public Map<String, Integer> evaluateRanking(List<ClientInstructions> clientInstructions,
			Predicate<ClientInstructions> predicate) throws ReportingSystemException {

		LOGGER.debug("ranking the entities based on the buying/selling amount");
		rank = 1;
		if (clientInstructions == null) {
			LOGGER.error("Exception occured {} ",
					ReportingSystemResourceUtil.getValue(ReportingSystemConstants.EXCEPTION_EMPTY_CLIENT_INSTRUCTIONS));
			throw new ReportingSystemException(
					ReportingSystemResourceUtil.getValue(ReportingSystemConstants.EXCEPTION_EMPTY_CLIENT_INSTRUCTIONS));
		}

		// arrange by entity names and tradeAmount
		clientInstructions.stream().filter(predicate).forEach(c -> records.merge(c.getEntity(),
				BigDecimal.valueOf(c.getTradeAmount().doubleValue()), (v1, v2) -> v1.add(v2)));

		// calculate the rank
		Map<String, Integer> entityRankings = records.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).map(e -> e.getKey())
				.collect(Collectors.toMap(s -> s, s -> rank++));

		LOGGER.debug("entity ranking completed");
		return entityRankings;
	}

}
