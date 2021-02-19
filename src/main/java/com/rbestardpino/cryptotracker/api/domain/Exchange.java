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
@Getter
@Setter
@Entity
public class Exchange {

	@Id
	private String exchange_id;

	@Column
	private String name;

	@Column
	private String website;
}
