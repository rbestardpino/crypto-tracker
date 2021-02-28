package com.rbestardpino.cryptotracker.api.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long exchangeRateId;

	@Column
	private Instant time;

	@Column
	private String assetIdBase;

	@Column
	private String assetIdQuote;

	@Column
	private double rate;

	public ExchangeRate(Instant time, String assetIdBase, String assetIdQuote, double rate) {
		this.time = time;
		this.assetIdBase = assetIdBase;
		this.assetIdQuote = assetIdQuote;
		this.rate = rate;
	}
}
