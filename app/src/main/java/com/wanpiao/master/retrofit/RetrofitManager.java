package com.wanpiao.master.retrofit;


import android.annotation.SuppressLint;
import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.BuildConfig;
import com.wanpiao.master.cache.OfflineCachaInterceptor;
import com.wanpiao.master.cache.TransitorilyCacheInterceptor;
import com.wanpiao.master.common.StateInterceptor;
import com.wanpiao.master.retrofit.interceptor.HttpCommonParamsInterceptor;
import com.wanpiao.master.retrofit.interceptor.HttpCommonParamsInterceptor2;
import com.wanpiao.master.utils.L;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述: retrofit管理类
 * --------------------------------------------
 * 工程:
 */
public class RetrofitManager {
    private static RetrofitManager instance;
    private static Retrofit retrofit;
    private static OkHttpClient client;
    private final static int CONNECT_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 60;
    private final static int WRITE_TIMEOUT = 60;

    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     */
    private static void initOkHttpClient() {
        Log.d("jinyangyang+","initOkHttpClient be called");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        BASEIC:请求/响应行
//        HEADER:请求/响应行 + 头
//        BODY:请求/响应行 + 头 + 体
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置Http缓存
        Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 20);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new TransitorilyCacheInterceptor())
                .addInterceptor(new OfflineCachaInterceptor(App.getInstance()))
                .addInterceptor(new HttpCommonParamsInterceptor())//添加全局请求参数
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)//是否重连
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                //.sslSocketFactory(createSSLSocketFactory())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager()[0])//忽略证书
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        client = builder.build();
    }

    private static void initOkHttpClient2() {
        Log.d("jinyangyang+","initOkHttpClient2 be called");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        BASEIC:请求/响应行
//        HEADER:请求/响应行 + 头
//        BODY:请求/响应行 + 头 + 体
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置Http缓存
        Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 20);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new TransitorilyCacheInterceptor())
                .addInterceptor(new OfflineCachaInterceptor(App.getInstance()))
                .addInterceptor(new HttpCommonParamsInterceptor2())//添加全局请求参数
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)//是否重连
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                //.sslSocketFactory(createSSLSocketFactory())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager()[0])//忽略证书
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        client = builder.build();
    }


    private RetrofitManager() {
        //RetrofitUrlManager.getInstance().setGlobalDomain(baseUrl);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new EmptyString2ObjConverterFactory())
                .addConverterFactory(new ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                initOkHttpClient();
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    public static RetrofitManager getInstance2() {
        Log.d("jinyangyang_","RetrofitManager getInstance2 be called");
        Log.d("jinyangyang+","instance is "+instance);
        instance = null;
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                initOkHttpClient2();
                instance = new RetrofitManager();
            }
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    private static String baseUrl = BuildConfig.BASE_URL;

}
