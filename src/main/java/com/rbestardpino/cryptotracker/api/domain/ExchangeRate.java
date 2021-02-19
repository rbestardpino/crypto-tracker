package com.rbestardpino.cryptotracker.api.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExchangeRate {

	@Id
	private int id;

	@Column
	private Instant time;

	@Column
	private String asset_id_base;

	@Column
	private String asset_id_quote;

	@Column
	private double rate;

	public ExchangeRate(Instant time, String asset_id_base, String asset_id_quote, double rate) {
		this.time = time;
		this.asset_id_base = asset_id_base;
		this.asset_id_quote = asset_id_quote;
		this.rate = rate;
	}
}
