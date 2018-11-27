package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.MyCommentContract;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class MyCommentPresenter extends BasePresenter<MyCommentContract.View, MyCommentContract.Model>
        implements  MyCommentContract.Presenter {
    @Inject
    public MyCommentPresenter(MyCommentContract.View rootView, MyCommentContract.Model model){
        super(rootView, model);
    }

    @Override
    public void selectMyComment(String userId, int pageNum, int pageSize) {
        mModel.selectMyComment(userId, pageNum, pageSize)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","selectMyComment hus ----------  "+s);
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            int messageId = jsonObject.optInt("messageId");
                            String message = jsonObject.optString("message");
                            //message字段代表token,登录成功之后需要把token保存到本地
                            //判定登录是否成功,message
                            if (messageId == 200 && message!=""){
                                mRootView.showMyCommentList(s);
                            }else {

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
