package com.wanpiao.master.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.wanpiao.master.common.BaseObserver;
import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.MovieTabFragmentTwoContract;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

public class MovieTabFragmentTwoPresenter
        extends BasePresenter<MovieTabFragmentTwoContract.View, MovieTabFragmentTwoContract.Model>
        implements MovieTabFragmentTwoContract.Presenter{
    @Inject
    public MovieTabFragmentTwoPresenter(MovieTabFragmentTwoContract.View rootView, MovieTabFragmentTwoContract.Model model){
        super(rootView, model);
    }

    @Override
    public void requestMovieTabTwoData(Context context, int pageNum, int pageSize) {
        mModel.requestMovieTabTwoData(context, pageNum, pageSize)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+"," requestMovieTabTwoData s ========= "+s);
                        try {
                            //  "messageId": 401,
                            //  "message": "Unauthorized",
                            //  "timestamp": "2018-10-24T07:55:12.089+0000",
                            JSONObject jsonObject = new JSONObject(s);
                            mRootView.showMovieTabTwo(s);
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
                            mRootView.showBannerTabTwo(s);
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
                        Log.d("jinyangyang+","requestMovieTabOneData data error e is "+e);
                        //LoginActivity.startLoginActivity(context);
                    }
                });
    }

    @Override
    public void requestMovieTabTwoGalleryData(Context context, int pageNum, int pageSize) {
        mModel.requestMovieTabTwoGalleryData(context, pageNum, pageSize)
                .as(bindLifecycle())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    protected void onHandleSuccess(String s) {
                        Log.d("jinyangyang+"," requestMovieTabTwoData s ========= "+s);
                        try {
                            //  "messageId": 401,
                            //  "message": "Unauthorized",
                            //  "timestamp": "2018-10-24T07:55:12.089+0000",
                            JSONObject jsonObject = new JSONObject(s);
                            mRootView.showMovieTabTwoGallery(s);
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
                    }
                });
    }
}
