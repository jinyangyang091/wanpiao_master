package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.SplashContract;
import com.wanpiao.master.mvp.model.SplashModel;
import com.wanpiao.master.mvp.ui.activity.SplashActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {
    @Provides
    static SplashContract.View provideView(SplashActivity activity){ return activity;}

    @Provides
    static SplashContract.Model provideModel(SplashModel model) {
        return model;
    }
}
