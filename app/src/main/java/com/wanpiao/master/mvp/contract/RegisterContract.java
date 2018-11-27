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
 * #0000      @Author: tianxiao     2018/10/18      onCreate
 */
public interface RegisterContract {

    interface View extends IView {
        void registerSuccess(String userAccount, String  userPassword);

        void landingSuccess();
    }

    interface Presenter extends IPresenter {
       void registerUser(String userName, int userSex , String userAccount, String userPassword);

        void requestLanding(Context context, String mail, String pwd);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> register(String userName, int userSex , String userAccount, String userPassword);

        Observable<BaseEntity<String>> requestLanding(Context context, String mail, String pwd);

    }
}
