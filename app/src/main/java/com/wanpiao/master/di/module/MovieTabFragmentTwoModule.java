package com.wanpiao.master.di.module;


import com.wanpiao.master.mvp.contract.MovieTabFragmentOneContract;
import com.wanpiao.master.mvp.contract.MovieTabFragmentTwoContract;
import com.wanpiao.master.mvp.model.MovieTabFragmentOneModel;
import com.wanpiao.master.mvp.model.MovieTabFragmentTwoModel;
import com.wanpiao.master.mvp.ui.fragment.MovieTabFragmentOne;
import com.wanpiao.master.mvp.ui.fragment.MovieTabFragmentTwo;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieTabFragmentTwoModule {
    @Provides
    static MovieTabFragmentTwoContract.View provideView(MovieTabFragmentTwo fragment){ return fragment;}

    @Provides
    static MovieTabFragmentTwoContract.Model provideModel(MovieTabFragmentTwoModel model) {
        return model;
    }
}
