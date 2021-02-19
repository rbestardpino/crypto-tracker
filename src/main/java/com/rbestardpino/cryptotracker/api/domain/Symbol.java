package com.rbestardpino.model.api.domain;

import java.time.Instant;

/**
 * Stores all the state for a symbol, as described in <a href=
 * "https://docs.coinapi.io/#list-all-symbols">https://docs.coinapi.io/#list-all-symbols</a>.
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
public class Symbol {

	/** Our symbol identifier, see table below for format description. */
	private final String symbol_id;

	/** Our identifier of the exchange where symbol is traded. */
	private final String exchange_id;

	/** Type of symbol (possible values are: SPOT, FUTURES or OPTION) */
	private final String symbol_type;

	/**
	 * FX Spot base asset identifier, for derivatives it's contact underlying (e.g.
	 * BTC for BTC/USD)
	 */
	private final String asset_id_base;

	/**
	 * FX Spot quote asset identifier, for derivatives it's contract underlying
	 * (e.g. USD for BTC/USD)
	 */
	private final String asset_id_quote;

	/**
	 * Identifier of the symbol unit asset used to denominate: transactions and
	 * order books volume (optional, if not provided then data is denominated in
	 * asset_id_base)
	 */
	private final String asset_id_unit;

	/** Date of the first data point */
	private final String data_start;

	/** Date of the last data point */
	private final String data_end;

	/** Last 1 hour volume denominated in symbol unit asset */
	private final Double volume_1hrs;

	/** Last 1 hour volume in USD */
	private final Double volume_1hrs_usd;

	/** Last 1 day volume denominated in symbol unit asset */
	private final Double volume_1day;

	/** Last 1 day volume in USD */
	private final Double volume_1day_usd;

	/** Last 1 month volume denominated in symbol unit asset */
	private final Double volume_1mth;

	/** Last 1 month volume in USD */
	private final Double volume_1mth_usd;

	/** Index identifier */
	private final String index_id;

	/** Human readable name of the index (optional) */
	private final String index_display_name;

	/** Description of the index (optional) */
	private final String index_display_description;

	/** Predetermined time of futures contract delivery date in ISO 8601 */
	private final Instant future_delivery_time;

	/**
	 * Contact size (eg. 10 BTC if future_contract_unit = 10 and
	 * future_contract_unit_asset = BTC)
	 */
	private final Integer future_contract_unit;

	/** Identifier of the asset used to denominate the contract unit */
	private final String future_contract_unit_asset;

	/**
	 * Boolean value representing option type. true for Call options, false for Put
	 * options
	 */
	private final Boolean option_type_is_call;

	/** Price at which option contract can be exercised */
	private final Double option_strike_price;

	/** Base asset amount of underlying spot which single option represents */
	private final Double option_contract_unit;

	/** Option exercise style. Can be EUROPEAN or AMERICAN */
	private final String option_exercise_style;

	/** Option contract expiration time */
	private final Instant option_expiration_time;

	public Symbol(String symbol_id, String exchange_id, String symbol_type, String asset_id_base, String asset_id_quote,
			String asset_id_unit, String data_start, String data_end, Double volume_1hrs, Double volume_1hrs_usd,
			Double volume_1day, Double volume_1day_usd, Double volume_1mth, Double volume_1mth_usd, String index_id,
			String index_display_name, String index_display_description, Instant future_delivery_time,
			Integer future_contract_unit, String future_contract_unit_asset, Boolean option_type_is_call,
			Double option_strike_price, Double option_contract_unit, String option_exercise_style,
			Instant option_expiration_time) {
		this.symbol_id = symbol_id;
		this.exchange_id = exchange_id;
		this.symbol_type = symbol_type;
		this.asset_id_base = asset_id_base;
		this.asset_id_quote = asset_id_quote;
		this.asset_id_unit = asset_id_unit;
		this.data_start = data_start;
		this.data_end = data_end;
		this.volume_1hrs = volume_1hrs;
		this.volume_1hrs_usd = volume_1hrs_usd;
		this.volume_1day = volume_1day;
		this.volume_1day_usd = volume_1day_usd;
		this.volume_1mth = volume_1mth;
		this.volume_1mth_usd = volume_1mth_usd;
		this.index_id = index_id;
		this.index_display_name = index_display_name;
		this.index_display_description = index_display_description;
		this.future_delivery_time = future_delivery_time;
		this.future_contract_unit = future_contract_unit;
		this.future_contract_unit_asset = future_contract_unit_asset;
		this.option_type_is_call = option_type_is_call;
		this.option_strike_price = option_strike_price;
		this.option_contract_unit = option_contract_unit;
		this.option_exercise_style = option_exercise_style;
		this.option_expiration_time = option_expiration_time;
	}

	public String get_symbol_id() {
		return symbol_id;
	}

	public String get_exchange_id() {
		return exchange_id;
	}

	public String get_symbol_type() {
		return symbol_type;
	}

	public String get_asset_id_base() {
		return asset_id_base;
	}

	public String get_asset_id_quote() {
		return asset_id_quote;
	}

	public String get_asset_id_unit() {
		return asset_id_unit;
	}

	public String get_data_start() {
		return data_start;
	}

	public String get_data_end() {
		return data_end;
	}

	public Double get_volume_1hrs() {
		return volume_1hrs;
	}

	public Double get_volume_1hrs_usd() {
		return volume_1hrs_usd;
	}

	public Double get_volume_1day() {
		return volume_1day;
	}

	public Double get_volume_1day_usd() {
		return volume_1day_usd;
	}

	public Double get_volume_1mth() {
		return volume_1mth;
	}

	public Double get_volume_1mth_usd() {
		return volume_1mth_usd;
	}

	public String get_index_id() {
		return index_id;
	}

	public String get_index_display_name() {
		return index_display_name;
	}

	public String get_index_display_description() {
		return index_display_description;
	}

	public Instant get_future_delivery_time() {
		return future_delivery_time;
	}

	public Integer get_future_contract_unit() {
		return future_contract_unit;
	}

	public String get_future_contract_unit_asset() {
		return future_contract_unit_asset;
	}

	public Boolean get_is_option_type_is_call() {
		return option_type_is_call;
	}

	public Double get_option_strike_price() {
		return option_strike_price;
	}

	public Double get_option_contract_unit() {
		return option_contract_unit;
	}

	public String get_option_exercise_style() {
		return option_exercise_style;
	}

	public Instant get_option_expiration_time() {
		return option_expiration_time;
	}
}
