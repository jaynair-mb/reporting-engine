package com.jpmc.reportsystem.operatingstrategies;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Partner configured for the currency types other than AED and SAR. This would
 * be acting as a default partner for any other currency type
 * 
 * @author jnair1
 *
 */
public class DefaultPartnerOperationalWindow extends PartnerOperationalWindow {

	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(DefaultPartnerOperationalWindow.class);

	/**
	 * making a volatile OperationalWindow object
	 */
	private static volatile PartnerOperationalWindow window;

	/**
	 * making OtherOperationalWindow constructor private to avoid direct
	 * initialization and making use of singleton
	 */
	private DefaultPartnerOperationalWindow() {
	}

	/**
	 * singleton instance of OperationalWindow
	 * 
	 * @return
	 */
	public static PartnerOperationalWindow getInstance() {

		return Optional.ofNullable(window).orElseGet(() -> {
			window = new DefaultPartnerOperationalWindow();
			return window;
		});
	}

	/**
	 * <p>
	 * Finds the operating window of the other partners for which the processing
	 * needs to be done. Implementation method of parent's abstract method
	 *
	 * @return operatingWindows
	 */
	@Override
	protected Map<DayOfWeek, Boolean> getOperatingWindow() {
		LOGGER.debug("get all operational windows of Default partner");
		Map<DayOfWeek, Boolean> otherWindow = new HashMap<DayOfWeek, Boolean>();
		otherWindow.put(DayOfWeek.MONDAY, true);
		otherWindow.put(DayOfWeek.TUESDAY, true);
		otherWindow.put(DayOfWeek.WEDNESDAY, true);
		otherWindow.put(DayOfWeek.THURSDAY, true);
		otherWindow.put(DayOfWeek.FRIDAY, true);
		otherWindow.put(DayOfWeek.SATURDAY, false);
		otherWindow.put(DayOfWeek.SUNDAY, false);

		return otherWindow;
	}

}
