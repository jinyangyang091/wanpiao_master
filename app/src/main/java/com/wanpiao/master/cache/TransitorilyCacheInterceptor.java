package com.wanpiao.master.cache;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: #
 *  * 描述: 短暂的缓存处理器 无论有网没网，一定时间段内都去读取缓存
 *  * 短时间内重复访问同一个网址 优先从之前的缓存中读取
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class TransitorilyCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String serverCache = response.header("Cache-Control");
        //如果服务端设置相应的缓存策略那么遵从服务端的缓存策略 不作修改
        if (TextUtils.isEmpty(serverCache)){
            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)){
                //如果请求中未设置Cache-Control 则统一设置一分钟
                //在线缓存 一分钟之内可读取 单位:秒
                int maxAge = 1*60;
                return response.newBuilder()
                        //清除头信息，如果服务器不支持，会返回干扰信息
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control","public, max-age="+maxAge)
                        .build();
            }else{
                return response.newBuilder()
                        .addHeader("Cache-Control",cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        }
        return response;
    }
}
