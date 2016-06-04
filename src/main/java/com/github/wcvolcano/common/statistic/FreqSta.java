package com.github.wcvolcano.common.statistic;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by canwen on 2016/5/2.
 */
public class FreqSta<K extends Comparable<K>> {
    private Map<String, TreeMap<K, Weight>> groupWeight = new HashMap<>();
    private Set<String> tokens = new TreeSet<>();
    private static final Gson GsonWriter = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public Weight addWeight(String token, K key, int increment) {

        TreeMap<K, Weight> valueMap;
        if (groupWeight.containsKey(token)) {
            valueMap = groupWeight.get(token);
        }
        else {
            valueMap = new TreeMap<>();
            groupWeight.put(token, valueMap);
            tokens.add(token);
        }
        valueMap.put(key, valueMap.getOrDefault(key, new Weight(token)).increment(increment));
        return valueMap.get(key);
    }

    public Weight addWeight(String token, K key) {
        return addWeight(token, key, 1);
    }

    public void calFreq() {
        groupWeight.values().stream()
                .map(TreeMap::values)
                .flatMap(Collection::stream)
                .forEach(Weight::calPercent);
    }

    public String staString(String token) {
        Map<K, Weight> map = groupWeight.get(token);
        if(map == null) return "";
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<K, Weight> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(",")
                    .append(GsonWriter.toJson(entry.getValue().calPercent())).append("\n");
        }

        return builder.toString();
    }

    public String staAllString() {
        StringBuilder builder = new StringBuilder();
        for (String token : tokens) {
            builder.append("============").append(token).append("============\n");
            builder.append(staString(token)).append("\n");
        }
        return builder.toString();
    }

    public void write(BufferedWriter writer, String token) throws IOException {
        Map<K, Weight> map = groupWeight.get(token);
        for (Map.Entry<K, Weight> entry : map.entrySet()) {
            writer.write(entry.getKey() + "," + GsonWriter.toJson(entry.getValue().calPercent()) + "\n");
        }
    }

    public void writeAll(BufferedWriter writer) throws IOException {
        for (String token : tokens) {
            writer.write("=============" + token + "===========\n");
            write(writer, token);
        }
    }
}
