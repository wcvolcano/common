package com.github.wcvolcano.common.file.sort2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by canwen on 2017/3/30.
 */
public class FileSortCommand {
    public static void main(String[] args) throws IOException {
        String delimiter = ",";
        String inputData = null;
        String outData = null;
        String tempMiddle = null;
        Comparator<String> comparator;

        List<SortKey> sortKeys = new ArrayList<>();

        for (String arg : args) {
            String[] tags = arg.split("=");
            if (tags.length == 2) {
                if (tags[0].equals("-d")) {
                    delimiter = tags[1];
                }
                else if (tags[0].equals("-i")) {
                    inputData = tags[1];
                }
                else if (tags[0].equals("-o")) {
                    outData = tags[1];
                }
                else if (tags[0].equals("-t")) {
                    tempMiddle = tags[1];
                }
                else {
                    SortKey key = SortKey.valueOf(tags[0].substring(1));
                    String[] fields = tags[1].split(",");
                    key.filed = new int[fields.length];
                    for (int i = 0; i < fields.length; ++i) {
                        key.filed[i] = Integer.parseInt(fields[i]);
                    }

                    sortKeys.add(key);
                }
            }
        }

        if (sortKeys.isEmpty()) {
            comparator = String::compareTo;
        }
        else {
            final String d = delimiter;
            comparator = (o1, o2) -> {
                for (SortKey key : sortKeys) {
                    int cmp = key.comparator(d).compare(o1, o2);
                    if (cmp != 0) return cmp;
                }
                return 0;
            };
        }

        FileSort2.create(
                new File(inputData),
                outData == null ? null : new File(outData),
                tempMiddle == null ? null : new File(tempMiddle))
                .setComparator(comparator).sort();
    }

    enum SortKey {
        s {
            @Override
            Comparator<String> comparator(String delimiter) {
                return SortKey.strCmp(filed, delimiter, 1);
            }
        },
        sr {
            @Override
            Comparator<String> comparator(String delimiter) {
                return SortKey.strCmp(filed, delimiter, -1);
            }
        },
        n {
            @Override
            Comparator<String> comparator(String delimiter) {
                return SortKey.numCmp(filed, delimiter, 1);
            }
        },
        nr {
            @Override
            Comparator<String> comparator(String delimiter) {
                return SortKey.numCmp(filed, delimiter, -1);
            }
        },;

        public int[] filed = null;

        abstract Comparator<String> comparator(String delimiter);

        private static Comparator<String> strCmp(int[] filed, String delimiter, int order) {
            if (filed == null) return null;
            else {
                Comparator<String> comparator = (o1, o2) -> {
                    String[] o1s = o1.split(delimiter);
                    String[] o2s = o2.split(delimiter);
                    for (int pos : filed) {
                        int cmp = order * o1s[pos].compareTo(o2s[pos]);
                        if (cmp != 0) return cmp;
                    }
                    return 0;
                };

                return comparator;
            }
        }

        private static Comparator<String> numCmp(int[] filed, String delimiter, int order) {
            if (filed == null) return null;
            else {
                Comparator<String> comparator = (o1, o2) -> {
                    String[] o1s = o1.split(delimiter);
                    String[] o2s = o2.split(delimiter);
                    for (int pos : filed) {
                        double o1d = Double.parseDouble(o1s[pos]);
                        double o2d = Double.parseDouble(o2s[pos]);
                        int cmp = order * Double.compare(o1d, o2d);
                        if (cmp != 0) return cmp;
                    }
                    return 0;
                };

                return comparator;
            }
        }
    }
}
