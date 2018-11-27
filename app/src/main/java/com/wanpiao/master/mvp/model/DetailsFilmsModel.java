package com.wanpiao.master.mvp.model;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.DetailsFilmsActivityContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class DetailsFilmsModel implements DetailsFilmsActivityContract.Model {
    @Inject
    public DetailsFilmsModel() {
    }


    @Override
    public Observable<BaseEntity<String>> requestDetailFilm(String movieId) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("movieId",movieId);
        return RetrofitManager.getInstance().create(CommonService.class)
                .requestDetailFilm(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestDianZan(String userId, String joinId, String joinType, String operation) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("userId",userId);
        p.put("joinId",joinId);
        p.put("joinType",joinType);
        p.put("operation",operation);
        return RetrofitManager.getInstance().create(CommonService.class)
                .requestDianzanData(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> requestInfo(String userId, String joinId, String joinType, String operation) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("userId",userId);
        p.put("joinId",joinId);
        p.put("joinType",joinType);
        p.put("operation",operation);
        return RetrofitManager.getInstance().create(CommonService.class)
                .requestInfoData(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public void onDestroy() {

    }
}
