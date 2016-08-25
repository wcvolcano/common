package com.github.wcvolcano.common.timer;

/**
 * Created by wencan on 2015/5/12.
 */
public class StopWatch {
    String name;
    long start;
    long end;

    long lastStart;
    long period;

    boolean isEnd = false;

    public StopWatch(String name) {
        this.name = name;
        start = System.currentTimeMillis();
        lastStart = start;
    }

    public StopWatch() {
        start = System.currentTimeMillis();
        lastStart = start;
    }

    public long consumeInSecond() {
        end();
        return period / 1000;
    }

    public long consumeInMinute() {
        end();
        return period / 1000 / 60;
    }

    public void pause() {
        if(isEnd) return;
        period += System.currentTimeMillis() - lastStart;
        isEnd = true;
    }

    public void resume() {
        isEnd = false;
        lastStart = System.currentTimeMillis();
    }


    public long consume() {
        end();
        return period;
    }

    public void end() {
        if(!isEnd) {
            pause();
        }
    }

    public long getStart() {
        return start;
    }
}
