package com.github.wcvolcano.common.statistic;

import com.github.wcvolcano.common.format.DoubleFormat;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by canwen on 2016/4/20.
 */
public class Weight {
    private final String sort;

    @Expose
    int num=0;
    @Expose double percent;
    static Map<String, Integer> totalMap = new HashMap<>();

    public Weight(String sort) {
        this.sort = sort;
    }

    public Weight calPercent() {
        int total = totalMap.getOrDefault(sort, 0);
        if(total == 0) ++total;
        percent = DoubleFormat.setAccuracy(num * 1.0 / total, 3);
        return this;
    }

    public Weight increment() {
        return increment(1);
    }

    public Weight increment(int delta) {
        num += delta;
        Weight.totalMap.put(sort, Weight.totalMap.getOrDefault(sort, 0) + delta);
        return this;
    }

    ////////////////////////////

    public int getNum() {
        return num;
    }

    public double getPercent() {
        return percent;
    }

    public  int getTotal() {
        return totalMap.getOrDefault(sort, 0);
    }
}
