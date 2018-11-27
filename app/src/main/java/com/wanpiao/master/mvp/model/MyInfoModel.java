package com.wanpiao.master.mvp.model;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.MyInfoContract;
import com.wanpiao.master.retrofit.RetrofitManager;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.retrofit.api.CommonService;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

public class MyInfoModel implements  MyInfoContract.Model {
    @Inject
    public MyInfoModel(){

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> selectMyInfo(String userId, int pageNum, int PageSize) {
        Gson gson=new Gson();
        HashMap<String,Object> p=new HashMap<>();
        p.put("userId",userId);
        p.put("pageNum",pageNum);
        p.put("pageSize",PageSize);
        return RetrofitManager.getInstance().create(CommonService.class)
                .selectMyInfo(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());

    }
}
