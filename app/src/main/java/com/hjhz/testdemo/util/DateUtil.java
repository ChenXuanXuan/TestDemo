package com.hjhz.testdemo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 陈宣宣 on 2016/5/3.
 */
public class DateUtil {

    public static String getRightTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;
       /*    now = df.parse("2004-01-02 11:30:25");
            java.util.Date date = df.parse("2004-01-02 11:30:24");*/

        System.out.println("getRightTime" + time + "currentTimeMillis" + System.currentTimeMillis());
     /*   Date currTime = new Date(time);
        int month = currTime.getMonth() + 1;//月*/

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int MONTH = cal.get(Calendar.MONTH) + 1;
        int dayy = cal.get(Calendar.DATE);


        if (System.currentTimeMillis() / time > 200)
            time = time * 1000;

        long l = System.currentTimeMillis() - time;
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sb = new StringBuffer();

        if (day > 3) {
            sb.append(MONTH + "月" + dayy + "日");
        } else if (day > 0 && day <= 3)
            sb.append(day + "天前");
        else if (hour > 0)
            sb.append(hour + "小时前");
        else if (min > 0)
            sb.append(min + "分前");
        else
            sb.append("刚刚");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static String getRightTime(long time, long currtime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;
       /*    now = df.parse("2004-01-02 11:30:25");
            java.util.Date date = df.parse("2004-01-02 11:30:24");*/
        System.out.println("getRightTime" + time + "currentTimeMillis" + currtime);

        //这个得是评论时间
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int MONTH = cal.get(Calendar.MONTH) + 1;
        int dayy = cal.get(Calendar.DATE);

        if (System.currentTimeMillis() / time > 200)
            time = time * 1000;

        long l = currtime - time;
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sb = new StringBuffer();

        if (day > 3) {
            sb.append(MONTH + "月" + dayy + "日");

        } else if (day > 0 && day <= 3)
            sb.append(day + "天前");
        else if (hour > 0)
            sb.append(hour + "小时前");
        else if (min > 0)
            sb.append(min + "分前");
        else
            sb.append("刚刚");
        System.out.println(sb.toString());
        return sb.toString();
    }


    public static String getRightTimereverse(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date now;
       /*    now = df.parse("2004-01-02 11:30:25");
            java.util.Date date = df.parse("2004-01-02 11:30:24");*/
        long l = time;
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sb = new StringBuffer();
        if (day > 0)
            sb.append(day + "天");
        else if (hour > 0)
            sb.append(hour + "小时");
        else if (min >= 0)
            sb.append(min + "分");
        System.out.println(sb.toString());
        return sb.toString();
    }

}
