package com.wanpiao.master;

import com.mob.MobSDK;
import com.wanpiao.master.common.BaseApplication;
import com.wanpiao.master.di.DaggerMyAppComponent;
import com.wanpiao.master.utils.DeviceUtils;

import org.greenrobot.eventbus.EventBus;
import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class App extends BaseApplication {


    private static App mInstance;

    @Override
    protected void setSDK() {
//        //激光推送
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);

        MobSDK.init(this);


        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
                .getUnitsManager()
                .setSupportDP(true)
                .setSupportSP(true);

        initEventBus();
    }

    private void initEventBus() {
        //配置一个当事件没有订阅者的时候，发布一个事件时，EventBus不做任何事情。
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .installDefaultEventBus();
    }

    @Override
    protected void injectApp() {
        mInstance = this;
        DaggerMyAppComponent.builder()
                .myAppModule(getAppModule())
                .build()
                .inject(this);
        //初始化DeviceId
        DeviceUtils.init(this);
    }


}
