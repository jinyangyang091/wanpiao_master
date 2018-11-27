package com.wanpiao.master.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 字符串工具
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-03-16 16:07
 */

public class PhoneStringUtils {
    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]*");
    /**
     * 隐藏手机号中间4位
     * @return
     */
    public static String hideNumber(String number){
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        if (number.contains("+86")){
            number = number.replace("+86","");
        }
        if (number.length() <6){
            return number;
        }
        int x = number.length() / 2 - 2;
        int y = number.length()-4-x;
        return number.replaceAll("(\\d{"+x+"})\\d{4}(\\d{"+y+"})", "$1****$2");
    }

    public static boolean isNumeric(String str){
        Matcher isNum = NUMBER_PATTERN.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 获取正确格式的号码 去除+86
     * @param number
     * @return
     */
    public static String getPhoneWithOut86(String number){
        if (TextUtils.isEmpty(number)) {
            return "";
        }
        if (number.contains("+86")){
            number = number.substring(number.indexOf("+86")+3);
        }
        return number;
    }
}
