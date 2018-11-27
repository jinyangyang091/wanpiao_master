package com.wanpiao.master.mvp.contract;

import android.content.Context;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface MyFragmentContract {

    interface View extends IView {
        void landingSuccess(String s);
    }

    interface Presenter extends IPresenter {
        void requestLanding(Context context, String mail, String pwd);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> requestLanding(Context context, String mail, String pwd);
    }
}
