package com.wanpiao.master.mvp.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.MyInformationContract;
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
 * #0000      @Author: tianxiao     2018/10/23      onCreate
 */
public class MyInformationModel implements MyInformationContract.Model {

    @Inject
    public MyInformationModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> uploadHeaderImg(String headerImg) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("imgFIle",headerImg);
        return RetrofitManager.getInstance().create(CommonService.class)
                .uploadHeader(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BaseEntity<String>> updateUserInfo(String userId, String userName, String userSex, String userPortrait) {
        Gson gson=new Gson();
        HashMap<String,String> p=new HashMap<>();
        p.put("id",userId);
        p.put("userName",userName);
        p.put("userSex",userSex);
        p.put("userPortrait",userPortrait);

        return RetrofitManager.getInstance().create(CommonService.class)
                .upDateUserInfo(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
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
