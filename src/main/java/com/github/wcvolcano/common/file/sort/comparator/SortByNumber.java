package com.github.wcvolcano.common.file.sort.comparator;


import com.github.wcvolcano.common.string.StringParser;

import java.util.Comparator;

/**
 * Created by wencan on 2015/6/17.
 */
class SortByNumber extends SortComparator {
    public SortByNumber(Comparator<String> comparator, char delimiter, int pos) {
        super(comparator, delimiter, pos);
    }

    @Override
    protected int compareMore(String a, String b) {
        double ad = Double.parseDouble(StringParser.stringOfIndex(a, pos, delimiter));
        double bd = Double.parseDouble(StringParser.stringOfIndex(b, pos, delimiter));
        if(ad > bd) return 1;
        if(ad < bd) return -1;
        return 0;
    }
}
