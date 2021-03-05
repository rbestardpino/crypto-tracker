package com.rbestardpino.cryptotracker.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private Properties properties = new Properties();

    public PropertiesReader(String propFile) {
        ClassLoader loader = getClass().getClassLoader();
        InputStream stream = loader.getResourceAsStream(propFile);
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read(String key) {
        return properties.getProperty(key);
    }
}
