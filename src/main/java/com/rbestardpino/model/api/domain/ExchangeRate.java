package com.rbestardpino.model.api.domain;

import java.time.Instant;

public class ExchangeRate {

	private final Instant time;

	private final String asset_id_base;

	private final String asset_id_quote;

	private final double rate;

	public ExchangeRate(Instant time, String asset_id_base, String asset_id_quote, double rate) {
		this.time = time;
		this.asset_id_base = asset_id_base;
		this.asset_id_quote = asset_id_quote;
		this.rate = rate;
	}

	public Instant get_time() {
		return time;
	}

	public String get_asset_id_base() {
		return asset_id_base;
	}

	public String get_asset_id_quote() {
		return asset_id_quote;
	}

	public double get_rate() {
		return rate;
	}

}
