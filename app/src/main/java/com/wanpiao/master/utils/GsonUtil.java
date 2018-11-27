package com.wanpiao.master.utils;

import com.google.gson.Gson;

public class GsonUtil {
    /**
     * 对象转换成json字符串
     * @param obj  需要被转换的对象
     * @return  json字符串
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    /**
     * json字符串转成对象
     * @param str   json字符串
     * @param type  类型
     * @return  被转换的对象
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }
}
