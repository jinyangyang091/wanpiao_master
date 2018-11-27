package com.wanpiao.master.mvp.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.LoginContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;
import com.wanpiao.master.utils.DeviceUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class LoginModel implements LoginContract.Model {

    @Inject
    public LoginModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> requestLanding(Context context, String userAccount, String userPassword) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();

//        p.put("userAccount","999999999999@qq.com");
//        p.put("userPassword","123456");
        p.put("userAccount",userAccount);
        p.put("userPassword",userPassword);
        p.put("deviceId",DeviceUtils.getUniqueID());

        Log.d("jinyangyang+","deviceId is "+DeviceUtils.getUniqueID());
        return RetrofitManager.getInstance().create(CommonService.class)
                .requestLanding(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }
}
