package com.github.wcvolcano.common.filesort;

import com.github.wcvolcano.common.file.sort.sorter.FileSort;
import com.github.wcvolcano.common.timer.StopWatch;

/**
 * Created by canwen on 2016/7/4.
 */
public class MainTest {
    public static void main(String[] args) {
        StopWatch watch = new StopWatch();

        String dataFile = "E:\\data\\opt\\answer.csv";
        String outFile = "E:\\data\\opt\\sort.log";

        String sep = "::";
        StringBuilder builder = new StringBuilder();

        builder.append(dataFile).append(sep)
                .append(outFile).append(sep)
                .append("-d").append(sep).append(",").append(sep)
                .append("-t").append(sep).append("E:\\data\\opt\\").append(sep)
                .append("-s").append(sep).append("1");

        FileSort.main(builder.toString().split(sep));

        System.out.println(watch.consume());
        System.out.println(watch.consumeInSecond());
    }
}
