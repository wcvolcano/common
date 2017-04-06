package com.github.wcvolcano.common.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canwen on 2017/4/6.
 */
public class Range {
    public static List<Integer> range(int s, int e, int step) {
        List<Integer> list = new ArrayList<>();
        for (; s < e; s += step) {
            list.add(s);
        }
        return list;
    }
}
