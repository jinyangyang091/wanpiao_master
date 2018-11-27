package com.wanpiao.master.mvp.model;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.DetailsFilmActivityContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 */
public class DetailsFilmModel implements DetailsFilmActivityContract.Model {

    @Inject
    public DetailsFilmModel() {
    }

    @Override
    public void onDestroy() {

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
    public Observable<BaseEntity<String>> commentFilm(String joinId, String joinType, String userId, String comment) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("joinId",joinId);
        p.put("joinType",joinType);
        p.put("userId",userId);
        p.put("comment",comment);
        return RetrofitManager.getInstance().create(CommonService.class)
                .sendCommentFilm(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> selectComment(String movieId, String pageNum, String pageSize) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();

//        p.put("userAccount","999999999999@qq.com");
//        p.put("userPassword","123456");
        p.put("movieId",movieId);
        p.put("pageNum",pageNum);
        p.put("pageSize",pageSize);

        return RetrofitManager.getInstance().create(CommonService.class)
                .selectCommentList(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }
}
