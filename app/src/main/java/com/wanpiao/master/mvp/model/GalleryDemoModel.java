package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.GalleryDemoContract;

import javax.inject.Inject;

public class GalleryDemoModel implements GalleryDemoContract.Model {
    @Inject
    public GalleryDemoModel(){

    }

    @Override
    public void onDestroy() {

    }
}
