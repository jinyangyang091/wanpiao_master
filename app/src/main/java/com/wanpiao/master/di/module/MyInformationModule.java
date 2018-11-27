package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.MyInformationContract;
import com.wanpiao.master.mvp.model.MyInformationModel;
import com.wanpiao.master.mvp.ui.activity.MyInformationActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/23      onCreate
 */
@Module
public class MyInformationModule {

    @Provides
    static MyInformationContract.View provideView(MyInformationActivity activity){ return activity;}

    @Provides
    static MyInformationContract.Model provideModel(MyInformationModel model) {
        return model;
    }
}
