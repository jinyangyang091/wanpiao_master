package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.MainContract;
import com.wanpiao.master.mvp.model.MainModel;
import com.wanpiao.master.mvp.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * 描述: MainActivityModule
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-05-09 16:41
 */
@Module
public class MainActivityModule {

    @Provides
    static MainContract.View provideView(MainActivity mainActivity){ return mainActivity;}

    @Provides
    static MainContract.Model provideModel(MainModel model) {
        return model;
    }
}
