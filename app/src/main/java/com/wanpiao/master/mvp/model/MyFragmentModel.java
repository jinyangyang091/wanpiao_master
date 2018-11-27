package com.wanpiao.master.mvp.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.MyFragmentContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;
import com.wanpiao.master.utils.DeviceUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MyFragmentModel implements MyFragmentContract.Model {
    @Inject
    public MyFragmentModel(){

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> requestLanding(Context context, String mail, String pwd) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("userAccount",mail);
        p.put("userPassword",pwd);
        p.put("deviceId",DeviceUtils.getUniqueID());
        Log.d("jinyangyang+","deviceId is "+DeviceUtils.getUniqueID());
        return RetrofitManager.getInstance().create(CommonService.class)
                .requestLanding(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }
}
