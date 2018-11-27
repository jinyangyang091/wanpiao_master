package com.wanpiao.master.mvp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.SplashContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SplashModel implements SplashContract.Model {

    @Inject
    public SplashModel() {
    }

    @Override
    public Observable<BaseEntity<String>> requestSplash() {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("systemType","Android");
        String jsonList = gson.toJson(p);

        Log.d("jinyangyang+"," jsonList ============ "+jsonList);

        return RetrofitManager.getInstance().create(CommonService.class)
//                .selectSplashAdv(gson.toJson(p))
                .selectSplashAdv(jsonList)
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public void onDestroy() {

    }
}
