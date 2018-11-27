package com.wanpiao.master.di.module;

import com.wanpiao.master.mvp.contract.MyFragmentContract;
import com.wanpiao.master.mvp.model.MyFragmentModel;
import com.wanpiao.master.mvp.ui.fragment.MyFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MyFragmentModule{
        @Provides
        static MyFragmentContract.View provideView(MyFragment fragment){ return fragment;}

        @Provides
        static MyFragmentContract.Model provideModel(MyFragmentModel model) {
            return model;
        }
}
