package com.github.wcvolcano.common.date;

import com.github.wcvolcano.common.timer.StopWatch;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by canwen on 2017/4/26.
 */
public class DateUtilTest {
    @Test
    public void test() {
        long now = System.currentTimeMillis();
        Assert.assertEquals(DateUtil.today(), DateUtil.day(System.currentTimeMillis()));
        Assert.assertEquals(DateUtil.parseFormattedTime(DateUtil.dayTime(now))/1000, now/1000);
    }

    int million = 1000000;
    int times = 6 * million;

    @Test
    public void timeDiff() {
        String t0 = "2017-04-26 18:20:30";
        String t1 = "2017-04-26 18:21:18";

        int count = times;

        System.out.println(DateUtil.parseFormattedTime(t0) - DateUtil.parseFormattedTime(t1));

        StopWatch stopWatch = new StopWatch();
        while (--count > 0) {
            long diff = DateUtil.parseFormattedTime(t0) - DateUtil.parseFormattedTime(t1);
        }

        System.out.println("in sec:" + stopWatch.consumeInSecond());

    }

    @Test
    public void timeDiff2() {
        String t0 = "2017-04-26 18:20:30";
        String t1 = "2017-04-26 18:21:18";

        int count = times;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        long temp = LocalDateTime.parse(t0, formatter).until(LocalDateTime.parse(t1, formatter), ChronoUnit.MILLIS);
        System.out.println(temp);

        StopWatch stopWatch = new StopWatch();
        while (--count > 0) {
            LocalDateTime time0 = LocalDateTime.parse(t0, formatter);
            LocalDateTime time1 = LocalDateTime.parse(t1, formatter);
            long diff = time0.until(time1, ChronoUnit.MILLIS);
        }

        System.out.println("in sec:" + stopWatch.consumeInSecond());

    }
}
