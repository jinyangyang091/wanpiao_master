package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.SoundRecordContract;
import com.wanpiao.master.mvp.model.SoundRecordModel;
import com.wanpiao.master.mvp.ui.activity.SoundRecordActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class SoundRecordModule {
    @Provides
    static SoundRecordContract.View provideView(SoundRecordActivity activity){ return activity;}

    @Provides
    static SoundRecordContract.Model provideModel(SoundRecordModel model) {
        return model;
    }
}
