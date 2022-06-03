package com.sdau.hotelsystem.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getNow(){
        SimpleDateFormat fm = new SimpleDateFormat("yyyy年MM月dd日");
        return fm.format(new Date());
    }
    public static String getNowTime(){
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fm.format(new Date());
    }
    public static String getTime(){
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss");
        return fm.format(new Date());
    }
    public static String getCode(){
        SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmssms");
        return fm.format(new Date());
    }

    public static Date getNextDay(Date date)
    {
        //1天24小时，1小时60分钟，1分钟60秒，1秒1000毫秒
        long addTime = 1 * 24 * 60 * 60 * 1000;
        Date nextDate = new  Date(date.getTime() + addTime);
        return nextDate;
    }
}
