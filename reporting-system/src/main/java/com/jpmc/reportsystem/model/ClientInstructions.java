package com.jpmc.reportsystem.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Currency;

/**
 * This model data represents the instructions sent by various clients to JP
 * Morgan to execute in the international market.
 * 
 * Only comments, settlementDate and instructionDate can be changed
 * 
 * @author jnair1
 *
 */
public final class ClientInstructions {

	/**
	 * Entity: A financial entity whose shares are to be bought or sold
	 */
	private final String entity;

	/**
	 * Buy/Sell flag: B – Buy – outgoing S – Sell – incoming
	 */
	private final Indicator indicator;

	/**
	 * Agreed Fx is the foreign exchange rate with respect to USD that was
	 * agreed
	 */
	private final BigDecimal agreedFx;
	/**
	 * price of individual unit transacted
	 */
	private final BigDecimal unitPrice;
	/**
	 * USD amount of a trade = Price per unit * Units * Agreed Fx
	 */
	private final BigDecimal tradeAmount;
	/**
	 * Units: Number of shares to be bought or sold
	 */
	private final BigInteger units;

	/**
	 * currency used for the transaction
	 */
	private final Currency currency;

	/**
	 * Instruction Date: Date on which the instruction was sent to JP Morgan by
	 * various clients
	 */
	private LocalDate instructionDate;
	/**
	 * Settlement Date: The date on which the client wished for the instruction
	 * to be settled with respect to Instruction Date
	 */
	private LocalDate settlementDate;

	/**
	 * comments to update the modification happening in case of settlement date
	 * change or instruction date change
	 */
	private StringBuilder comments = new StringBuilder();

	/**
	 * parameterized constructor for initializing all the fields with inputed
	 * values
	 * 
	 * @param entity
	 * @param indicator
	 * @param agreedFx
	 * @param unitPrice
	 * @param tradeAmount
	 * @param units
	 * @param currency
	 * @param instructionDate
	 * @param settlementDate
	 */
	public ClientInstructions(String entity, Indicator indicator, BigDecimal agreedFx, BigDecimal unitPrice,
			BigInteger units, Currency currency, LocalDate instructionDate, LocalDate settlementDate) {

		this.entity = entity;
		this.indicator = indicator;

		// initialize the values with ONE in case its null or negative
		// handling error scenarios as below values can never be negative
		if (agreedFx == null || agreedFx.signum() < 1)
			this.agreedFx = BigDecimal.ONE;
		else
			this.agreedFx = agreedFx;
		if (unitPrice == null || unitPrice.signum() < 1)
			this.unitPrice = BigDecimal.ONE;
		else
			this.unitPrice = unitPrice;
		if (units == null || units.signum() < 1)
			this.units = BigInteger.ONE;
		else
			this.units = units;

		// calculate the tradeamout during the initialization itself
		// as we have all the dependent variables right during initialization
		this.tradeAmount = this.getUnitPrice().multiply(BigDecimal.valueOf(this.getUnits().longValue()))
				.multiply(this.agreedFx);

		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;

	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public String getEntity() {
		return entity;
	}

	public Indicator getIndicator() {
		return indicator;
	}

	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigInteger getUnits() {
		return units;
	}

	public Currency getCurrency() {
		return currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public StringBuilder getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments.append(comments + "\t");
	}

	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * hashcode implementation
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entity.hashCode();
		result = prime * result + indicator.hashCode();
		result = prime * result + agreedFx.hashCode();
		result = prime * result + unitPrice.hashCode();
		result = prime * result + units.hashCode();
		result = prime * result + instructionDate.hashCode();
		result = prime * result + settlementDate.hashCode();
		result = prime * result + currency.hashCode();
		result = prime * result + tradeAmount.hashCode();
		return result;
	}

	/**
	 * equals implementation
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || getClass() != obj.getClass())
			return false;

		ClientInstructions instructions = (ClientInstructions) obj;

		if (!entity.equals(instructions.getEntity()))
			return false;
		if (indicator != instructions.getIndicator())
			return false;

		if (agreedFx.compareTo(instructions.getAgreedFx()) != 0)
			return false;
		if (unitPrice.compareTo(instructions.getUnitPrice()) != 0)
			return false;
		if (units.compareTo(instructions.getUnits()) != 0)
			return false;
		if (tradeAmount.compareTo(instructions.getTradeAmount()) != 0)
			return false;

		if (instructionDate != instructions.getInstructionDate())
			return false;
		if (settlementDate != instructions.getSettlementDate())
			return false;
		if (currency != instructions.getCurrency())
			return false;

		return true;
	}

	/**
	 * toString implementation of ClientInstructions
	 */
	@Override
	public String toString() {
		return "[entity=" + entity + " indicator=" + indicator + ", agreedFx=" + agreedFx + ", currency=" + currency
				+ ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate + ", units=" + units
				+ ", unitPrice=" + unitPrice + ", tradeAmount=" + tradeAmount + ", comments=" + comments.toString() + "]";
	}
}
