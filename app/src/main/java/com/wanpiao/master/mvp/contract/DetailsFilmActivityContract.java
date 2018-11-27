package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 */
public interface DetailsFilmActivityContract {

    interface View extends IView {
        void showDetailFilmData(String data);
        void refreshDianzanState(String data);
        void commentFilm(String data);
        void showCommentList(String data);
    }

    interface Presenter extends IPresenter {
        void requestDetailFilmData(String movieId);
        void requestDianZanData(String userId, String joinId, String joinType, String operation);
        void commentFilm(String joinId, String joinType, String userId, String comment);
        void selectComment(String movieId, String pageNum, String pageSize);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> requestDetailFilm(String movieId);
        Observable<BaseEntity<String>> requestDianZan(String userId, String joinId, String joinType, String operation);
        Observable<BaseEntity<String>> commentFilm(String joinId, String joinType, String userId, String comment);
        Observable<BaseEntity<String>> selectComment(String movieId, String pageNum, String pageSize);
    }
}
