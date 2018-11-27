package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.SplashContract;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter<SplashContract.View,SplashContract.Model>
        implements SplashContract.Presenter {
    @Inject
    public SplashPresenter(SplashContract.View rootView, SplashContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void requestSplash() {
        mModel.requestSplash()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","requestSplash s ========= "+s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String messageId = jsonObject.optString("messageId");
                            String token = jsonObject.optString("token");
                            //第一次获取游客token
                            Log.d("jinyangyang+","requestSplash token is "+token);
                            if(SPUtils.get(App.getInstance(), "token", "").equals("")){
                                Log.d("jinyangyang+","cur token is not null ,cur token is "+token);
                                SPUtils.put(App.getInstance(), "token", token);
                            }else {
                                Log.d("jinyangyang+","first install cur token is null ,cur token is "+token);
                            }
                            Log.d("jinyangyang+","messageId = "+messageId);
                            if (messageId.equals("200")){

                                mRootView.showAd(s);
                                return;
                            }else if(messageId.equals("400")){
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
