package com.wanpiao.master.mvp.contract;

import android.content.Context;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;
import com.wanpiao.master.di.module.entity.User;

import io.reactivex.Observable;

public interface SplashContract {
    interface View extends IView {
        void showAd(String s);
    }

    interface Presenter extends IPresenter {
        void requestSplash();
    }

    interface Model extends IModel {

        Observable<BaseEntity<String>> requestSplash();
    }
}
