package com.github.wcvolcano.common.file;

import com.github.wcvolcano.common.file.io.FileIOUtil;
import com.github.wcvolcano.common.timer.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by canwen on 2016/7/5.
 */
public class ReadFile {
    public static void main(String[] args) throws IOException {
        StopWatch watch = new StopWatch();

        String file = "E:\\\\data\\\\opt\\\\out.log";

        BufferedReader reader = FileIOUtil.getBufferedReader(file);
        String aline;

        int c=0;
        while ((aline = reader.readLine()) != null) {
            ++c;
        }

        System.out.println(c);
        System.out.println(watch.consume());
    }
}
