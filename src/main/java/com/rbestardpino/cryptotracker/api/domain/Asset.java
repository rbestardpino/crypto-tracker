package com.rbestardpino.cryptotracker.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Entity
public class Asset {

	@Id
	private String assetId;

	@Column
	private String name;

	@Column
	private boolean crypto;

	@Column
	private double volume1DayUSD;

	@Column
	private double priceUSD;
}
