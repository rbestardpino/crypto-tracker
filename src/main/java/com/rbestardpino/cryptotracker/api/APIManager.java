package com.rbestardpino.cryptotracker.api;

import java.io.Closeable;
import java.io.IOException;
import java.time.Instant;

import com.rbestardpino.cryptotracker.api.domain.Asset;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.api.domain.ExchangeRate;
import com.rbestardpino.cryptotracker.api.domain.Period;
import com.rbestardpino.cryptotracker.api.domain.PeriodIdentifier;
import com.rbestardpino.cryptotracker.api.domain.Quote;
import com.rbestardpino.cryptotracker.api.domain.Quote_with_trade;
import com.rbestardpino.cryptotracker.api.domain.Symbol;
import com.rbestardpino.cryptotracker.api.domain.Taker_side;
import com.rbestardpino.cryptotracker.api.domain.Timedata;
import com.rbestardpino.cryptotracker.api.domain.Trade;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class APIManager implements Closeable {

	private final String key;

	private final OkHttpClient client = new OkHttpClient();

	public APIManager(String key) {
		this.key = key;
	}

	@Override
	public void close() {
		client.dispatcher().executorService().shutdown();
		client.connectionPool().evictAll();
	}

	private String request(String url) throws IOException {
		Request request = new Request.Builder().url("https://rest.coinapi.io" + url).addHeader("X-CoinAPI-Key", key)
				.build();

		try (Response response = client.newCall(request).execute()) {
			ResponseBody body = response.body();

			if (response.code() >= 400) {
				String error;
				try {
					JSONObject object = new JSONObject(body.string());
					if (object.has("error")) {
						error = object.getString("error");
					} else {
						error = "[NOTHING: response has no value for \"error\"]";
					}
				} catch (Throwable t) {
					error = "[FAILED to extract response's \"error\" value because this Throwable was raised: " + t
							+ "]";
				}

				String message = "the response code for url is an ERROR code:" + "\n" + "\t" + "url = " + url + "\n"
						+ "\t" + "response code = " + response.code() + "\n" + "\t" + "response body error = " + error
						+ "\n";

				throw new RuntimeException(message);
			}

			return body.string();
		}
	}

	public Exchange[] listAllExchanges() throws IOException {
		String json = request("/v1/exchanges");
		JSONArray array = new JSONArray(json);

		Exchange[] result = new Exchange[array.length()];
		for (int i = 0; i < array.length(); i++) {
			String exchange_id = array.getJSONObject(i).getString("exchange_id");
			String name = array.getJSONObject(i).getString("name");
			String website = array.getJSONObject(i).getString("website");
			result[i] = new Exchange(exchange_id, name, website);
		}
		return result;
	}

	public Asset[] listAllAssets() throws IOException {
		String json = request("/v1/assets");
		JSONArray array = new JSONArray(json);

		Asset[] result = new Asset[array.length()];
		for (int i = 0; i < array.length(); i++) {
			String asset_id = array.getJSONObject(i).getString("asset_id");
			String name = array.getJSONObject(i).optString("name", null);
			boolean type_is_crypto = array.getJSONObject(i).getInt("type_is_crypto") != 0;
			result[i] = new Asset(asset_id, name, type_is_crypto);
		}
		return result;
	}

	public Symbol[] listAllSymbols() throws IOException {
		String json = request("/v1/symbols");
		JSONArray array = new JSONArray(json);

		Symbol[] result = new Symbol[array.length()];
		for (int i = 0; i < array.length(); i++) {
			String symbol_id = array.getJSONObject(i).getString("symbol_id");
			String exchange_id = array.getJSONObject(i).getString("exchange_id");
			String symbol_type = array.getJSONObject(i).getString("symbol_type");
			String asset_id_base = UtilsJSON.parseStringFromJson("asset_id_base", array.getJSONObject(i));
			String asset_id_quote = UtilsJSON.parseStringFromJson("asset_id_quote", array.getJSONObject(i));
			String asset_id_unit = UtilsJSON.parseStringFromJson("asset_id_unit", array.getJSONObject(i));
			String data_start = UtilsJSON.parseStringFromJson("data_start", array.getJSONObject(i));
			String data_end = UtilsJSON.parseStringFromJson("data_end", array.getJSONObject(i));
			Double volume_1hrs = UtilsJSON.parseDoubleFromJson("volume_1hrs", array.getJSONObject(i));
			Double volume_1hrs_usd = UtilsJSON.parseDoubleFromJson("volume_1hrs_usd", array.getJSONObject(i));
			Double volume_1day = UtilsJSON.parseDoubleFromJson("volume_1day", array.getJSONObject(i));
			Double volume_1day_usd = UtilsJSON.parseDoubleFromJson("volume_1day_usd", array.getJSONObject(i));
			Double volume_1mth = UtilsJSON.parseDoubleFromJson("volume_1mth", array.getJSONObject(i));
			Double volume_1mth_usd = UtilsJSON.parseDoubleFromJson("volume_1mth_usd", array.getJSONObject(i));
			String index_id = UtilsJSON.parseStringFromJson("index_id", array.getJSONObject(i));
			String index_display_name = UtilsJSON.parseStringFromJson("index_display_name", array.getJSONObject(i));
			String index_display_description = UtilsJSON.parseStringFromJson("index_display_description",
					array.getJSONObject(i));
			Instant future_delivery_time = UtilsJSON.parseInstantFromJson("future_delivery_time",
					array.getJSONObject(i));
			Integer future_contract_unit = UtilsJSON.parseIntegerFromJson("future_contract_unit",
					array.getJSONObject(i));
			String future_contract_unit_asset = UtilsJSON.parseStringFromJson("future_contract_unit_asset",
					array.getJSONObject(i));
			Boolean option_type_is_call = UtilsJSON.parseBooleanFromJson("option_type_is_call", array.getJSONObject(i));
			Double option_strike_price = UtilsJSON.parseDoubleFromJson("option_strike_price", array.getJSONObject(i));
			Double option_contract_unit = UtilsJSON.parseDoubleFromJson("option_contract_unit", array.getJSONObject(i));
			String option_exercise_style = UtilsJSON.parseStringFromJson("option_exercise_style",
					array.getJSONObject(i));
			Instant option_expiration_time = UtilsJSON.parseInstantFromJson("option_expiration_time",
					array.getJSONObject(i));

			result[i] = new Symbol(symbol_id, exchange_id, symbol_type, asset_id_base, asset_id_quote, asset_id_unit,
					data_start, data_end, volume_1hrs, volume_1hrs_usd, volume_1day, volume_1day_usd, volume_1mth,
					volume_1mth_usd, index_id, index_display_name, index_display_description, future_delivery_time,
					future_contract_unit, future_contract_unit_asset, option_type_is_call, option_strike_price,
					option_contract_unit, option_exercise_style, option_expiration_time);
		}
		return result;
	}

	public ExchangeRate getExchangeRate(String asset_id_base, String asset_id_quote) throws IOException {
		String json = request("/v1/exchangerate/" + asset_id_base + "/" + asset_id_quote);
		JSONObject object = new JSONObject(json);

		asset_id_base = object.getString("asset_id_base");
		asset_id_quote = object.getString("asset_id_quote");
		double rate = object.getDouble("rate");

		return new ExchangeRate(Instant.parse(object.getString("time")), asset_id_base, asset_id_quote, rate);
	}

	public ExchangeRate getExchangeRate(String asset_id_base, String asset_id_quote, Instant time) throws IOException {
		String json = request("/v1/exchangerate/" + asset_id_base + "/" + asset_id_quote + "?time=" + time);
		JSONObject object = new JSONObject(json);

		asset_id_base = object.getString("asset_id_base");
		asset_id_quote = object.getString("asset_id_quote");
		double rate = object.getDouble("rate");
		return new ExchangeRate(Instant.parse(object.getString("time")), asset_id_base, asset_id_quote, rate);
	}

	public ExchangeRate[] getAllExchangeRates(String asset_id_base) throws IOException {
		String json = request("/v1/exchangerate/" + asset_id_base);
		JSONObject object = new JSONObject(json);
		JSONArray array = object.getJSONArray("rates");

		ExchangeRate[] result = new ExchangeRate[array.length()];
		for (int i = 0; i < array.length(); i++) {
			String asset_id_quote = array.getJSONObject(i).getString("asset_id_quote");
			double rate = array.getJSONObject(i).getDouble("rate");
			Instant time = Instant.parse(array.getJSONObject(i).getString("time"));
			result[i] = new ExchangeRate(time, asset_id_base, asset_id_quote, rate);
		}
		return result;
	}

	public Period[] ohlcv_list_all_periods() throws IOException {
		String json = request("/v1/ohlcv/periods");
		JSONArray array = new JSONArray(json);

		Period[] result = new Period[array.length()];
		for (int i = 0; i < array.length(); i++) {
			PeriodIdentifier period_id = PeriodIdentifier.parse(array.getJSONObject(i).getString("period_id"));
			int length_seconds = array.getJSONObject(i).getInt("length_seconds");
			int length_months = array.getJSONObject(i).getInt("length_months");
			int unit_count = array.getJSONObject(i).getInt("unit_count");
			String unit_name = array.getJSONObject(i).getString("unit_name");
			String display_name = array.getJSONObject(i).getString("display_name");

			result[i] = new Period(period_id, length_seconds, length_months, unit_count, unit_name, display_name);
		}
		return result;
	}

	private Timedata[] parse_timeseries(JSONArray array) {
		Timedata[] result = new Timedata[array.length()];
		for (int i = 0; i < array.length(); i++) {
			Instant time_period_start = Instant.parse(array.getJSONObject(i).getString("time_period_start"));
			Instant time_period_end = Instant.parse(array.getJSONObject(i).getString("time_period_end"));
			Instant time_open = Instant.parse(array.getJSONObject(i).getString("time_open"));
			Instant time_close = Instant.parse(array.getJSONObject(i).getString("time_close"));
			double price_open = array.getJSONObject(i).getDouble("price_open");
			double price_high = array.getJSONObject(i).getDouble("price_high");
			double price_low = array.getJSONObject(i).getDouble("price_low");
			double price_close = array.getJSONObject(i).getDouble("price_close");
			double volume_traded = array.getJSONObject(i).getDouble("volume_traded");
			int trades_count = array.getJSONObject(i).getInt("trades_count");

			result[i] = new Timedata(time_period_start, time_period_end, time_open, time_close, price_open, price_high,
					price_low, price_close, volume_traded, trades_count);
		}
		return result;
	}

	public Timedata[] ohlcv_get_latest_timeseries(String symbol_id, PeriodIdentifier period_id) throws IOException {
		String json = request("/v1/ohlcv/" + symbol_id + "/latest?period_id=" + period_id);
		JSONArray array = new JSONArray(json);
		return parse_timeseries(array);
	}

	public Timedata[] ohlcv_get_latest_timeseries(String symbol_id, PeriodIdentifier period_id, int limit)
			throws IOException {
		String json = request("/v1/ohlcv/" + symbol_id + "/latest?period_id=" + period_id + "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_timeseries(array);
	}

	public Timedata[] ohlcv_get_historical_timeseries(String symbol_id, PeriodIdentifier period_id, Instant time_start)
			throws IOException {
		String json = request(
				"/v1/ohlcv/" + symbol_id + "/history?period_id=" + period_id + "&time_start=" + time_start);
		JSONArray array = new JSONArray(json);
		return parse_timeseries(array);
	}

	public Timedata[] ohlcv_get_historical_timeseries(String symbol_id, PeriodIdentifier period_id, Instant time_start,
			Instant time_end) throws IOException {
		String json = request("/v1/ohlcv/" + symbol_id + "/history?period_id=" + period_id + "&time_start=" + time_start
				+ "&time_end=" + time_end);
		JSONArray array = new JSONArray(json);
		return parse_timeseries(array);
	}

	public Timedata[] ohlcv_get_historical_timeseries(String symbol_id, PeriodIdentifier period_id, Instant time_start,
			int limit) throws IOException {
		String json = request("/v1/ohlcv/" + symbol_id + "/history?period_id=" + period_id + "&time_start=" + time_start
				+ "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_timeseries(array);
	}

	public Timedata[] ohlcv_get_historical_timeseries(String symbol_id, PeriodIdentifier period_id, Instant time_start,
			Instant time_end, int limit) throws IOException {
		String json = request("/v1/ohlcv/" + symbol_id + "/history?" + "period_id=" + period_id + "&time_start="
				+ time_start + "&time_end=" + time_end + "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_timeseries(array);
	}

	private Trade parse_trade(JSONObject object, String symbol_id) {
		if (symbol_id == null)
			symbol_id = object.getString("symbol_id");
		Instant time_exchange = Instant.parse(object.getString("time_exchange"));
		Instant time_coinapi = Instant.parse(object.getString("time_coinapi"));
		String uuid = object.getString("uuid");
		double price = object.getDouble("price");
		double size = object.getDouble("size");
		Taker_side taker_side = Taker_side.valueOf(object.getString("taker_side"));

		return new Trade(symbol_id, time_exchange, time_coinapi, uuid, price, size, taker_side);
	}

	private Trade[] parse_trades(JSONArray array) {
		Trade[] result = new Trade[array.length()];

		for (int i = 0; i < array.length(); i++) {
			result[i] = parse_trade(array.getJSONObject(i), null);
		}
		return result;
	}

	private Quote_with_trade parse_quote_with_trade(JSONObject object) {
		String symbol_id = object.getString("symbol_id");
		Instant time_exchange = Instant.parse(object.getString("time_exchange"));
		Instant time_coinapi = Instant.parse(object.getString("time_coinapi"));
		double ask_price = object.getDouble("ask_price");
		double ask_size = object.getDouble("ask_size");
		double bid_price = object.getDouble("bid_price");
		double bid_size = object.getDouble("bid_size");
		Trade last_trade = null;
		if (object.has("last_trade"))
			last_trade = parse_trade(object.getJSONObject("last_trade"), symbol_id);
		return new Quote_with_trade(symbol_id, time_exchange, time_coinapi, ask_price, ask_size, bid_price, bid_size,
				last_trade);
	}

	private Quote parse_quote(JSONObject object) {
		String symbol_id = object.getString("symbol_id");
		Instant time_exchange = Instant.parse(object.getString("time_exchange"));
		Instant time_coinapi = Instant.parse(object.getString("time_coinapi"));
		double ask_price = object.getDouble("ask_price");
		double ask_size = object.getDouble("ask_size");
		double bid_price = object.getDouble("bid_price");
		double bid_size = object.getDouble("bid_size");
		return new Quote(symbol_id, time_exchange, time_coinapi, ask_price, ask_size, bid_price, bid_size);
	}

	private Quote_with_trade[] parse_quotes_with_trade(JSONArray array) {
		Quote_with_trade[] result = new Quote_with_trade[array.length()];
		for (int i = 0; i < array.length(); i++) {
			result[i] = parse_quote_with_trade(array.getJSONObject(i));
		}
		return result;
	}

	private Quote[] parse_quotes(JSONArray array) {
		Quote[] result = new Quote[array.length()];
		for (int i = 0; i < array.length(); i++) {
			result[i] = parse_quote(array.getJSONObject(i));
		}
		return result;
	}

	public Trade[] trades_get_latest_data() throws IOException {
		String json = request("/v1/trades/latest");
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_latest_data(int limit) throws IOException {
		String json = request("/v1/trades/latest?limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_latest_data(String symbol_id) throws IOException {
		String json = request("/v1/trades/" + symbol_id + "/latest");
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_latest_data(String symbol_id, int limit) throws IOException {
		String json = request("/v1/trades/" + symbol_id + "/latest?limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_historical_data(String symbol_id, Instant time_start) throws IOException {
		String json = request("/v1/trades/" + symbol_id + "/history?time_start=" + time_start);
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_historical_data(String symbol_id, Instant time_start, int limit) throws IOException {
		String json = request("/v1/trades/" + symbol_id + "/history?time_start=" + time_start + "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_historical_data(String symbol_id, Instant time_start, Instant time_end)
			throws IOException {
		String json = request(
				"/v1/trades/" + symbol_id + "/history?time_start=" + time_start + "&time_end=" + time_end);
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Trade[] trades_get_historical_data(String symbol_id, Instant time_start, Instant time_end, int limit)
			throws IOException {
		String json = request("/v1/trades/" + symbol_id + "/history?time_start=" + time_start + "&time_end=" + time_end
				+ "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_trades(array);
	}

	public Quote_with_trade[] quotes_get_for_all_symbols() throws IOException {
		String json = request("/v1/quotes/current");
		JSONArray array = new JSONArray(json);
		return parse_quotes_with_trade(array);
	}

	public Quote_with_trade quotes_get_for_symbol(String symbol_id) throws IOException {
		String json = request("/v1/quotes/" + symbol_id + "/current");
		JSONObject object = new JSONObject(json);
		return parse_quote_with_trade(object);
	}

	public Quote[] quotes_get_latest_data() throws IOException {
		String json = request("/v1/quotes/latest");
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_latest_data(int limit) throws IOException {
		String json = request("/v1/quotes/latest?limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_latest_data(String symbol_id) throws IOException {
		String json = request("/v1/quotes/" + symbol_id + "/latest");
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_latest_data(String symbol_id, int limit) throws IOException {
		String json = request("/v1/quotes/" + symbol_id + "/latest?limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_historical_data(String symbol_id, Instant time_start) throws IOException {
		String json = request("/v1/quotes/" + symbol_id + "/history?time_start=" + time_start);
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_historical_data(String symbol_id, Instant time_start, int limit) throws IOException {
		String json = request("/v1/quotes/" + symbol_id + "/history?time_start=" + time_start + "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_historical_data(String symbol_id, Instant time_start, Instant time_end)
			throws IOException {
		String json = request(
				"/v1/quotes/" + symbol_id + "/history?time_start=" + time_start + "&time_end=" + time_end);
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}

	public Quote[] quotes_get_historical_data(String symbol_id, Instant time_start, Instant time_end, int limit)
			throws IOException {
		String json = request("/v1/quotes/" + symbol_id + "/history?time_start=" + time_start + "&time_end=" + time_end
				+ "&limit=" + limit);
		JSONArray array = new JSONArray(json);
		return parse_quotes(array);
	}
}
