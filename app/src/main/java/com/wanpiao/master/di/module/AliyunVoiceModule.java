package com.wanpiao.master.di.module;


import com.wanpiao.master.mvp.contract.AliyunVoiceContract;
import com.wanpiao.master.mvp.model.AliyunVoiceModel;
import com.wanpiao.master.mvp.ui.activity.AliyunVoiceActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class AliyunVoiceModule {
    @Provides
    static AliyunVoiceContract.View provideView(AliyunVoiceActivity activity){ return activity;}

    @Provides
    static AliyunVoiceContract.Model provideModel(AliyunVoiceModel model) {
        return model;
    }

}
