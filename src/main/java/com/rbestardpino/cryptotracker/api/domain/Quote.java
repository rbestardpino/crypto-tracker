package com.rbestardpino.model.api.domain;

import java.time.Instant;

/**
 * Stores all the state for a latest quote, as described in <a href=
 * "https://docs.coinapi.io/#latest-data33">https://docs.coinapi.io/#latest-data33</a>.
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
public class Quote {

	/** Our symbol identifier */
	private final String symbol_id;

	/** Exchange time of orderbook */
	private final Instant time_exchange;

	/** CoinAPI time when orderbook received from exchange */
	private final Instant time_coinapi;

	/** Best asking price */
	private final double ask_price;

	/** Volume resting on best ask */
	private final double ask_size;

	/** Best bidding price */
	private final double bid_price;

	/** Volume resting on best bid */
	private final double bid_size;

	public Quote(String symbol_id, Instant time_exchange, Instant time_coinapi, double ask_price, double ask_size,
			double bid_price, double bid_size) {
		this.symbol_id = symbol_id;
		this.time_exchange = time_exchange;
		this.time_coinapi = time_coinapi;
		this.ask_price = ask_price;
		this.ask_size = ask_size;
		this.bid_price = bid_price;
		this.bid_size = bid_size;
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

	public double get_ask_price() {
		return ask_price;
	}

	public double get_ask_size() {
		return ask_size;
	}

	public double get_bid_price() {
		return bid_price;
	}

	public double get_bid_size() {
		return bid_size;
	}

	public boolean has_last_trade() {
		return false;
	}

}
