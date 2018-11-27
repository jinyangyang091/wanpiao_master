package com.wanpiao.master.mvp.contract;

import android.content.Context;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;
import com.wanpiao.master.di.module.entity.User;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public interface MainContract {
     interface View extends IView {
        void queryUserSuccess(User user);
        void showVersionUpdateView(String s);
        void startDownloadNewVersion(String downloadUrl);
    }

    interface Presenter extends IPresenter {
        void queryUser();
        void versionUpdate();
    }

    interface Model extends IModel {
        User getUser(Context context);
        void saveUser(User user);
        Observable<BaseEntity<User>> queryUser();
        Observable<BaseEntity<String>> versionUpdate();
    }
}
