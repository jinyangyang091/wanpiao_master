package com.wanpiao.master.mvp.model;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.mvp.contract.UserReviewsContract;
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
 * #0000      @Author: tianxiao     2018/10/21      onCreate
 */
public class UserReviewModel implements UserReviewsContract.Model {
    @Inject
    public UserReviewModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<BaseEntity<String>> sendComments(String comType, String userId, String JoinId, String comment, int comScore) {
        Gson gson=new Gson();
        HashMap<String,Object> p=new HashMap<>();
        p.put("comType",comType);
        p.put("userId",userId);
        p.put("joinId",JoinId);
        p.put("comment",comment);
        p.put("comScore",comScore);
        return RetrofitManager.getInstance().create(CommonService.class)
                .sendCommentFilm(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());

    }

    @Override
    public Observable<BaseEntity<String>> sendVoiceComments(String comType, String userId, String JoinId, String comment, int comScore, String commentVoice, Double voiceLength) {
        Gson gson=new Gson();
        HashMap<String,Object> p=new HashMap<>();
        p.put("comType",comType);
        p.put("userId",userId);
        p.put("joinId",JoinId);
        p.put("comment",comment);
        p.put("comScore",comScore);
        p.put("commentVoice", commentVoice);
        p.put("voiceLength", voiceLength);
        return RetrofitManager.getInstance().create(CommonService.class)
                .sendCommentFilm(gson.toJson(p))
                .throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main());
    }
}
