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
public interface LoginContract {

    interface View extends IView {
       void landingSuccess();
    }

    interface Presenter extends IPresenter {
        void requestLanding(Context context, String mail, String pwd);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> requestLanding(Context context, String mail, String pwd);
    }
}
