package com.github.wcvolcano.common.date;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by canwen on 2016/6/4.
 * 以天为单位进行计数。
 */
public class IncreaseCounter {
    @SuppressWarnings("FieldCanBeLocal")
    private final long MAX_COUNT = Long.MAX_VALUE - 10000;
    private AtomicLong count = new AtomicLong(0);
    private long todayEndTime = 0;

    public IncreaseCounter() {
        todayEndTimeReset();
    }

    public long getOrder() {
        if (System.currentTimeMillis() > todayEndTime) {
            todayEndTimeReset();
            count.set(0);
        }
        if (count.get() > MAX_COUNT) {
            count.set(0);
        }
        return count.incrementAndGet();
    }

    private void todayEndTimeReset() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        todayEndTime = calendar.getTimeInMillis();
    }

}
