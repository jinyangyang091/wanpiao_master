package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.DetailsFilmActivityContract;
import com.wanpiao.master.mvp.model.DetailsFilmModel;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/19      onCreate
 */
@Module
public class DetailsFilmModule {
    @Provides
    static DetailsFilmActivityContract.View provideView(DetailsFilmActivity activity){ return activity;}

    @Provides
    static DetailsFilmActivityContract.Model provideModel(DetailsFilmModel model) {
        return model;
    }
}
