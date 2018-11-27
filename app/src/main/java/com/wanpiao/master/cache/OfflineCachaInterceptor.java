package com.wanpiao.master.cache;

import android.content.Context;

import com.wanpiao.master.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description: #
 *  * 描述: 没网络的情况下读取缓存，有网络时读取最新数据
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class OfflineCachaInterceptor implements Interceptor {

    private Context mContext;

    public OfflineCachaInterceptor(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected(mContext)){
            //离线缓存控制 总的缓存时间=在线缓存时间+设置离线缓存时间
            //保存14天
            int maxStale = 60 * 60 * 24 * 14 ;
            CacheControl cacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxStale, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        return chain.proceed(request);
    }
}
