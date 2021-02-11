package com.rbestardpino.model.api.domain;

import java.time.Instant;

/**
 * Stores all the state for a quote with a trade, as described in <a href=
 * "https://docs.coinapi.io/#current-data32">https://docs.coinapi.io/#current-data32</a>.
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
public class Quote_with_trade extends Quote {

	/** Last executed transaction */
	private final Trade last_trade;

	public Quote_with_trade(String symbol_id, Instant time_exchange, Instant time_coinapi, double ask_price,
			double ask_size, double bid_price, double bid_size, Trade last_trade) {
		super(symbol_id, time_exchange, time_coinapi, ask_price, ask_size, bid_price, bid_size);
		this.last_trade = last_trade;
	}

	@Override
	public boolean has_last_trade() {
		return last_trade != null;
	}

	public Trade get_last_trade() {
		return last_trade;
	}

}
