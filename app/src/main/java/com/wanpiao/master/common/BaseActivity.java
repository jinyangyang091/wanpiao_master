package com.wanpiao.master.common;

import android.app.Activity;

import androidx.lifecycle.Lifecycle;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.luck.picture.lib.tools.Constant;
import com.uber.autodispose.AutoDisposeConverter;
import com.wanpiao.master.mvp.ui.activity.SettingActivity;
import com.wanpiao.master.utils.LanguageUtil;
import com.wanpiao.master.utils.RxLifecycleUtils;
import com.wanpiao.master.widgets.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * 描述:Activity基类 @author mwy
 * <p>
 */
public abstract class BaseActivity<P extends IPresenter, B extends ViewDataBinding> extends BaseInjectActivity implements IActivity {

    protected B bin;

    @Inject
    protected P presenter;

    protected LoadingDialog loadingDialog;

    private boolean isInit = false;
    public static Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        bin = DataBindingUtil.setContentView(this, getLayoutId());
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        LanguageUtil.changeLanguage(BaseActivity.this);
        initLifecycleObserver(getLifecycle());
        initViews(savedInstanceState);
        initStatusBar();
        initToolBar();
        initWebView();
        ActivityManager.getInstance().addActivity(this);
        Log.d("CurActivity======",getClass().getSimpleName());
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
            initData();
        }
    }

    @MainThread
    protected abstract void initViews(Bundle savedInstanceState);

    @MainThread
    public abstract int getLayoutId();

    @MainThread
    public abstract void initToolBar();

    @MainThread
    protected abstract void initData();

    @MainThread
    protected void initStatusBar() {
        ImmersionBar.with(this).init();
    }

    public void initWebView() {

    }

    @CallSuper
    @MainThread
    protected void initLifecycleObserver(@NotNull Lifecycle lifecycle) {
        if (presenter != null)
            lifecycle.addObserver(presenter);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    /**
     * 跳转WEB ACtivity的方法。
     * 此方法为了提前预加载，使用sonic 让网页加载更快
     *
     * @param
     * @param
     */
    /*public void startWebActivity(@Nullable String url, @Nullable Intent intent) {
        //-----------------------
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);
        boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfigBuilder.build());
        intent.putExtra(BaseWebActivity.URL,url);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        startActivity(intent);
    }*/
    @MainThread
    @Override
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @MainThread
    @Override
    public void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @MainThread
    protected void startActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoading();
    }

    /**
     * 是否使用eventbus
     * 默认不开启
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        //必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        ActivityManager.getInstance().removeActivity(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static List<Activity> activities = new ArrayList<Activity>();
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }



}
