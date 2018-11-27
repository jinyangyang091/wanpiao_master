package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public interface SettingContract {
    interface View extends IView {
        void logOutSucess();
        void showPrivacy(String s);
        void logOutFail();
    }

    interface Presenter extends IPresenter {
        void requestLogOut();
        void privacy();
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> logOut();
        Observable<BaseEntity<String>> privacy();
    }
}
