package com.wanpiao.master.di;


import com.wanpiao.master.di.component.BaseActivityComponent;
import com.wanpiao.master.di.module.AliyunVoiceModule;
import com.wanpiao.master.di.module.CurrencyWebModule;
import com.wanpiao.master.di.module.DetailsFilmModule;
import com.wanpiao.master.di.module.DetailsFilmsModule;
import com.wanpiao.master.di.module.GalleryDemoModule;
import com.wanpiao.master.di.module.GuideActivityModule;
import com.wanpiao.master.di.module.LoginActivityModule;
import com.wanpiao.master.di.module.MainActivityModule;
import com.wanpiao.master.di.module.MyCommentModule;
import com.wanpiao.master.di.module.MyInfoModule;
import com.wanpiao.master.di.module.MyInformationModule;
import com.wanpiao.master.di.module.RegisterActivityModule;
import com.wanpiao.master.di.module.SettingModule;
import com.wanpiao.master.di.module.SoundRecordModule;
import com.wanpiao.master.di.module.SplashModule;
import com.wanpiao.master.di.module.UserReviewModule;
import com.wanpiao.master.di.module.VideoPlayerActivityModule;
import com.wanpiao.master.di.scope.ActivityScope;
import com.wanpiao.master.mvp.ui.activity.AliyunVoiceActivity;
import com.wanpiao.master.mvp.ui.activity.CurrencyWebActivity;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmActivity;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmsActivity;
import com.wanpiao.master.mvp.ui.activity.GalleryDemoActivity;
import com.wanpiao.master.mvp.ui.activity.GuideActivity;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;
import com.wanpiao.master.mvp.ui.activity.MainActivity;
import com.wanpiao.master.mvp.ui.activity.MyCommentActivity;
import com.wanpiao.master.mvp.ui.activity.MyInfoActivity;
import com.wanpiao.master.mvp.ui.activity.MyInformationActivity;
import com.wanpiao.master.mvp.ui.activity.RegisterActivity;
import com.wanpiao.master.mvp.ui.activity.SettingActivity;
import com.wanpiao.master.mvp.ui.activity.SoundRecordActivity;
import com.wanpiao.master.mvp.ui.activity.SplashActivity;
import com.wanpiao.master.mvp.ui.activity.UserReviewsActivity;
import com.wanpiao.master.mvp.ui.activity.VideoPlayerActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * 描述: 所有ActivityModule
 * --------------------------------------------
 * 工程:
 *
 */
@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AllActivitysModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity contributeLoginActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity contributeRegisterActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = DetailsFilmModule.class)
    abstract DetailsFilmActivity contributeDetailsFilmActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = UserReviewModule.class)
    abstract UserReviewsActivity contributeUserReviewsActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = SettingModule.class)
    abstract SettingActivity contributeSettingActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = CurrencyWebModule.class)
    abstract CurrencyWebActivity contributeCurrencyWebActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MyInfoModule.class)
    abstract MyInfoActivity contributeMyInfoActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MyInformationModule.class)
    abstract MyInformationActivity contributeMyInformationActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MyCommentModule.class)
    abstract MyCommentActivity contributeMyCommentActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity contributeSplashActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = GuideActivityModule.class)
    abstract GuideActivity contributeGuideActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = DetailsFilmsModule.class)
    abstract DetailsFilmsActivity contributeDetailsFilmsActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = VideoPlayerActivityModule.class)
    abstract VideoPlayerActivity contributeVideoPlayerActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = AliyunVoiceModule.class)
    abstract AliyunVoiceActivity contributeAliyunVoiceActivityInjector();


    @ActivityScope
    @ContributesAndroidInjector(modules = SoundRecordModule.class)
    abstract SoundRecordActivity contributeSoundRecordActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = GalleryDemoModule.class)
    abstract GalleryDemoActivity contributeGalleryDemoActivityInjector();

}
