package com.wanpiao.master.retrofit.interceptor;

import android.content.Intent;
import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.receiver.GlobalReceiver;
import com.wanpiao.master.utils.L;
import com.wanpiao.master.utils.SPUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 描述: 公共参数拦截器
 * --------------------------------------------
 * 工程:
 */
public class HttpCommonParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        Request.Builder requestBuilder = request.newBuilder();
//        RequestBody formBody = new FormBody.Builder()
//                .add("version", ""+ ApplicationUtils.getVerCode(App.getInstance()))
//                .add("systemType","android")
//                //.add("token",""+ UserProxy.getInstance().getUser(App.getInstance()).getToken())
//                .build();
//        String postBodyString = bodyToString(request.body());
//        postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  bodyToString(formBody);
//        request = requestBuilder
//                .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
//                        postBodyString))
//                .build();
        Log.d("jinyangyang+","intercept be called ");
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                //每一个请求头里面都添加token
                //.addHeader("token",SPUtils.get(App.getInstance(), "token",""))
                .addHeader("token",SPUtils.get(App.getInstance(), "token",""))
                .build();
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Accept", "*/*")
//                .addHeader("Cookie", "add cookies here");

        Response response = chain.proceed(request);
        String res = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(res);
            String messageId = jsonObject.optString("messageId");
            String token = jsonObject.optString("data");
            Log.d("jinyangyang+", "messageId ~~~~~~~~~~~~ "+messageId);
            Log.d("jinyangyang+", "token ************ "+token);
            if("1403".equals(messageId)||"1402".equals(messageId)||"1401".equals(messageId)){
                SPUtils.put(App.getInstance(), "isLogin", "");
                SPUtils.put(App.getInstance(), "token", token);
                Intent intent = new Intent(GlobalReceiver.ACTION_TOKEN_QUESTION);
                App.getInstance().sendBroadcast(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        L.d("StateInterceptor"+res);

        return chain.proceed(request);
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null){
                copy.writeTo(buffer);
            }else{
                return "";
            }
            return buffer.readUtf8();
        }catch (final IOException e) {
            return "did not work";
        }
    }
}
