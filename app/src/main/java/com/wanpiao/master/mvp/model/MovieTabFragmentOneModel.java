package com.wanpiao.master.mvp.model;


import android.content.Context;

import com.google.gson.Gson;
import com.lany.uniqueid.DeviceUtils;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.MovieTabFragmentOneContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MovieTabFragmentOneModel implements MovieTabFragmentOneContract.Model{

    @Inject

    public MovieTabFragmentOneModel(){

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> requestMovieTabOneData(Context context, int pageNum, int pageSize) {
        Gson gson = new Gson();
        HashMap<String, Object> p = new HashMap<>();
        p.put("pageNum",pageNum);
        p.put("pageSize",pageSize);
        p.put("mStatus","0");
        //语言切换可能用到一个appLanguage字段
        p.put("appLanguage","0");
        return RetrofitManager.getInstance().create(CommonService.class)
                .movieTabOneData(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestTabBanner(Context context) {
        Gson gson=new Gson();
        HashMap<String,Object> p=new HashMap<>();
        p.put("pageNum","0");
        p.put("pageSize","10");
        p.put("advertisePosition",1);
        return RetrofitManager.getInstance().create(CommonService.class)
                .homeBannerData(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestMovieTabOneGalleryData(Context context, int pageNum, int pageSize) {
        Gson gson = new Gson();
        HashMap<String, Object> p = new HashMap<>();
        p.put("pageNum",pageNum);
        p.put("pageSize",pageSize);
        p.put("mStatus","0");
        //语言切换可能用到一个appLanguage字段
        p.put("appLanguage","0");
        return RetrofitManager.getInstance().create(CommonService.class)
                .movieTabOneData(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }
}
