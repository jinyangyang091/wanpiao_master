package com.wanpiao.master.mvp.presenter;

import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.DetailsFilmActivityContract;
import com.wanpiao.master.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 */
public class DetailsFilmPresenter extends BasePresenter<DetailsFilmActivityContract.View,DetailsFilmActivityContract.Model> implements DetailsFilmActivityContract.Presenter {

    @Inject
    public DetailsFilmPresenter(DetailsFilmActivityContract.View rootView, DetailsFilmActivityContract.Model model) {
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
    public void commentFilm(String joinId, String joinType, String userId, String comment) {
        mModel.commentFilm(joinId, joinType,userId, comment)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","commentFilm   s ========= "+s);
                        mRootView.commentFilm(s);
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
    public void selectComment(String movieId, String pageNum, String pageSize) {
        mModel.selectComment(movieId, pageNum,pageSize)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","selectComment   s ========= "+s);
                        mRootView.showCommentList(s);
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
