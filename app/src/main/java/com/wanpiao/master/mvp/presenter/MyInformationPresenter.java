package com.wanpiao.master.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.MyInformationContract;
import com.wanpiao.master.mvp.model.bean.UserSaveBean;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/23      onCreate
 */
public class MyInformationPresenter extends BasePresenter<MyInformationContract.View,MyInformationContract.Model> implements MyInformationContract.Presenter {

    @Inject
    public MyInformationPresenter(MyInformationContract.View rootView, MyInformationContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void uploadHeaderImg(String headerImg) {
        mModel.uploadHeaderImg(headerImg)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","s ========= "+s);
                        try {
                            //  "messageId": 401,
                            //  "message": "Unauthorized",
                            //  "timestamp": "2018-10-24T07:55:12.089+0000",
                            JSONObject jsonObject = new JSONObject(s);
                            String messageId = jsonObject.optString("messageId");
                            mRootView.showUploadHeaderData(s);
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
    public void updateUserInfo(String userId, String userName, String userSex, String userPortrait) {
        mModel.updateUserInfo(userId, userName, userSex, userPortrait)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>(){

                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+"," updateUserInfo ++++++++++ "+s);
                        mRootView.upDateUserInfo(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(BaseEntity<String> stringBaseEntity) {
                        super.onNext(stringBaseEntity);
                    }
                });
    }

    @Override
    public void requestLanding(Context context, String mail, String pwd) {
        mModel.requestLanding(context,mail,pwd )
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","s ----------  "+s);
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            int messageId = jsonObject.optInt("messageId");
                            String message = jsonObject.optString("message");
                            String token = jsonObject.optString("token");
                            //message字段代表token,登录成功之后需要把token保存到本地
                            //判定登录是否成功,message
                            if (messageId == 200 && message!=""){
                                SPUtils.put(App.getInstance(), "token",token);
                                //                                JSONArray jsonArray = jsonObject.optJSONArray("data");
//                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                                String userAccount = jsonObject1.optString("userAccount");
                                String userPassword = jsonObject1.optString("userPassword");
                                String userName = jsonObject1.optString("userName");
                                String id = jsonObject1.optString("id");
                                String userSex = jsonObject1.optString("userSex");
                                String userPortrait = jsonObject1.optString("userPortrait");
                                String isLogin = jsonObject1.optString("isLogin");
                                SPUtils.put(App.getInstance(), "isLogin", isLogin);
                                Log.d("jinyangyang+","userAccount = "+userAccount+"  , userPassword = "+userPassword+"   ,id = "+id);
                                SPUtils.put(App.getInstance(),"userAccount", userAccount);
                                SPUtils.put(App.getInstance(),"userPassword", userPassword);
                                SPUtils.put(App.getInstance(), "userName", userName);
                                SPUtils.put(App.getInstance(),"id",""+id);
                                SPUtils.put(App.getInstance(),"userSex",""+userSex);
                                SPUtils.put(App.getInstance(), "userPortrait", userPortrait);
                                Log.d("jinyangyang+ ","token is is is "+ SPUtils.get(App.getInstance(), "token",""));
                                mRootView.landingSuccess(s);
                            }else {
                                ToastUtil.show(App.getInstance(), "对不起，登录失败！");
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
