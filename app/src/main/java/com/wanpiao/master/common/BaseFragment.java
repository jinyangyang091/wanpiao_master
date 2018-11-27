package com.wanpiao.master.common;


import androidx.lifecycle.Lifecycle;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uber.autodispose.AutoDisposeConverter;
import com.wanpiao.master.utils.RxLifecycleUtils;
import com.wanpiao.master.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * 描述:Fragment基类
 * 工程:
 */
public abstract class BaseFragment<P extends IPresenter, B extends ViewDataBinding>
        extends BaseInjectFragment implements IFragment {

    public B bin;

    protected View rootView;

    @Inject
    protected P presenter;

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    public  Context mContext;
    public static String switchFlag = "1";
    public int switchGalleryOrRecyclerFlag = 0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //过场动画
        // “内存重启”时调用  解决重叠问题  #0001
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }

        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext()).inflate(getLayoutRes(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bin = DataBindingUtil.bind(view);
        initLifecycleObserver(getLifecycle());
        initView(view);
        initData();

    }

    @Override
    public void onDestroy() {
        this.rootView = null;
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    @CallSuper
    @MainThread
    protected void initLifecycleObserver(@NotNull Lifecycle lifecycle) {
        if (presenter == null) {
            ToastUtil.showLong(getContext(), "presenter 为空");
        } else {
            lifecycle.addObserver(presenter);
        }
    }

    protected abstract void initView(View view);

    protected abstract void initData();

    @LayoutRes
    protected abstract int getLayoutRes();

    public boolean useEventBus() {
        return false;
    }

    @MainThread
    @Override
    public void showLoading() {

    }

    @MainThread
    @Override
    public void hideLoading() {

    }
}
