package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.GuideContract;
import com.wanpiao.master.mvp.model.GuideModel;
import com.wanpiao.master.mvp.ui.activity.GuideActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class GuideActivityModule {
    @Provides
    static GuideContract.View provideView(GuideActivity activity){ return activity;}

    @Provides
    static GuideContract.Model provideModel(GuideModel model) {
        return model;
    }
}
