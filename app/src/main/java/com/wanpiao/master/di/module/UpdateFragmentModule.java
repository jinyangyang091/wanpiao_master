package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.UpdateFragmentContract;
import com.wanpiao.master.mvp.model.UpdateFragmentModel;
import com.wanpiao.master.mvp.ui.fragment.UpdateFragment;

import dagger.Module;
import dagger.Provides;
@Module
public class UpdateFragmentModule {
    @Provides
    static UpdateFragmentContract.View provideView(UpdateFragment fragment){ return fragment;}

    @Provides
    static UpdateFragmentContract.Model provideModel(UpdateFragmentModel model) {
        return model;
    }
}
