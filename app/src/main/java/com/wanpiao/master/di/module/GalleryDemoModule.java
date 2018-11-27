package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.GalleryDemoContract;
import com.wanpiao.master.mvp.model.GalleryDemoModel;
import com.wanpiao.master.mvp.ui.activity.GalleryDemoActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class GalleryDemoModule {
    @Provides
    static GalleryDemoContract.View provideView(GalleryDemoActivity activity){ return activity;}

    @Provides
    static GalleryDemoContract.Model provideModel(GalleryDemoModel model) {
        return model;
    }
}
