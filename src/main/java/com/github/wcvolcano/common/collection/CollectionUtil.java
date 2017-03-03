package com.github.wcvolcano.common.collection;

import java.util.Collection;
import java.util.function.Function;

/**
 * Created by canwen on 2016/12/8.
 */
public class CollectionUtil {
    /**
     * 选择集合中最大属性的元素
     * 属性可以自己设置 depend
     * @param eles
     * @param depend 最大元素的依据
     * @param <T>
     * @return
     */
    public static  <T> T max(Collection<T> eles, Function<T, Double> depend) {
        double max = Double.MIN_VALUE;
        T result = null;

        for (T e : eles) {
            double by = depend.apply(e);
            if (by > max) {
                max = by;
                result = e;
            }
        }

        return result;
    }
}
