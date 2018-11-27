package com.wanpiao.master.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeSwitchUtils {
    public static void main(String[] args) {
        String utcTime = "2018-08-07T01:00:59Z";
        String utcTime2 = "2018-11-14T02:29:58.000+0000";
        String utcTime3 = "2016-09-03T00:00:00.000+08:00";
//        String time = formatStrUTCToDateStr(utcTime);
//        System.out.println("utcTime 转换前：" + utcTime);
//        System.out.println("utcTime 转换后 time ：" + time);
        String curtime = dealDateFormat2(utcTime2);
        System.out.println("curtime 转换后 time ：" + curtime);


        Date date = new Date();
        System.out.println("date is ："+date);
        String strDateFormat = "yyyy-MM-dd-HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        System.out.println("当前时间是："+sdf.format(date));


        System.out.println(System.currentTimeMillis());
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(new Date().getTime());

        long curtimeMillis = System.currentTimeMillis();
        System.out.println("curtimeMillis change after is "+stampToDate(curtimeMillis));
        try {
            System.out.println("cutTime change after is "+dateToStamp(stampToDate(curtimeMillis)));
        }catch (ParseException e){
            e.printStackTrace();
        }

    }

    //日期格式转化时间戳
    public static String dateToStamp(String time) throws ParseException {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    //时间戳转化日期格式
    public static String stampToDate(long timeMillis){
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    public static String dealDateFormat(String oldDateStr){
//        try {
//            //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
//            //DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
//            //            //DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
//            Date  date = df.parse(oldDateStr);
//            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.CHINA);
//            Date date1 =  df1.parse(date.toString());
//            DateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日");
//            return df2.format(date1);
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//        return "";

        try {
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXXZ");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.CANADA);
            Date  date = df.parse(oldDateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return  sdf.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return "";
    }

    public static String dealDateFormat2(String oldDateStr){
        try {
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXXZ");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.CANADA);
            Date  date = df.parse(oldDateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            return  sdf.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return "";
    }



}
