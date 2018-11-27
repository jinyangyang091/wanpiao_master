package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.MyInfoContract;
import com.wanpiao.master.mvp.model.MyInfoModel;
import com.wanpiao.master.mvp.ui.activity.MyInfoActivity;

import dagger.Module;
import dagger.Provides;
@Module
public class MyInfoModule {
    @Provides
    static MyInfoContract.View provideView(MyInfoActivity myInfoActivity){ return myInfoActivity;}

    @Provides
    static MyInfoContract.Model provideModel(MyInfoModel model) {
        return model;
    }
}
