package com.github.wcvolcano.common.file.sort.comparator;

import java.util.Comparator;

/**
 * Created by wencan on 2015/6/17.
 */
abstract class SortComparator implements Comparator<String> {
    Comparator<String> comparator;
    int pos;
    int ord;
    char delimiter;

    public SortComparator(Comparator<String> comparator, char delimiter,int pos) {
        this.comparator = comparator;
        this.pos = pos;
        if (pos < 0) {
            ord = -1;
            this.pos = -pos;
        } else ord = 1;
        this.delimiter = delimiter;
    }

    @Override
    public int compare(String o1, String o2) {
        int cmp = comparator.compare(o1, o2);
        if(0!=cmp) return cmp;
        return ord * compareMore(o1, o2);
    }

    protected abstract int compareMore(String a, String b);
}
