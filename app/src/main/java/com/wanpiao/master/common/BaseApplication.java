package com.wanpiao.master.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Fragment;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.wanpiao.master.di.module.MyAppModule;
import com.wanpiao.master.utils.ApplicationUtils;
import com.wanpiao.master.utils.L;
import com.wanpiao.master.utils.SDCardUtil;
import com.wanpiao.master.utils.ToastUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasBroadcastReceiverInjector;
import dagger.android.HasContentProviderInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.HasServiceInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * 描述: Application 的基类
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2016-07-14 11:04
 */
public abstract class BaseApplication extends Application implements HasActivityInjector,
        HasBroadcastReceiverInjector,
        HasFragmentInjector,
        HasServiceInjector,
        HasContentProviderInjector,
        HasSupportFragmentInjector {

    protected static BaseApplication mInstance;

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    @Inject
    DispatchingAndroidInjector<androidx.fragment.app.Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<BroadcastReceiver> broadcastReceiverInjector;
    @Inject
    DispatchingAndroidInjector<Service> serviceInjector;
    @Inject
    DispatchingAndroidInjector<ContentProvider> contentProviderInjector;

    @Inject
    public Gson gson;

    //activity 在前台的数量
    int activityInForegroundCount = 0;
    private ActivityLifecycleCallbacks activityLifecycleCallbacks;
    protected boolean isDebug;

    @Override
    public void onCreate() {
        super.onCreate();
        String currentProcessName = getProcessName(Process.myPid());
        //防止app 多进程 初始化多个application
        if (currentProcessName == null || !getPackageName().equals(currentProcessName)){
            return;
        }
        mInstance = this;
        isDebug = ApplicationUtils.isApkDebugable(this);
        //配置时候显示toast
        ToastUtil.isShow = true;
        //设置SD卡 项目目录
        createAPPDir();
        //Dagger inject
        initDI();
        //注册Activity生命周期监听
        registerLifecycleCallbacks();
        //设置第三方sdk
        setSDK();
        /*//配置程序异常退出,异常捕获处理类
        setDefaultUncaughtExceptionHandler();*/
    }


    /**
     * 设置第三方sdk
     */
    protected abstract void setSDK();

    private void initDI() {
        injectApp();
    }

    /**
     * 这是类库底层的injectApp代码示例，你应该在你的Module中重写该方法:
     * <p>
     * DaggerMyAppComponent.builder()
     * .cacheModule(getCacheModule())
     * .appModule(getAppModule())
     * .globalConfigModule(getGlobalConfigModule())
     * .httpClientModule(getHttpClientModule())
     * .serviceModule(getServiceModule())
     * .build()
     * .inject(this);
     */
    abstract protected void injectApp();

    /**
     * 创建SD卡 项目目录
     */
    public void createAPPDir() {
        SDCardUtil.initAppSDCardPath(this);
    }

    /**
     * 配置程序异常退出,异常捕获处理类
     */
    //public abstract void setDefaultUncaughtExceptionHandler();

    public static BaseApplication getInstance() {
        return mInstance;
    }

    protected MyAppModule getAppModule() {
        return new MyAppModule(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Dependencies Injection by dagger.android
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<BroadcastReceiver> broadcastReceiverInjector() {
        return broadcastReceiverInjector;
    }

    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        return contentProviderInjector;
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceInjector;
    }

    @Override
    public AndroidInjector<androidx.fragment.app.Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }


    /**
     * 注册activity生命周期监听
     */
    private void registerLifecycleCallbacks(){

        activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                L.i("Application",activity.getClass().getSimpleName()+" Created");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activityInForegroundCount == 0){
                    Log.i("Application","app to foreground");
                }
                activityInForegroundCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                com.wanpiao.master.common.ActivityManager.getInstance().setCurrentActivity(activity);
                L.i("Application",activity.getClass().getSimpleName()+" Resumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                L.i("Application",activity.getClass().getSimpleName()+" Paused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityInForegroundCount--;
                com.wanpiao.master.common.ActivityManager.getInstance().setCurrentActivity(null);
                //无activity在前台 即应用回到后台
                if (activityInForegroundCount == 0){
                    L.i("Application","app to background");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                L.i("Application",activity.getClass().getSimpleName()+" Destroyed");
            }
        };
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    /**
     * 获取进程名
     * @param cxt
     * @param pid
     * @return
     */
    public String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    protected String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
