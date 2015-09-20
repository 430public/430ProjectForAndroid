package com.projectforandroid.utils.dateutils;

import android.content.Context;
import android.provider.ContactsContract;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 大灯泡 on 2015/9/20.
 * 时间格式化工具类
 */
public class DateUtils {
    public final static String DATE_YYYYMMDD = "yyyy-MM-dd";
    public final static String DATE_YYYYMMDD_CN = "yyyy年MM月dd日";
    public final static String DATE_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_YYYYMMDDHHMMSS_CN = "yyyy年MM月dd日 HH:mm:ss";
    public final static String DATE_YYYYMM = "yyyy-MM";
    public final static String DATE_YYYYMMDD_SPRIT = "yyyy/MM/dd";

    /**
     * 格式化日期
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date(timeInMillis));
    }

    /**
     * 得到年份
     *
     * @return
     */
    public String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    /**
     * 得到月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return (c.get(Calendar.MONTH) + 1) + "";
    }

    /**
     * 获得当天的日期
     *
     * @return
     */
    public static String getCurrDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH) + "";
    }



}
