package com.wanpiao.master.mvp.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.RegisterContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;
import com.wanpiao.master.utils.DeviceUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/18      onCreate
 */
public class RegisterModel implements RegisterContract.Model {

    @Inject
    public RegisterModel() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public Observable<BaseEntity<String>> register(String userName, int userSex , String userAccount,  String userPassword) {

        Gson gson=new Gson();
        HashMap<String,Object> p=new HashMap<>();
//        p.put("userSex","0");
//        p.put("userAccount","999999999999@qq.com");
//        p.put("userName","xiaohong");
//        p.put("userPassword","123456");
        //此处使用输入框里面获取到的实际值
        p.put("userName",userName);
        p.put("userSex",userSex);
        p.put("userAccount",userAccount);
        p.put("userPassword",userPassword);
        p.put("deviceId",DeviceUtils.getUniqueID());

        return RetrofitManager.getInstance2().create(CommonService.class)
                .register(gson.toJson(p))
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
