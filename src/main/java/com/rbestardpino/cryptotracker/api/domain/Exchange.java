package com.rbestardpino.cryptotracker.api.domain;

import java.time.Instant;

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
@Getter
@Setter
@Entity
public class Exchange {

	@Id
	private String id;

	@Column
	private String name;

	@Column
	private String website;

	@Column
	private double volume1MthUSD;

	@Column
	private Instant time;
}
