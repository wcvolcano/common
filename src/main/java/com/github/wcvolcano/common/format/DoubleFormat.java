package com.github.wcvolcano.common.format;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by wencan on 2016/2/17.
 */
public class DoubleFormat {
    public static String formatN(double d, int n) {
        return new BigDecimal(d).setScale(n, ROUND_HALF_UP).toString();
    }

    public static double setAccuracy(double d, int n) {
        return new BigDecimal(d).setScale(n, ROUND_HALF_UP).doubleValue();
    }
}
