package com.github.wcvolcano.common.string;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by wencan on 2015/6/1.
 */
public class StringParser {

    public static String stringOfIndex(String s, int pos, char delimiter) {
        if (pos > 0) return stringOfPositiveIndex(s, pos, delimiter);
        if (pos < 0) return stringOfNegativeIndex(s, pos, delimiter);
        return s;
    }

    public static String stringOfPositiveIndex(String s, int pos, char delimiter) {
        int begin = 0;
        int end = -1;
        for (int i = 0; i < pos; i++) {
            begin = end + 1;
            end = s.indexOf(delimiter, begin);
        }

        if (end == -1) return s.substring(begin);
        return s.substring(begin, end);
    }

    public static String stringOfNegativeIndex(String s, int pos, char delimiter) {
        pos = -pos;
        int end = -1;
        int begin = s.length();
        for (int i = 0; i < pos; i++) {
            end = begin - 1;
            begin = s.lastIndexOf(delimiter, end);
        }
        return s.substring(begin + 1, end + 1);
    }


    public static List<String> splitStrList(String s, char delimiter) {
        List<String> list = new ArrayList<String>();
        int begin = 0;
        int end = s.indexOf(delimiter);
        while (end >= 0) {
            list.add(s.substring(begin, end));
            begin = end + 1;
            end = s.indexOf(delimiter, begin);
        }

        if (begin <= s.length()) {
            list.add(s.substring(begin));
        }

        return list;
    }

    public static String[] splitStrArray(String s, char delimiter) {
        List<String> list = splitStrList(s, delimiter);
        String[] a = new String[list.size()];
        return list.toArray(a);
    }

    /**
     * split a string into string array with lenOfArray element
     * sample:
     * String s = hello world, wen, can
     * splitStrArray(s, ',' 2)
     *      println()::hello world
     *                 wen, can
     * splitStrArray(s, ',', 3)
     *      println()::hello world
     *                 (space)wen
     *                 (space)can
     * @param s
     * @param delimiter
     * @param lenOfArray
     * @return
     */
    public static String[] splitStrArray(String s, char delimiter, int lenOfArray) {
        String[] split = new String[lenOfArray];
        int count = 0;
        int begin = 0;
        int end = s.indexOf(delimiter);
        while (end >= 0 && count < lenOfArray-1) {
            split[count++] = s.substring(begin, end);
            begin = end + 1;
            end = s.indexOf(delimiter, begin);
        }

        if (begin <= s.length() && count < lenOfArray) {
            split[count] = s.substring(begin);

        }

        return split;
    }

    int pos;
    char delimiter;

    public StringParser(int pos, char delimiter) {
        this.pos = pos;
        this.delimiter = delimiter;
    }

    public String StringOfIndex(String s) {
        int begin = 0;
        int end = -1;
        for (int i = 0; i < pos; i++) {
            begin = end + 1;
            end = s.indexOf(delimiter, begin);
        }
        return s.substring(begin, end);
    }

}
