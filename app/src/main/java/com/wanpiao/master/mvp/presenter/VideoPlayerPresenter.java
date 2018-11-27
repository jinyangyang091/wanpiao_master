package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.VideoPlayerContract;

import javax.inject.Inject;

public class VideoPlayerPresenter extends BasePresenter<VideoPlayerContract.View, VideoPlayerContract.Model> implements VideoPlayerContract.Presenter {
    @Inject
    public VideoPlayerPresenter(VideoPlayerContract.View rootView, VideoPlayerContract.Model model) {
        super(rootView, model);
    }

}
