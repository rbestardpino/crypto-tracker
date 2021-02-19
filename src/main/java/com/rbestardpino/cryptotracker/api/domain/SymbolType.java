package com.rbestardpino.cryptotracker.api.domain;

/**
 * Symbol types list (enumeration of symbol_type output variable)
 */
public class SymbolType {

    /**
     * FX Spot. Agreement to exchange one asset for another one (e.g. Buy BTC for
     * USD)
     */
    public static final String SPOT = "SPOT";

    /**
     * Futures contract. FX Spot derivative contract where traders agree to trade fx
     * spot at predetermined future time
     */
    public static final String FUTURES = "FUTURES";

    /**
     * Option contract. FX Spot derivative contract where traders agree to trade
     * right to require buy or sell of fx spot at agreed price on exercise date
     */
    public static final String OPTION = "OPTION";

    /**
     * Perpetual contract. FX Spot derivative contract where traders agree to trade
     * fx spot continously without predetermined future delivery time
     */
    public static final String PERPETUAL = "PERPETUAL";

    /**
     * Index. Statistical composite that measures changes in the economy or markets.
     */
    public static final String INDEX = "INDEX";
}
