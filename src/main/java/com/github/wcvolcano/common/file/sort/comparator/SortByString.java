package com.github.wcvolcano.common.file.sort.comparator;


import com.github.wcvolcano.common.string.StringParser;

import java.util.Comparator;

/**
 * Created by wencan on 2015/6/17.
 */
class SortByString extends SortComparator {

    public SortByString(Comparator<String> comparator, char delimiter, int pos) {
        super(comparator, delimiter, pos);
    }

    @Override
    protected int compareMore(String a, String b) {
        return StringParser.stringOfIndex(a, pos, delimiter).compareTo(StringParser.stringOfIndex(b, pos, delimiter));
    }
}
