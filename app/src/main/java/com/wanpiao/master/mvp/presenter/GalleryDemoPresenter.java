package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.GalleryDemoContract;

import javax.inject.Inject;

public class GalleryDemoPresenter extends BasePresenter<GalleryDemoContract.View, GalleryDemoContract.Model> implements GalleryDemoContract.Presenter {
    @Inject
    public GalleryDemoPresenter(GalleryDemoContract.View rootView, GalleryDemoContract.Model model) {
        super(rootView, model);
    }
}
