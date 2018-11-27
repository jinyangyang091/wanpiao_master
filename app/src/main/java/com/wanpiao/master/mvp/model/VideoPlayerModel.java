package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.VideoPlayerContract;

import javax.inject.Inject;

public class VideoPlayerModel implements VideoPlayerContract.Model {

    @Inject
    public VideoPlayerModel() {
    }
    @Override
    public void onDestroy() {

    }
}
