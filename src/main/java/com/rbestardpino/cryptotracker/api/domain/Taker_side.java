package com.rbestardpino.model.api.domain;

/**
 * Defines all the elements of a taker_side, as described in <a href=
 * "https://docs.coinapi.io/#json-structure">https://docs.coinapi.io/#json-structure</a>.
 * <p>
 * This enum is multithread safe: it is stateless (except for the enumeration of
 * values, which are immutable).
 * <p>
 * Like all java enums, this enum is Comparable and Serializable.
 * <p>
 * 
 * @see <a href=
 *      "https://docs.oracle.com/javase/8/docs/technotes/guides/language/enums.html">Enum
 *      documentation</a>
 */
public enum Taker_side {

	BUY, SELL, BUY_ESTIMATED, SELL_ESTIMATED, UNKNOWN;

}
