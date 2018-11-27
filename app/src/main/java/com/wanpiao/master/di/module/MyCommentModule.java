package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.MyCommentContract;
import com.wanpiao.master.mvp.model.MyCommentModel;
import com.wanpiao.master.mvp.ui.activity.MyCommentActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class MyCommentModule {
    @Provides
    static MyCommentContract.View provideView(MyCommentActivity myInfoActivity){ return myInfoActivity;}

    @Provides
    static MyCommentContract.Model provideModel(MyCommentModel model) {
        return model;
    }
}
