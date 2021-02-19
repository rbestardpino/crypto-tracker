package com.rbestardpino.model.api;

import org.json.JSONObject;

import java.time.Instant;

/**
 * Helper class to handle the json.
 */
public class UtilsJSON {

    /**
     * Converting json values to string.
     * 
     * @param name
     * @param value
     * @return
     */
    public static String parseStringFromJson(String name, JSONObject value) {

        if (value.has(name)) {
            return value.getString(name);
        }
        return null;
    }

    /**
     * Converting json values to double.
     * 
     * @param name
     * @param value
     * @return
     */
    public static Double parseDoubleFromJson(String name, JSONObject value) {

        if (value.has(name)) {
            return value.getDouble(name);
        }
        return null;
    }

    /**
     * Converting json values to boolean.
     * 
     * @param name
     * @param value
     * @return
     */
    public static Boolean parseBooleanFromJson(String name, JSONObject value) {

        if (value.has(name)) {
            return value.getBoolean(name);
        }
        return null;
    }

    /**
     * Converting json values to instatnt.
     * 
     * @param name
     * @param value
     * @return
     */
    public static Instant parseInstantFromJson(String name, JSONObject value) {

        if (value.has(name)) {
            return Instant.parse(value.getString(name));
        }
        return null;
    }

    /**
     * Converting json values to integer.
     * 
     * @param name
     * @param value
     * @return
     */
    public static Integer parseIntegerFromJson(String name, JSONObject value) {

        if (value.has(name)) {
            return value.getInt(name);
        }
        return null;
    }
}
