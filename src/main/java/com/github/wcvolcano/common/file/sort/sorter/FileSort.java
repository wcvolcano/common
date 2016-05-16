package com.github.wcvolcano.common.file.sort.sorter;


import com.github.wcvolcano.common.file.sort.comparator.ComparatorFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wencan on 2015/6/16.
 */
public class FileSort {
    public static void sort(Comparator<String> comparator,
                             File dataFile, File outFile, String tempDir) throws IOException {
        List<File> fileList = new FileSpliter(dataFile, tempDir, comparator).fileSplit();
        new FileMerger(fileList, outFile, comparator);
    }

    public static void sort(Comparator<String> comparator, BufferedReader dataReader, File outFile, String tempDir) throws IOException {
        List<File> fileList = new FileSpliter(dataReader, tempDir, comparator).fileSplit();
        new FileMerger(fileList, outFile, comparator);
    }

    public static void main(String[] args) {
        String tempDir = null;
        File dataFile;
        File outFile;
        char delimiter=',';
        List<String[]> list = new ArrayList<>();

        boolean delimiterFlag = false;

        try {
            dataFile = new File(args[0]);
            outFile = new File(args[1]);
            int len = args.length;
            for (int param = 2; param < len; param++) {
                switch (args[param]) {
                    case "-t":
                        tempDir = args[++param];
                        break;
                    case "-d":
                        delimiter = args[++param].charAt(0);
                        delimiterFlag = true;
                        break;
                    case "-s":
                        list.add(new String[]{"-s", args[++param]});
                        break;
                    case "-n":
                        list.add(new String[]{"-n", args[++param]});
                        break;
                    default:
                        break;
                }
            }

            Comparator<String> comparator;

            if (!delimiterFlag) {
                comparator = ComparatorFactory.DEFAULTCOMPARATOR;
                System.out.println("warning: use String.compare() comparator");
            } else comparator = ComparatorFactory.generator(list, delimiter);

            sort(comparator, dataFile, outFile, tempDir);

        } catch (Exception ex) {
            disPlayUsage();
            ex.printStackTrace();
        }

    }

    private static void disPlayUsage() {
        System.out.println("FileSort:\ninputDataFile\noutputFile");
        System.out.println("-t : tempDir for temporary files");
        System.out.println("-d : delimiter");
        System.out.println("-s : sort by the String of the specific position. \n" +
                "  example: -s 5; sort by 5th String\n" +
                "  example: -s -5: sort by 5th String in reverse order");
        System.out.println("-n : sort by the Number of the specific position. refer to -s");
    }
}
