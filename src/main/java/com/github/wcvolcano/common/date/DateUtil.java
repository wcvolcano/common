package com.github.wcvolcano.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wencan on 2015/5/5.
 */
public class DateUtil {


    public static String today() {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        return new SimpleDateFormat("yyyy-MM-dd").format(cl.getTime());
    }

    public static String yesterday() {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        cl.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(cl.getTime());
    }

    public static String day(Long time) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(time);
        return new SimpleDateFormat("yyyy-MM-dd").format(cl.getTime());
    }

    /**
     *
     * @param time
     * @return 2015-5-4 即月，日不以0补全
     */
    public static String daySingleDigit(Long time) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(time);
        return new SimpleDateFormat("yyyy-M-d").format(cl.getTime());
    }

    public static String dayTime(Long time) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cl.getTime());
    }

    /**
     * 根据yyyy-MM-dd HH:mm:ss将字符串解析为long类型数据
     * @param formatTime format with yyyy-MM-dd HH:mm:ss
     * @return time in long millis
     * @throws ParseException
     */
    public static long parseFormateTime(String formatTime) throws ParseException {
        String format = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(formatTime).getTime();
    }

    public static void main(String[] args) throws ParseException {

//        System.out.println(today());
//        System.out.println(yesterday());
//        System.out.println(day(System.currentTimeMillis()));
//        System.out.println(daySingleDigit(System.currentTimeMillis()));

        long cur = new Date().getTime();
//        System.out.println(dayTime(cur));
//        System.out.println(cur);

        final String format = "HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse("00:00");
        Date date1 = dateFormat.parse("00:00");
        long temp = date1.getTime();
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(temp);
        System.out.println(dateFormat.format(cl.getTime()));
        System.out.println(temp);
        System.out.println(cl.getTime().getTime());

    }


}
