package com.rbestardpino.cryptotracker;

import org.json.JSONObject;

import io.quickchart.QuickChart;

public class Test {
    public static void main(String[] args) {
        JSONObject data = new JSONObject();
        JSONObject datasets = new JSONObject();
        data.append("labels", "Q1").append("labels", "Q2").append("labels", "Q3").append("labels", "Q4");
        datasets.put("label", "Users");
        datasets.append("data", 50).append("data", 60).append("data", 170).append("data", 180);
        data.append("datasets", datasets);

        QuickChart chart = new QuickChart();
        chart.setBackgroundColor("white");
        chart.setConfig(new JSONObject().put("type", "line").put("data", data).toString());
        System.out.println(chart.getShortUrl());
    }
}