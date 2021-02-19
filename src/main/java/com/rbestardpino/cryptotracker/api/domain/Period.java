package com.rbestardpino.cryptotracker.api.domain;

/**
 * Stores all the state for a period, as described in <a href=
 * "https://docs.coinapi.io/#list-all-periods">https://docs.coinapi.io/#list-all-periods</a>.
 * <p>
 * This class is multithread safe: it is <a href=
 * "http://www.ibm.com/developerworks/java/library/j-jtp02183.html">immutable</a>.
 * In particular, it is always <a href=
 * "http://www.ibm.com/developerworks/java/library/j-jtp0618.html">properly
 * constructed</a>, all of its fields are final, and none of their state can be
 * changed after construction. See p. 53 of
 * <a href="http://www.javaconcurrencyinpractice.com">Java Concurrency In
 * Practice</a> for more discussion.
 */
public class Period {

	/** Period identifier, used in other API calls */
	private final PeriodIdentifier period_id;

	/** Seconds part of period length */
	private final int length_seconds;

	/** Months part of period length */
	private final int length_months;

	/** Period length in units */
	private final int unit_count;

	/** Type of unit (second/minute/hour/day/month/year) */
	private final String unit_name;

	/** Display name of period length */
	private final String display_name;

	public Period(PeriodIdentifier period_id, int length_seconds, int length_months, int unit_count, String unit_name,
			String display_name) {
		this.period_id = period_id;
		this.length_seconds = length_seconds;
		this.length_months = length_months;
		this.unit_count = unit_count;
		this.unit_name = unit_name;
		this.display_name = display_name;
	}

	public PeriodIdentifier get_period_id() {
		return period_id;
	}

	public int get_length_seconds() {
		return length_seconds;
	}

	public int get_length_months() {
		return length_months;
	}

	public int get_unit_count() {
		return unit_count;
	}

	public String get_unit_name() {
		return unit_name;
	}

	public String get_display_name() {
		return display_name;
	}

}
