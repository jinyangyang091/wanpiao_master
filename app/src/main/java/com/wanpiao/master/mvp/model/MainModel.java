package com.wanpiao.master.mvp.model;

import android.content.Context;

import com.google.gson.Gson;
import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.contract.MainContract;
import com.wanpiao.master.retrofit.ApiContstants;
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
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class MainModel implements MainContract.Model{

    @Inject
    public MainModel() {
    }

    @Override
    public User getUser(Context context) {
        return UserProxy.getInstance().getUser(context);
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public Observable<BaseEntity<User>> queryUser() {
        return RetrofitManager.getInstance().create(CommonService.class)
                .getUserInfo("id")
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> versionUpdate() {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("systemType","Android");
        return RetrofitManager.getInstance().create(CommonService.class)
                .versionUpdate(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public void onDestroy() {

    }
}
