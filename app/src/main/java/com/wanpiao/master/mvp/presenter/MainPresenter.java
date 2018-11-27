package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.contract.MainContract;
import com.wanpiao.master.mvp.ui.fragment.UpDateFragmentS;
import com.wanpiao.master.utils.GetVersionInfoUtils;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class MainPresenter extends BasePresenter<MainContract.View,MainContract.Model>
        implements MainContract.Presenter{

    @Inject
    public MainPresenter(MainContract.View rootView, MainContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void queryUser() {
        mModel.queryUser().
                as(bindLifecycle())
                .subscribe(new BaseObserver<User>() {
                    @Override
                    protected void onHandleSuccess(User user) {
                        mModel.saveUser(user);
                        mRootView.queryUserSuccess(user);
                    }

                    @Override
                    protected void onFinally() {
                        mRootView.hideLoading();
                    }
                });
    }

    @Override
    public void versionUpdate() {
        mModel.versionUpdate()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>(){
                    @Override
                    protected void onHandleSuccess(String s) {
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            String messageId = jsonObject.optString("messageId");
                            if(messageId.equals("200")){
                                String message = jsonObject.optString("message");
                                Log.d("jinyangyang+","messageId ========++++++++++++  "+messageId);
                                mRootView.showVersionUpdateView(s);
                            }else{
                                Log.d("jinyangyang+","获取版本更新信息失败！");
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    protected void onFinally() {
                        super.onFinally();
                    }
                });
    }
}
