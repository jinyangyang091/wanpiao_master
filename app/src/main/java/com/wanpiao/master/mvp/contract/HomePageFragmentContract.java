package com.wanpiao.master.mvp.contract;

import com.wanpiao.master.common.BaseEntity;
import com.wanpiao.master.common.IModel;
import com.wanpiao.master.common.IPresenter;
import com.wanpiao.master.common.IView;

import io.reactivex.Observable;

public interface HomePageFragmentContract {

    interface View extends IView {
        void showBannerView(String data);
        void showInfoShow(String data);
        void showPreShow(String data);
        void showExInfoShow(String data);
    }

    interface Presenter extends IPresenter {
        //首页Banner数据
        void requestBannerData();
        //首页资讯数据
        void requestInfoData();
        //首页预告片数据
        void requestPreData();
        //首页专访数据
        void requestExInfoData(String pageNum);
    }

    interface Model extends IModel {
        Observable<BaseEntity<String>> requestBannerData();
        Observable<BaseEntity<String>> requestInfoData();
        Observable<BaseEntity<String>> requestPreData();
        Observable<BaseEntity<String>> requestExInfoData(String pageNum);
    }
}
