package com.jpmc.reportsystem.model;

/**
 * Indicator to identify whether the client instructions contains sell or buy
 * indicator
 * 
 * @author jnair1
 *
 */
public enum Indicator {

	BUY("B", "Bought"), SELL("S", "SOLD");

	private String type;
	private String description;

	private Indicator(String type, String description) {
		this.description = description;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
