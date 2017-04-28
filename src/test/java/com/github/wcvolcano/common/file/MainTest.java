package com.github.wcvolcano.common.file;

import com.github.wcvolcano.common.file.sort.sorter.FileSort;
import com.github.wcvolcano.common.file.sort2.FileSort2;
import com.github.wcvolcano.common.file.sort2.FileSortCommand;
import com.github.wcvolcano.common.timer.StopWatch;

import java.io.File;
import java.io.IOException;

/**
 * Created by canwen on 2016/7/4.
 */
public class MainTest {
    public static void main(String[] args) throws IOException {
//        sort();
//        sort2();
        sortCommandLine();
    }

    private static void sort() {
        StopWatch watch = new StopWatch();

        String dataFile = "E:\\data\\off-judge-alg\\todo-case\\sortTemp\\temp.sort";
        String outFile = "E:\\data\\off-judge-alg\\todo-case\\sortTemp\\temp.sort.sort_0";

        String sep = "::";
        StringBuilder builder = new StringBuilder();

        builder.append(dataFile).append(sep)
                .append(outFile).append(sep)
                .append("-d").append(sep).append(",").append(sep)
                .append("-t").append(sep).append("E:\\data\\off-judge-alg\\todo-case\\sortTemp\\middle_0\\").append(sep)
                .append("-s").append(sep).append("1");

        FileSort.main(builder.toString().split(sep));

        System.out.println(watch.consume());
        System.out.println(watch.consumeInSecond());
    }

    private static void sort2() throws IOException {
        StopWatch stopWatch = new StopWatch();
        File dataFile = new File("E:\\data\\off-judge-alg\\todo-case\\sortTemp\\temp.sort");
        File sortedFile = new File("E:\\data\\off-judge-alg\\todo-case\\sortTemp\\temp.sort.sort_1");

        FileSort2.create(dataFile, sortedFile)
                .setMemoryLimit(FileSort2.MemoryUnit.M, 300)
                .sort();

        System.out.println(stopWatch.consume());
        System.out.println(stopWatch.consumeInSecond());
    }

    private static void sortCommandLine() throws IOException {
        StopWatch stopWatch = new StopWatch();

        File dataFile = new File("E:\\data\\off-judge-alg\\todo-case\\sortTemp\\piece");
//        File sortedFile = new File("E:\\data\\off-judge-alg\\todo-case\\sortTemp\\temp.sort.sort_1");

        String sep = "=";
        StringBuilder builder = new StringBuilder();
        builder.append("-i").append(sep).append(dataFile).append(" ")
//                .append("-o").append(sep).append(sortedFile).append(" ")
                .append(" -s").append(sep).append("1");

        FileSortCommand.main(builder.toString().split(" "));


        System.out.println(stopWatch.consume());
        System.out.println(stopWatch.consumeInSecond());
    }


}
