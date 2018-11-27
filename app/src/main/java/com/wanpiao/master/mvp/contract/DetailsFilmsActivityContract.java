package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface DetailsFilmsActivityContract {
    interface View extends IView {
        void showDetailFilmData(String data);
        void refreshDianzanState(String data);
        void refreshInfoState(String data);
    }

    interface Presenter extends IPresenter {
        void requestDetailFilmData(String movieId);
        void requestDianZanData(String userId, String joinId, String joinType, String operation);
        void requestInfoData(String userId, String joinId, String joinType, String operation);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> requestDetailFilm(String movieId);
        Observable<BaseEntity<String>> requestDianZan(String userId, String joinId, String joinType, String operation);
        Observable<BaseEntity<String>> requestInfo(String userId, String joinId, String joinType, String operation);
    }

}
