package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.HomePageFragmentContract;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class HomePageFragmentPresenter
        extends BasePresenter<HomePageFragmentContract.View,HomePageFragmentContract.Model>
        implements HomePageFragmentContract.Presenter {

    @Inject
    public HomePageFragmentPresenter(HomePageFragmentContract.View rootView, HomePageFragmentContract.Model model) {
        super(rootView, model);
    }


    @Override
    public void requestBannerData() {
        mModel.requestBannerData()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        mRootView.showBannerView(s);
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
    public void requestInfoData() {
        mModel.requestInfoData()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        mRootView.showInfoShow(s);
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
    public void requestPreData() {
        mModel.requestPreData()
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        mRootView.showPreShow(s);
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
    public void requestExInfoData(String pageNum) {
        mModel.requestExInfoData(pageNum)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        mRootView.showExInfoShow(s);
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
