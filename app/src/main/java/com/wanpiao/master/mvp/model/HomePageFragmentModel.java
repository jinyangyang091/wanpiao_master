package com.wanpiao.master.mvp.model;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.HomePageFragmentContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HomePageFragmentModel implements HomePageFragmentContract.Model {

    @Inject
    public HomePageFragmentModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> requestBannerData() {
        Gson gson=new Gson();
        HashMap<String,Object> p=new HashMap<>();
        p.put("pageNum","0");
        p.put("pageSize","10");
        p.put("advertisePosition",0);
        return RetrofitManager.getInstance().create(CommonService.class)
                .homeBannerData(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestInfoData() {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("pageNum","0");
        p.put("pageSize","10");
        p.put("infoType","1");
        return RetrofitManager.getInstance().create(CommonService.class)
                .homeInfo(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestPreData() {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("pageNum","0");
        p.put("pageSize","10");
        p.put("infoType","2");
        return RetrofitManager.getInstance().create(CommonService.class)
                .homePre(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestExInfoData(String pageNum) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("pageNum",pageNum);
        p.put("pageSize","10");
        p.put("infoType","3");
        return RetrofitManager.getInstance().create(CommonService.class)
                .homeExInfo(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }
}
