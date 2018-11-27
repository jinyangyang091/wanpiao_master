package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.GuideContract;

import javax.inject.Inject;

public class GuideModel implements GuideContract.Model {
    @Inject
    public GuideModel() {
    }
    @Override
    public void onDestroy() {

    }
}
