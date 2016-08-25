package com.github.wcvolcano.common.collection.list;

import java.util.List;

/**
 * Created by canwen on 2016/8/12.
 */
public class Lists {
    public static <T> T lastElement(List<T> list) {
        return list.get(list.size() - 1);
    }
}
