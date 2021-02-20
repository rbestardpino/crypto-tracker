package com.rbestardpino.cryptotracker.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Asset {

	@Id
	private String asset_id;

	@Column
	private String name;

	@Column
	private boolean type_is_crypto;

	@Column
	private double volume_1day_usd;
}
