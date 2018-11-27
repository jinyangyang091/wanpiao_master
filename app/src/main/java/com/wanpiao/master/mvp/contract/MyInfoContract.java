package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface MyInfoContract {
    interface View extends IView {
        void showMyInfoList(String response);
    }

    interface Presenter extends IPresenter {
        void selectMyInfo(String userId, int pageNum , int PageSize);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> selectMyInfo(String userId, int pageNum , int PageSize);
    }
}
