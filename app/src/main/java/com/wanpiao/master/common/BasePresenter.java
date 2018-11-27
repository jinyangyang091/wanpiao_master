package com.wanpiao.master.common;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.ObservableSubscribeProxy;
import com.wanpiao.master.retrofit.RetryWhenNetworkException;
import com.wanpiao.master.retrofit.RxSchedulers;
import com.wanpiao.master.utils.RxLifecycleUtils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * 描述: BasePresenter
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-04-27 18:20
 */
public class BasePresenter<V extends IView, M extends IModel> implements IPresenter {
    private static final String TAG = "common.BasePresenter";

    protected V mRootView;
    protected M mModel;

    public V getmRootView() {
        return mRootView;
    }

    public M getmModel() {
        return mModel;
    }

    private LifecycleOwner lifecycleOwner;

    public BasePresenter(V rootView, M model) {
        this.mRootView = rootView;
        this.mModel = model;
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (null == lifecycleOwner){
            throw new NullPointerException("lifecycleOwner == null");
        }
        return RxLifecycleUtils.bindLifecycle(lifecycleOwner);
    }


    public <T> ObservableSubscribeProxy<BaseEntity<T>> netWorks(@NonNull Observable<BaseEntity<T>> observable) {
        return observable.throttleFirst(1, TimeUnit.SECONDS)
                .retryWhen(new RetryWhenNetworkException(3, 1000, 1000))
                .compose(RxSchedulers.io_main()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle());
    }

    @Override
    @CallSuper
    @MainThread
    public void onCreate(@NotNull LifecycleOwner owner) {
        this.lifecycleOwner = owner;
    }

    @Override
    @CallSuper
    @MainThread
    public void onDestroy(@NotNull LifecycleOwner owner) {
        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
        this.mRootView = null;
    }
}
