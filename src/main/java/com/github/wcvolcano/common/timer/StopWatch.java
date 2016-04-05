package com.github.wcvolcano.common.timer;

/**
 * Created by wencan on 2015/5/12.
 */
public class StopWatch {
    String name;
    long start;
    long end;

    public StopWatch(String name) {
        this.name = name;
        start = System.currentTimeMillis();
    }

    public StopWatch() {
        start = System.currentTimeMillis();
    }

    public long consumeInSecond() {
        end = System.currentTimeMillis();
        return (end - start) / 1000;
    }

    public long consumeInMinute() {
        end = System.currentTimeMillis();
        return (end - start) /1000 / 60;
    }

    public long consume() {
        return System.currentTimeMillis() - start;
    }

    public long getStart() {
        return start;
    }
}
