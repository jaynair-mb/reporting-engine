package com.jpmc.reportsystem.operatingstrategies;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Partner configured for the currency of type AED and SAR
 * 
 * @author jnair1
 *
 */
public class AEDPartnerOperationalWindow extends PartnerOperationalWindow {
	
	/**
	 * Logger
	 */
	private Logger LOGGER = LoggerFactory.getLogger(AEDPartnerOperationalWindow.class);
	
	/**
	 * making a volatile OperationalWindow object
	 */
	private static volatile PartnerOperationalWindow window;
	
	/**
	 * making AEDOperationalWindow constructor private to avoid
	 * direct initialization and making use of singleton
	 */
	private AEDPartnerOperationalWindow(){}
	
	/**
	 * singleton instance of OperationalWindow
	 * @return
	 */
	public static PartnerOperationalWindow getInstance() {
		
		return Optional.ofNullable(window).orElseGet(() -> {
			window = new AEDPartnerOperationalWindow();
			return window;
		});
	}
	

	/**
	 * <p>
	 * Finds the operating window of the AED partner for which the processing
	 * needs to be done.
	 * Implementation method of parent's abstract method
	 *
	 * @return operatingWindows
	 */
	@Override
	protected Map<DayOfWeek, Boolean> getOperatingWindow() {
		LOGGER.debug("get all operational windows of AED partner");
		Map<DayOfWeek, Boolean> aedWindow = new HashMap<DayOfWeek, Boolean>();
		aedWindow.put(DayOfWeek.SUNDAY, true);
		aedWindow.put(DayOfWeek.MONDAY, true);
		aedWindow.put(DayOfWeek.TUESDAY, true);
		aedWindow.put(DayOfWeek.WEDNESDAY, true);
		aedWindow.put(DayOfWeek.THURSDAY, true);
		aedWindow.put(DayOfWeek.FRIDAY, false);
		aedWindow.put(DayOfWeek.SATURDAY, false);
		
		return aedWindow;
	}

}
