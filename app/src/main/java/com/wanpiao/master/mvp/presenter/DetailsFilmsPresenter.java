package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.DetailsFilmsActivityContract;

import javax.inject.Inject;

public class DetailsFilmsPresenter extends BasePresenter<DetailsFilmsActivityContract.View,DetailsFilmsActivityContract.Model> implements DetailsFilmsActivityContract.Presenter {
    @Inject
    public DetailsFilmsPresenter(DetailsFilmsActivityContract.View rootView, DetailsFilmsActivityContract.Model model) {
        super(rootView, model);
    }

    @Override
    public void requestDetailFilmData(String movieId) {
        mModel.requestDetailFilm(movieId)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","requestDetailFilmData   s ========= "+s);
                        mRootView.showDetailFilmData(s);
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
    public void requestDianZanData(String userId, String joinId, String joinType, String operation) {
        mModel.requestDianZan(userId, joinId,joinType, operation)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","requestDianZanData   s ========= "+s);
                        mRootView.refreshDianzanState(s);
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
    public void requestInfoData(String userId, String joinId, String joinType, String operation) {
        mModel.requestDianZan(userId, joinId,joinType, operation)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","requestInfoData   s ========= "+s);
                        mRootView.refreshInfoState(s);
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
