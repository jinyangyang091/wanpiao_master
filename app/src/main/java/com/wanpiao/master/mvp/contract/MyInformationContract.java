package com.wanpiao.master.mvp.contract;

import android.content.Context;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/23      onCreate
 */
public interface MyInformationContract {
    interface View extends IView {
        void showUploadHeaderData(String data);
        void upDateUserInfo(String data);
        void landingSuccess(String s);
    }

    interface Presenter extends IPresenter {
        void uploadHeaderImg(String headerImg);
        void updateUserInfo(String userId, String userName, String userSex, String userPortrait);
        void requestLanding(Context context, String mail, String pwd);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> uploadHeaderImg(String headerImg);
        Observable<BaseEntity<String>> updateUserInfo(String userId, String userName, String userSex, String userPortrait);
        Observable<BaseEntity<String>> requestLanding(Context context, String mail, String pwd);
    }
}
