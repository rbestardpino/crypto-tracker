package com.rbestardpino.cryptotracker.api.domain;

import java.time.Instant;

/**
 * Stores all the state for a trade, as described in <a href=
 * "https://docs.coinapi.io/#json-structure">https://docs.coinapi.io/#json-structure</a>.
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
public class Trade {

	/** Our symbol identifier */
	private final String symbol_id;

	/** Time of trade reported by exchange */
	private final Instant time_exchange;

	/** Time when coinapi first received trade from exchange */
	private final Instant time_coinapi;

	/** Our trade unique identifier in form of UUIDv4 */
	private final String uuid;

	/** Price of the transaction */
	private final double price;

	/** Base asset amount traded in the transaction */
	private final double size;

	/**
	 * Aggressor side of the transaction
	 * (BUY/SELL/BUY_ESTIMATED/SELL_ESTIMATED/UNKNOWN)
	 */
	private final Taker_side taker_side;

	public Trade(String symbol_id, Instant time_exchange, Instant time_coinapi, String uuid, double price, double size,
			Taker_side taker_side) {
		this.symbol_id = symbol_id;
		this.time_exchange = time_exchange;
		this.time_coinapi = time_coinapi;
		this.uuid = uuid;
		this.price = price;
		this.size = size;
		this.taker_side = taker_side;
	}

	public String get_symbol_id() {
		return symbol_id;
	}

	public Instant get_time_exchange() {
		return time_exchange;
	}

	public Instant get_time_coinapi() {
		return time_coinapi;
	}

	public String get_uuid() {
		return uuid;
	}

	public double get_price() {
		return price;
	}

	public double get_size() {
		return size;
	}

	public Taker_side get_taker_side() {
		return taker_side;
	}

}
