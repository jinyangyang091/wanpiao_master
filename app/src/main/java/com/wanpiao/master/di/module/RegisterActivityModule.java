package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.RegisterContract;
import com.wanpiao.master.mvp.model.RegisterModel;
import com.wanpiao.master.mvp.ui.activity.RegisterActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/18      onCreate
 */
@Module
public class RegisterActivityModule {

    @Provides
    static RegisterContract.View provideView(RegisterActivity registerActivity){ return registerActivity;}

    @Provides
    static RegisterContract.Model provideModel(RegisterModel model) {
        return model;
    }
}
