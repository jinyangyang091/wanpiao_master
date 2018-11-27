package com.wanpiao.master.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.UserReviewsContract;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/21      onCreate
 */
public class UserReviewPresenter extends BasePresenter<UserReviewsContract.View,UserReviewsContract.Model> implements UserReviewsContract.Presenter {

    @Inject
    public UserReviewPresenter(UserReviewsContract.View rootView, UserReviewsContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void callComments(String comType, String userId, String JoinId, String comment, int comScore) {
        if (TextUtils.isEmpty(comment)) {
            ToastUtil.show(App.getInstance(), "您好，评论不饿能为空！");
            return;
        }
        if (TextUtils.isEmpty(comScore+"")) {
            ToastUtil.show(App.getInstance(), "您好，请选择星星");
            return;
        }
        mModel.sendComments(comType,userId ,JoinId,comment,comScore)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","sendComments s ========= "+s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String messageId = jsonObject.optString("messageId");
                            Log.d("jinyangyang+","messageId = "+messageId);
                            if (messageId.equals("200")){
                                ToastUtil.show(App.getInstance(), "发表评论成功！");
                                mRootView.showCommentState(s);
                                return;
                            }else if(messageId.equals("400")){
                                ToastUtil.show(App.getInstance(), "发表评论失败！");
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("jinyangyang+","data error e is "+e);
                    }
                });

    }

    @Override
    public void callVoiceComments(String comType, String userId, String JoinId, String comment, int comScore, String commentVoice, Double voiceLength) {
        if (TextUtils.isEmpty(comScore+"")) {
            ToastUtil.show(App.getInstance(), "您好，请选择星星");
            return;
        }
        mModel.sendVoiceComments(comType,userId ,JoinId,comment,comScore, commentVoice, voiceLength)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","sendVoiceComments s ========= "+s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String messageId = jsonObject.optString("messageId");
                            Log.d("jinyangyang+","messageId = "+messageId);
                            if (messageId.equals("200")){
                                ToastUtil.show(App.getInstance(), "发表语音评论成功！");
                                mRootView.showVoliceCommentState(s);
                                return;
                            }else if(messageId.equals("400")){
                                ToastUtil.show(App.getInstance(), "发表语音评论失败！");
                                return;
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("jinyangyang+","data error e is "+e);
                    }
                });
    }
}
