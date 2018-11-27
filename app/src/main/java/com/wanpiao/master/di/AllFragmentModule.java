package com.wanpiao.master.di;

import com.wanpiao.master.di.component.BaseFragmentComponent;
import com.wanpiao.master.di.module.HomePageFragmentModule;
import com.wanpiao.master.di.module.InlandFragmentModule;
import com.wanpiao.master.di.module.MoviePageFragmentModule;
import com.wanpiao.master.di.module.MovieTabFragmentOneModule;
import com.wanpiao.master.di.module.MovieTabFragmentTwoModule;
import com.wanpiao.master.di.module.MyFragmentModule;
import com.wanpiao.master.di.module.UpdateFragmentModule;
import com.wanpiao.master.di.scope.FragmentScope;
import com.wanpiao.master.mvp.model.MovieTabFragmentOneModel;
import com.wanpiao.master.mvp.ui.fragment.HomePageFragment;
import com.wanpiao.master.mvp.ui.fragment.InlandFragment;
import com.wanpiao.master.mvp.ui.fragment.MoviePageFragment;
import com.wanpiao.master.mvp.ui.fragment.MovieTabFragmentOne;
import com.wanpiao.master.mvp.ui.fragment.MovieTabFragmentTwo;
import com.wanpiao.master.mvp.ui.fragment.MyFragment;
import com.wanpiao.master.mvp.ui.fragment.UpdateFragment;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by
 * @author mwy on 2018/5/13.
 */
@Module(subcomponents = {BaseFragmentComponent.class})
public abstract class AllFragmentModule {
//    @FragmentScope
//    @ContributesAndroidInjector(modules = PendingOrderFragmentModule.class)
//    abstract PendingOrderFragment contributePendingOrderFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = HomePageFragmentModule.class)
    abstract HomePageFragment contributeHomePageFragmentInjector();


    @FragmentScope
    @ContributesAndroidInjector(modules = MoviePageFragmentModule.class)
    abstract MoviePageFragment contributeMoviePageFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = MovieTabFragmentOneModule.class)
    abstract MovieTabFragmentOne contributeMovieTabFragmentOneInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = MovieTabFragmentTwoModule.class)
    abstract MovieTabFragmentTwo contributeMovieTabFragmentTwoInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = MyFragmentModule.class)
    abstract MyFragment contributeMyFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = InlandFragmentModule.class)
    abstract InlandFragment contributeInlandFragmentInjector();

    @FragmentScope
    @ContributesAndroidInjector(modules = UpdateFragmentModule.class)
    abstract UpdateFragment contributeUpdateFragmentInjector();

}
