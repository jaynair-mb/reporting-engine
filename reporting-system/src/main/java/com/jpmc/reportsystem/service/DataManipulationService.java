package com.jpmc.reportsystem.service;

import com.jpmc.reportsystem.model.ClientInstructions;

/**
 * Data Manipulation Service interface that holds all the manipulation methods
 * that can be performed on the client instructions
 * 
 * @author jnair1
 *
 */
public interface DataManipulationService {

	/**
	 * <p>
	 * Takes the list of client-instructions and works on the predicates to sum
	 * up the trade amounts This would compute on the entire dataset without any
	 * date restriction
	 * 
	 * @param clientInstructions
	 * @param predicate
	 * @return
	 */
	public ClientInstructions updateSettlementDates(ClientInstructions instruction);

	
}
