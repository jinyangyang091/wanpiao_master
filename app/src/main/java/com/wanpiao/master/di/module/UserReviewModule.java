package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.UserReviewsContract;
import com.wanpiao.master.mvp.model.UserReviewModel;
import com.wanpiao.master.mvp.ui.activity.UserReviewsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/21      onCreate
 */
@Module
public class UserReviewModule {
    @Provides
    static UserReviewsContract.View provideView(UserReviewsActivity registerActivity){ return registerActivity;}

    @Provides
    static UserReviewsContract.Model provideModel(UserReviewModel model) {
        return model;
    }
}
