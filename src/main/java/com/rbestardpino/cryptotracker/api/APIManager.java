package com.rbestardpino.cryptotracker.api;

import java.io.Closeable;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.rbestardpino.cryptotracker.api.domain.Asset;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.api.domain.ExchangeRate;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class APIManager implements Closeable {

	private static APIManager instance = null;

	private final String key;

	private final OkHttpClient client = new OkHttpClient();

	private APIManager(String key) {
		this.key = key;
	}

	public static APIManager getInstance() {
		if (instance == null)
			instance = new APIManager("9619CF7F-A991-4149-8527-D8A856BE258F");
		return instance;
	}

	@Override
	public void close() {
		client.dispatcher().executorService().shutdown();
		client.connectionPool().evictAll();
	}

	private String request(String slug) throws IOException {
		Request request = new Request.Builder().url("https://rest.coinapi.io" + slug).addHeader("X-CoinAPI-Key", key)
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

				String message = "the response code for url is an ERROR code:" + "\n" + "\t" + "url = " + slug + "\n"
						+ "\t" + "response code = " + response.code() + "\n" + "\t" + "response body error = " + error
						+ "\n";

				throw new RuntimeException(message);
			}

			return body.string();
		}
	}

	public List<Exchange> getAllExchanges() throws IOException {
		String json = request("/v1/exchanges");
		JSONArray array = new JSONArray(json);

		List<Exchange> result = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			String exchange_id = array.getJSONObject(i).getString("exchange_id");
			String name = array.getJSONObject(i).getString("name");
			String website = array.getJSONObject(i).getString("website");
			result.add(new Exchange(exchange_id, name, website));
		}
		return result;
	}

	public Exchange getExchange(String exchange_id) throws IOException {
		String json = request("/v1/exchanges/" + exchange_id);

		JSONArray array = new JSONArray(json);
		if (array.length() == 0)
			return null;

		JSONObject object = array.getJSONObject(0);

		exchange_id = object.getString("exchange_id");
		String name = object.getString("name");
		String website = object.getString("website");

		return new Exchange(exchange_id, name, website);
	}

	public List<Asset> getAllAssets() throws IOException {
		String json = request("/v1/assets");
		JSONArray array = new JSONArray(json);

		List<Asset> result = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			String asset_id = array.getJSONObject(i).getString("asset_id");
			String name = array.getJSONObject(i).optString("name", null);
			boolean type_is_crypto = array.getJSONObject(i).getInt("type_is_crypto") != 0;
			double volume_1day_usd = array.getJSONObject(i).getDouble("volume_1day_usd");
			double price_usd = array.getJSONObject(i).getDouble("price_usd");
			result.add(new Asset(asset_id, name, type_is_crypto, volume_1day_usd, price_usd));
		}
		return result;
	}

	public Asset getAsset(String asset_id) throws IOException {
		String json = request("/v1/assets/" + asset_id);

		JSONArray array = new JSONArray(json);
		if (array.length() == 0)
			return null;

		JSONObject object = array.getJSONObject(0);

		asset_id = object.getString("asset_id");
		String name = object.optString("name", null);
		boolean type_is_crypto = object.getInt("type_is_crypto") != 0;
		double volume_1day_usd = object.getDouble("volume_1day_usd");
		double price_usd = object.getDouble("price_usd");

		return new Asset(asset_id, name, type_is_crypto, volume_1day_usd, price_usd);
	}

	public List<ExchangeRate> getAllExchangeRates(String asset_id_base) throws IOException {
		String json = request("/v1/exchangerate/" + asset_id_base);
		JSONObject object = new JSONObject(json);
		JSONArray array = object.getJSONArray("rates");

		List<ExchangeRate> result = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			String asset_id_quote = array.getJSONObject(i).getString("asset_id_quote");
			double rate = array.getJSONObject(i).getDouble("rate");
			Instant time = Instant.parse(array.getJSONObject(i).getString("time"));
			result.add(new ExchangeRate(time, asset_id_base, asset_id_quote, rate));
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

	public ExchangeRate getTimedExchangeRate(String asset_id_base, String asset_id_quote, Instant time)
			throws IOException {
		String json = request("/v1/exchangerate/" + asset_id_base + "/" + asset_id_quote + "?time=" + time);
		JSONObject object = new JSONObject(json);

		asset_id_base = object.getString("asset_id_base");
		asset_id_quote = object.getString("asset_id_quote");
		double rate = object.getDouble("rate");
		return new ExchangeRate(Instant.parse(object.getString("time")), asset_id_base, asset_id_quote, rate);
	}

}