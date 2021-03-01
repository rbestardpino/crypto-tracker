package com.rbestardpino.cryptotracker;

import java.io.IOException;

import com.rbestardpino.cryptotracker.api.APIManager;

public class Test {
    public static void main(String[] args) {
        // for (Asset obj : APIManager.getInstance().getAllAssets()) {
        // System.out.println(obj);
        // }

        try {
            System.out.println(APIManager.getInstance().getExchangeRate("asd", "asdasdasd"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ioexcpetion");
        } catch (RuntimeException e) {
            System.out.println("runtimeexception");
        }
    }
}