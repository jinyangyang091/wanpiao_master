package com.wanpiao.master.di.module;


import com.wanpiao.master.mvp.contract.MovieTabFragmentOneContract;
import com.wanpiao.master.mvp.model.MovieTabFragmentOneModel;
import com.wanpiao.master.mvp.ui.fragment.MovieTabFragmentOne;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieTabFragmentOneModule {
    @Provides
    static MovieTabFragmentOneContract.View provideView(MovieTabFragmentOne fragment){ return fragment;}

    @Provides
    static MovieTabFragmentOneContract.Model provideModel(MovieTabFragmentOneModel model) {
        return model;
    }
}
