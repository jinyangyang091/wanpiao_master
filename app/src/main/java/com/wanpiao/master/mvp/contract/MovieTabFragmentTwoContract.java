package com.wanpiao.master.mvp.contract;

import android.content.Context;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface MovieTabFragmentTwoContract {
    interface View extends IView {
        void showMovieTabTwo(String s);
        void showBannerTabTwo(String s);
        void showMovieTabTwoGallery(String s);
    }

    interface Presenter extends IPresenter {
        void requestMovieTabTwoData(Context context, int pageNum, int pageSize);
        void requestTabBanner(Context context);
        void requestMovieTabTwoGalleryData(Context context, int pageNum, int pageSize);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> requestMovieTabTwoData(Context context, int pageNum, int pageSize);
        Observable<BaseEntity<String>> requestTabBanner(Context context);
        Observable<BaseEntity<String>> requestMovieTabTwoGalleryData(Context context, int pageNum, int pageSize);
    }
}
