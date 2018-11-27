package com.wanpiao.master.di.component;


import com.wanpiao.master.common.BaseFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by
 * @author mwy on 2018/5/13.
 */
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseFragmentComponent  extends AndroidInjector<BaseFragment>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment>{}
}
