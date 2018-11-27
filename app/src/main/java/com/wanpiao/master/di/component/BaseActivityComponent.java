package com.wanpiao.master.di.component;


import com.wanpiao.master.common.BaseActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * 描述: BaseActivityComponent
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-05-02 18:03
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity>{}
}
