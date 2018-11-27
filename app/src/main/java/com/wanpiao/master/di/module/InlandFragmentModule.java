package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.InlandFragmentContract;
import com.wanpiao.master.mvp.model.InlandFragmentModel;
import com.wanpiao.master.mvp.ui.fragment.InlandFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class InlandFragmentModule {
    @Provides
    static InlandFragmentContract.View provideView(InlandFragment fragment){ return fragment;}

    @Provides
    static InlandFragmentContract.Model provideModel(InlandFragmentModel model) {
        return model;
    }
}
