package com.github.wcvolcano.common.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by wencan on 2015/5/5.
 */
public class DateUtil {
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");
    private static final DateTimeFormatter SINGLE_DATE = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String today() {
        return LocalDate.now().toString();
    }

    public static String yesterday() {
        return LocalDate.now().minusDays(1).toString();
    }

    public static String day(long milliseconds) {
        LocalDate date = Instant.ofEpochMilli(milliseconds).atZone(ZONE_ID).toLocalDate();
        return date.toString();
    }

    public static String daySingleDigit(long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZONE_ID).format(SINGLE_DATE);
    }

    public static String dayTime(long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZONE_ID).format(DATE_TIME);
    }

    public static long parseFormattedTime(String formatTime) {
        return LocalDateTime.parse(formatTime, DATE_TIME).atZone(ZONE_ID).toInstant().toEpochMilli();
    }

}
