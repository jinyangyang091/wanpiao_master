package com.wanpiao.master.mvp.contract;

import android.content.Context;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface MovieTabFragmentOneContract {
    interface View extends IView {
        void showMovieTabOne(String s);
        void showBannerTabOne(String s);
        void showMovieTabOneGallery(String s);
    }

    interface Presenter extends IPresenter {
        void requestMovieTabOneData(Context context, int pageNum, int pageSize);
        void requestTabBanner(Context context);
        void requestMovieTabOneGalleryData(Context context, int pageNum, int pageSize);
    }


    interface Model extends IModel {
        Observable<BaseEntity<String>> requestMovieTabOneData(Context context, int pageNum, int pageSize);
        Observable<BaseEntity<String>> requestTabBanner(Context context);

        Observable<BaseEntity<String>> requestMovieTabOneGalleryData(Context context, int pageNum, int pageSize);
    }
}
