package com.rbestardpino.cryptotracker;

import java.io.IOException;

import com.rbestardpino.cryptotracker.api.APIManager;

public class Test {
    public static void main(String[] args) throws IOException {
        // for (Asset obj : APIManager.getInstance().getAllAssets()) {
        // System.out.println(obj);
        // }

        System.out.println(APIManager.getInstance().getExchangeRate("BTC", "USD"));
    }
}