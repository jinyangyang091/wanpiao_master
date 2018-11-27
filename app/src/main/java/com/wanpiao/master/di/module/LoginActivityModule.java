package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.LoginContract;
import com.wanpiao.master.mvp.model.LoginModel;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
@Module
public class LoginActivityModule {

    @Provides
    static LoginContract.View provideView(LoginActivity loginActivity){ return loginActivity;}

    @Provides
    static LoginContract.Model provideModel(LoginModel model) {
        return model;
    }
}
