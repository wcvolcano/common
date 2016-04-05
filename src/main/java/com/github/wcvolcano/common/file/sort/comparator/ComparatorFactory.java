package com.github.wcvolcano.common.file.sort.comparator;

import java.util.Comparator;
import java.util.List;

/**
 * Created by wencan on 2015/6/16.
 */
public class ComparatorFactory {
    public static Comparator<String> DEFAULTCOMPARATOR = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };

    public static Comparator<String> generator(List<String[]> list, char delimiter) {
        Comparator<String> comparator = new SortByNull();
        for (String[] aList : list) {
            String m = aList[0];
            int pos = Integer.parseInt(aList[1]);
            switch (m) {
                case "-s":
                    comparator = new SortByString(comparator, delimiter, pos);
                    break;
                case "-n":
                    comparator = new SortByNumber(comparator, delimiter, pos);
                    break;
                default:
                    comparator = new SortByNull();
                    break;
            }

        }

        return comparator;

    }


}
