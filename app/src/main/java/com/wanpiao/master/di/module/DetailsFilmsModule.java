package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.DetailsFilmsActivityContract;
import com.wanpiao.master.mvp.model.DetailsFilmsModel;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmsActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailsFilmsModule {
    @Provides
    static DetailsFilmsActivityContract.View provideView(DetailsFilmsActivity activity){ return activity;}

    @Provides
    static DetailsFilmsActivityContract.Model provideModel(DetailsFilmsModel model) {
        return model;
    }
}
