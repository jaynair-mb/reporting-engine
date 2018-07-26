package com.jpmc.reportsystem.operatingstrategies;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * abstract class for operational window
 * @author jnair1
 *
 */
public abstract class PartnerOperationalWindow {
	
	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(PartnerOperationalWindow.class);			

	/**
	 * <p>
	 * Finds the operating window of the partner for which the processing needs
	 * to be done. Abstract method, all the derived partner classes should have
	 * their implementation
	 *
	 * @return operatingWindows
	 */
	protected abstract Map<DayOfWeek, Boolean> getOperatingWindow();

	/**
	 * <p>
	 * Finds the details from the pre-populated list of days, based on the input
	 * 
	 * @param date
	 *            - date that needs to be validated
	 * @return <tt>true</tt> if the selected date is a weekday and
	 *         <tt> false</tt> in case of weekend
	 * @throws IllegalArgumentException
	 *             in case the entered date contains an invalid day, which is
	 *             not part of the configured list returned from db. Exception
	 *             scenario will not happen as the input is a LocalDate,
	 *             exception would be throw while creation itself
	 */
	public boolean isSelectedDayWeekday(LocalDate date) throws IllegalArgumentException {
		boolean isWeekday = Optional.ofNullable(getOperatingWindow().get(date.getDayOfWeek())).get();
		
		LOGGER.debug("Is incoming date={} weekday={}",date, isWeekday);
		return isWeekday;
	}

	/**
	 * <p>
	 * Obtains the next working day in case the encountered date is a weekend.
	 * All the trade settlement should happen on the immediate working day, in
	 * case the entered settlement date is a weekend.
	 * 
	 * @param date
	 * @return
	 */
	public LocalDate findNextOperationalDay(LocalDate date) {
		final DayOfWeek inputDay = date.getDayOfWeek();

		// in case the given date is working date, just return this
		if (getOperatingWindow().get(inputDay)) {
			return date;
		} else {
			// otherwise look for the next working date (Recursively)
			return findNextOperationalDay(date.plusDays(1));
		}
	}

}
