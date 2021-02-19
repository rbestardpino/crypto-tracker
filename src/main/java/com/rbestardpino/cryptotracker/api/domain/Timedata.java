package com.rbestardpino.model.api.domain;

import java.time.Instant;

/**
 * Stores all the state for a timeseries data, as described in <a href=
 * "https://docs.coinapi.io/#latest-data">https://docs.coinapi.io/#latest-data</a>.
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
public class Timedata {

	/** Period starting time (range left inclusive) */
	private final Instant time_period_start;

	/** Period ending time (range right exclusive) */
	private final Instant time_period_end;

	/** Time of first trade inside period range */
	private final Instant time_open;

	/** Time of last trade inside period range */
	private final Instant time_close;

	/** First trade price inside period range */
	private final double price_open;

	/** Highest traded price inside period range */
	private final double price_high;

	/** Lowest traded price inside period range */
	private final double price_low;

	/** Last trade price inside period range */
	private final double price_close;

	/** Cumulative base amount traded inside period range */
	private final double volume_traded;

	/** Amount of trades executed inside period range */
	private final int trades_count;

	public Timedata(Instant time_period_start, Instant time_period_end, Instant time_open, Instant time_close,
			double price_open, double price_high, double price_low, double price_close, double volume_traded,
			int trades_count) {
		this.time_period_start = time_period_start;
		this.time_period_end = time_period_end;
		this.time_open = time_open;
		this.time_close = time_close;
		this.price_open = price_open;
		this.price_high = price_high;
		this.price_low = price_low;
		this.price_close = price_close;
		this.volume_traded = volume_traded;
		this.trades_count = trades_count;
	}

	public Instant get_time_period_start() {
		return time_period_start;
	}

	public Instant get_time_period_end() {
		return time_period_end;
	}

	public Instant get_time_open() {
		return time_open;
	}

	public Instant get_time_close() {
		return time_close;
	}

	public double get_price_open() {
		return price_open;
	}

	public double get_price_high() {
		return price_high;
	}

	public double get_price_low() {
		return price_low;
	}

	public double get_price_close() {
		return price_close;
	}

	public double get_volume_traded() {
		return volume_traded;
	}

	public int get_trades_count() {
		return trades_count;
	}

}
