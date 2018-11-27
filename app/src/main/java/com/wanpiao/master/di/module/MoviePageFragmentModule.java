package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.MoviePageFragmentContract;
import com.wanpiao.master.mvp.model.MoviePageFragmentModel;
import com.wanpiao.master.mvp.ui.fragment.MoviePageFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviePageFragmentModule {
    @Provides
    static MoviePageFragmentContract.View provideView(MoviePageFragment fragment){ return fragment;}

    @Provides
    static MoviePageFragmentContract.Model provideModel(MoviePageFragmentModel model) {
        return model;
    }
}
