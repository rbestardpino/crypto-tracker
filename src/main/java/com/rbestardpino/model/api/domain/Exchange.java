package com.rbestardpino.model.api.domain;

/**
 * Stores all the state for an exchange, as described in <a href=
 * "https://docs.coinapi.io/#list-all-exchanges">https://docs.coinapi.io/#list-all-exchanges</a>.
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
public class Exchange {

	/** Our exchange identifier */
	private final String exchange_id;

	/** Display name of the exchange */
	private final String name;

	/** Exchange website address */
	private final String website;

	public Exchange(String exchange_id, String name, String website) {
		this.exchange_id = exchange_id;
		this.name = name;
		this.website = website;
	}

	public String get_exchange_id() {
		return exchange_id;
	}

	public String get_name() {
		return name;
	}

	public String get_website() {
		return website;
	}

}
