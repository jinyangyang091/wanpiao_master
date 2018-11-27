package com.wanpiao.master.common;


import com.wanpiao.master.utils.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class StateInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        String res = response.body().string();
        L.d("StateInterceptor"+res);
        return response;
    }
}
