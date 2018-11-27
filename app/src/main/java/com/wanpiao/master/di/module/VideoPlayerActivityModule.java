package com.wanpiao.master.di.module;


import com.wanpiao.master.mvp.contract.VideoPlayerContract;
import com.wanpiao.master.mvp.model.VideoPlayerModel;
import com.wanpiao.master.mvp.ui.activity.VideoPlayerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoPlayerActivityModule {
    @Provides
    static VideoPlayerContract.View provideView(VideoPlayerActivity activity){ return activity;}

    @Provides
    static VideoPlayerContract.Model provideModel(VideoPlayerModel model) {
        return model;
    }
}
