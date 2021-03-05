package com.rbestardpino.cryptotracker.api;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.rbestardpino.cryptotracker.App;
import com.rbestardpino.cryptotracker.api.domain.Asset;
import com.rbestardpino.cryptotracker.api.domain.Exchange;
import com.rbestardpino.cryptotracker.api.domain.ExchangeRate;
import com.rbestardpino.cryptotracker.utils.PropertiesReader;

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
			instance = new APIManager(new PropertiesReader("private.properties").read("coinapi_token"));
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
			result.add(new Exchange(exchange_id, name, website, Instant.now()));
		}
		return result;
	}

	public Exchange getExchange(String exchange_id) throws IOException {

		App.database.createEntityManager();
		App.database.beginTransaction();

		Exchange exchange = App.database.find(Exchange.class, exchange_id);

		if (exchange == null) {
			String json = request("/v1/exchanges/" + exchange_id);

			JSONArray array = new JSONArray(json);
			if (array.length() == 0)
				return null;

			JSONObject object = array.getJSONObject(0);

			exchange_id = object.getString("exchange_id");
			String name = object.getString("name");
			String website = object.getString("website");

			exchange = new Exchange(exchange_id, name, website, Instant.now());
			App.database.persist(exchange);
		}

		if (Duration.between(exchange.getTime(), Instant.now()).toDays() > 60) {
			String json = request("/v1/exchanges/" + exchange_id);

			JSONArray array = new JSONArray(json);
			if (array.length() == 0)
				return null;

			JSONObject object = array.getJSONObject(0);

			exchange.setName(object.getString("name"));
			exchange.setWebsite(object.getString("website"));
			exchange.setTime(Instant.now());

			App.database.merge(exchange);
		}
		App.database.commit();
		App.database.close();

		return exchange;
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
			result.add(new Asset(asset_id, name, type_is_crypto, volume_1day_usd, price_usd, Instant.now()));
		}
		return result;
	}

	public Asset getAsset(String asset_id) throws IOException {
		App.database.createEntityManager();
		App.database.beginTransaction();

		Asset asset = App.database.find(Asset.class, asset_id);

		if (asset == null) {
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
			asset = new Asset(asset_id, name, type_is_crypto, volume_1day_usd, price_usd, Instant.now());
			App.database.persist(asset);
		}

		if (Duration.between(asset.getTime(), Instant.now()).toMinutes() > 3) {
			String json = request("/v1/assets/" + asset_id);

			JSONArray array = new JSONArray(json);
			if (array.length() == 0)
				return null;

			JSONObject object = array.getJSONObject(0);

			asset.setName(object.optString("name", null));
			asset.setCrypto(object.getInt("type_is_crypto") != 0);
			asset.setVolume1DayUSD(object.getDouble("volume_1day_usd"));
			asset.setPriceUSD(object.getDouble("price_usd"));
			asset.setTime(Instant.now());

			App.database.merge(asset);
		}
		App.database.commit();
		App.database.close();

		return asset;
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