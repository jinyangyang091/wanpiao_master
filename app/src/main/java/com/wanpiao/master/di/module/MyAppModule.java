package com.wanpiao.master.di.module;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 描述: MyAppModule
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-05-03 11:26
 */
@Module
public class MyAppModule {
    private BaseApplication application;

    public MyAppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    BaseApplication provideApplication() {
        return application;
    }
}
