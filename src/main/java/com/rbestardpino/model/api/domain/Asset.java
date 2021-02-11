package com.rbestardpino.model.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Asset {

	@Getter
	private final String asset_id;

	@Getter
	private final String name;

	@Getter
	private final boolean type_is_crypto;
}
