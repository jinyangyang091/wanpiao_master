package com.wanpiao.master.di;

import com.google.gson.Gson;
import com.wanpiao.master.common.BaseApplication;
import com.wanpiao.master.di.module.MyAppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * 描述: AppComponent
 * --------------------------------------------
 * 工程:
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AllActivitysModule.class,
        AllFragmentModule.class,
        MyAppModule.class
})
public interface MyAppComponent {

    Gson gson();

    void inject(BaseApplication baseApplication);
}
