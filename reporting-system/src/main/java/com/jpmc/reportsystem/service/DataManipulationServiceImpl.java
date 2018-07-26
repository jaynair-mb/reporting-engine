package com.jpmc.reportsystem.service;

import java.time.LocalDate;
import java.util.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.reportsystem.model.ClientInstructions;
import com.jpmc.reportsystem.operatingstrategies.AEDPartnerOperationalWindow;
import com.jpmc.reportsystem.operatingstrategies.DefaultPartnerOperationalWindow;
import com.jpmc.reportsystem.operatingstrategies.PartnerOperationalWindow;
import com.jpmc.reportsystem.util.ReportingSystemConstants;

/**
 * Data Manipulation Service interface that holds all the manipulation methods
 * that can be performed on the client instructions
 * 
 * @author jnair1
 *
 */
public class DataManipulationServiceImpl implements DataManipulationService {

	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(DataManipulationService.class);

	/**
	 * <p>
	 * Takes the client-instructions and checks for the settlement date. If the
	 * date is valid workday for the partner, then sets the same date, in case
	 * of weekend, changes the same to valid workday
	 * 
	 * @param instruction
	 * @return
	 */
	public ClientInstructions updateSettlementDates(ClientInstructions instruction) {
		LOGGER.debug("updating the settlement date in case of weekend for Currency={} and for settlementDate={}",
				instruction.getCurrency(), instruction.getSettlementDate());
		PartnerOperationalWindow operationalWindow = getWorkingDaysStrategy(instruction.getCurrency());
		LocalDate localDate = instruction.getSettlementDate();
		if (!operationalWindow.isSelectedDayWeekday(localDate)) {
			instruction.setSettlementDate(operationalWindow.findNextOperationalDay(localDate));
			instruction.setComments(
					" Settlement date changed from " + localDate + " to " + instruction.getSettlementDate());
			LOGGER.info(" Settlement date changed from previousDate={} to newDate={} ", localDate,
					instruction.getSettlementDate());
		}

		return instruction;
	}

	/**
	 * this would return the appropriate instance of the partner.
	 * Currently we have only two partners configured, one for AED\SAR
	 * and one for the remaining currencies 
	 * 
	 * @param currency
	 * @return
	 */
	private static PartnerOperationalWindow getWorkingDaysStrategy(Currency currency) {
		if ((currency.getCurrencyCode().equals(ReportingSystemConstants.CURRENCY_AED))
				|| (currency.getCurrencyCode().equals(ReportingSystemConstants.CURRENCY_SAR))) {
			return AEDPartnerOperationalWindow.getInstance();
		}
		return DefaultPartnerOperationalWindow.getInstance();
	}

}
