package com.rbestardpino.model.api.domain;

public enum PeriodIdentifier {

	_1SEC, _2SEC, _3SEC, _4SEC, _5SEC, _6SEC, _10SEC, _15SEC, _20SEC, _30SEC, _1MIN, _2MIN, _3MIN, _4MIN, _5MIN, _6MIN,
	_10MIN, _15MIN, _20MIN, _30MIN, _1HRS, _2HRS, _3HRS, _4HRS, _6HRS, _8HRS, _12HRS, _1DAY, _2DAY, _3DAY, _5DAY, _7DAY,
	_10DAY, _1MTH, _2MTH, _3MTH, _4MTH, _6MTH, _1YRS, _2YRS, _3YRS, _4YRS, _5YRS;

	static {
		for (PeriodIdentifier period_identifier : values()) {
			if (!period_identifier.name().startsWith("_"))
				throw new IllegalStateException(
						"enum element " + period_identifier.name() + " fails to start with an underscore ('_')");
		}
	}

	public static PeriodIdentifier parse(String s) throws IllegalArgumentException {
		for (PeriodIdentifier pi : values()) {
			if (pi.toString().equals(s))
				return pi;
		}
		throw new IllegalArgumentException("s = " + s + " is an unsupported value");
	}

	@Override
	public String toString() {
		return name().substring(1);
	}
}
