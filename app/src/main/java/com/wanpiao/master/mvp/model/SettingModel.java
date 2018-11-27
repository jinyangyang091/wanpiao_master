package com.wanpiao.master.mvp.model;

import com.google.gson.Gson;
import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.SettingContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;
import com.wanpiao.master.utils.SPUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public class SettingModel implements SettingContract.Model {

    @Inject
    public SettingModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> logOut() {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
//        p.put("userAccount",SPUtils.get(App.getInstance(), "userAccount",""));
//        p.put("userPassword",SPUtils.get(App.getInstance(),"userPassword",""));
//        p.put("deviceId","123456789qwertyui");
        return RetrofitManager.getInstance().create(CommonService.class)
                .LogOut(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> privacy() {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
//        p.put("userAccount",SPUtils.get(App.getInstance(), "userAccount",""));
//        p.put("userPassword",SPUtils.get(App.getInstance(),"userPassword",""));
//        p.put("deviceId","123456789qwertyui");
        return RetrofitManager.getInstance().create(CommonService.class)
                .privacy(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

}
