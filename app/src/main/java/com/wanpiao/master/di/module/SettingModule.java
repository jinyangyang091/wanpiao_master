package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.SettingContract;
import com.wanpiao.master.mvp.model.SettingModel;
import com.wanpiao.master.mvp.ui.activity.SettingActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
@Module
public class SettingModule {
    @Provides
    static SettingContract.View provideView(SettingActivity activity){ return activity;}

    @Provides
    static SettingContract.Model provideModel(SettingModel model) {
        return model;
    }
}
