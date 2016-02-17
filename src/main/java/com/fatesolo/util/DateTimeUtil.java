package com.fatesolo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateTimeUtil {

    //获取当前毫秒时间
    public static String getCurrentTimeMillis() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        return String.valueOf(System.currentTimeMillis());
    }

    //毫秒时间转换为日期时间格式
    public static String millisToDateTime(String millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(millis));

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }

    //毫秒时间转换为日期格式
    public static String millisToDate(String millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(millis));

        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

}
