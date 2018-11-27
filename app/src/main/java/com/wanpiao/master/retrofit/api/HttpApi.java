package com.wanpiao.master.retrofit.api;

import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface HttpApi {
    @FormUrlEncoded
    @POST("{path}")
    Observable<JSONObject> post(@Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{root}/{path}")
    Observable<JSONObject> post(@Path("root") String root, @Path("path") String path, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("{root}/{path}")
    Observable<JSONObject> post(@Path("root") String root, @Path("path") String path);

    @FormUrlEncoded
    @POST("{path}")
    Observable<JSONObject> post(@Path("path") String path);

    @GET("{path}")
    Observable<JSONObject> get(@Path("path") String path);

    @GET("{path}")
    Observable<JSONObject> get(@Path("path") String path, @QueryMap Map<String, String> map);
}

//public class HttpRequests {
//    private static String baseUrl = "http://127.0.0.1:8080";
//    private static HttpRequests instance = null;
//    private HttpApi httpService;
//    public static HttpRequests getInstance() {
//        if (instance == null) {
//            synchronized (HttpRequests.class) {
//                if (instance == null) {
//                    instance = new HttpRequests();
//                }
//            }
//        } return instance;
//    }
//    private HttpRequests() {
//        Retrofit retrofit = new Retrofit.Builder() .baseUrl(baseUrl) .addConverterFactory(GsonConverterFactory.create()) .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) .build();
//        httpService = retrofit.create(HttpApi.class);
//    }
//
//    public Observable post(String path, Map<String, String> map) {
//        try {
//            Observable<JSONObject> observable;
//            if (path.split("/").length > 1) {
//                String root = path.split("/")[0];
//                path = path.split("/")[1];
//                if (map != null)
//                    observable = httpService.post(root, path, map);
//                else
//                    observable = httpService.post(root, path);
//            } else if (map != null)
//                observable = httpService.post(path, map);
//            else
//                observable = httpService.post(path);
//                observable.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread()); return observable;
//        } catch (Exception e) {
//            Log.e("error", e.getMessage()); return null;
//        }
//    }
//    public Observable get(String path, Map<String, String> map) {
//        try {
//            Observable<JSONObject> observable;
//            if (map != null) {
//                observable = httpService.get(path, map);
//            } else {
//                observable = httpService.get(path);
//            }
//            observable.subscribeOn(Schedulers.io()) .observeOn(AndroidSchedulers.mainThread()); return observable;
//        } catch (Exception e) {
//            Log.e("lawliex", e.getMessage()); return null;
//        }
//    }
//}


