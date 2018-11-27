package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.HomePageFragmentContract;
import com.wanpiao.master.mvp.model.HomePageFragmentModel;
import com.wanpiao.master.mvp.ui.fragment.HomePageFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class HomePageFragmentModule {

    @Provides
    static HomePageFragmentContract.View provideView(HomePageFragment fragment){ return fragment;}

    @Provides
    static HomePageFragmentContract.Model provideModel(HomePageFragmentModel model) {
        return model;
    }
}
