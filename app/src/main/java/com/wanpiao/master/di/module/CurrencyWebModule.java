package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.CurrencyWebContract;
import com.wanpiao.master.mvp.model.CurrencyWebModel;
import com.wanpiao.master.mvp.ui.activity.CurrencyWebActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
@Module
public class CurrencyWebModule {
    @Provides
    static CurrencyWebContract.View provideView(CurrencyWebActivity activity){ return activity;}

    @Provides
    static CurrencyWebContract.Model provideModel(CurrencyWebModel model) {
        return model;
    }
}
