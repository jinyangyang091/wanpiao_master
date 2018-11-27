package com.wanpiao.master.utils;

import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * 描述: Rx 生命周期工具
 * https://blog.csdn.net/mq2553299/article/details/79418068
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-04-27 18:10
 */
public class RxLifecycleUtils {
    private RxLifecycleUtils() {
        throw new IllegalStateException("Can't instance the RxLifecycleUtils");
    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }
}
