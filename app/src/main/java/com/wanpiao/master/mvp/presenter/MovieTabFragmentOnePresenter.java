package com.wanpiao.master.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.MovieTabFragmentOneContract;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;
import com.wanpiao.master.mvp.ui.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class MovieTabFragmentOnePresenter
        extends BasePresenter<MovieTabFragmentOneContract.View, MovieTabFragmentOneContract.Model>
        implements MovieTabFragmentOneContract.Presenter {
    @Inject
    public MovieTabFragmentOnePresenter(MovieTabFragmentOneContract.View rootView, MovieTabFragmentOneContract.Model model){
        super(rootView, model);
    }

    @Override
    public void requestMovieTabOneData(Context context, int pageNum, int pageSize) {
        mModel.requestMovieTabOneData(context, pageNum, pageSize)
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
                            mRootView.showMovieTabOne(s);
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
                        Log.d("jinyangyang+","requestMovieTabOneData data error e is "+e);
                        //LoginActivity.startLoginActivity(context);
                    }
                });
    }

    @Override
    public void requestTabBanner(Context context) {
        mModel.requestTabBanner(context)
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
                            mRootView.showBannerTabOne(s);
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
                        Log.d("jinyangyang+","requestMovieTabOneData data error e is "+e);
                        //LoginActivity.startLoginActivity(context);
                    }
                });
    }

    @Override
    public void requestMovieTabOneGalleryData(Context context, int pageNum, int pageSize) {
        mModel.requestMovieTabOneGalleryData(context, pageNum, pageSize)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+","requestMovieTabOneGalleryData ========= "+s);
                        try {
                            //  "messageId": 401,
                            //  "message": "Unauthorized",
                            //  "timestamp": "2018-10-24T07:55:12.089+0000",
                            JSONObject jsonObject = new JSONObject(s);
                            mRootView.showMovieTabOneGallery(s);
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
                        //super.onError(e);
                        Log.d("jinyangyang+","requestMovieTabOneGalleryData data error e is "+e);
                        //LoginActivity.startLoginActivity(context);
                    }
                });
    }
}
