package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface MyCommentContract {
    interface View extends IView {
        void showMyCommentList(String response);
    }

    interface Presenter extends IPresenter {
        void selectMyComment(String userId, int pageNum, int pageSize);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> selectMyComment(String userId, int pageNum, int pageSize);
    }
}
