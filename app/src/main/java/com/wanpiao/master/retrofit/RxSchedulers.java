package com.wanpiao.master.retrofit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述: Rx线程
 * --------------------------------------------
 * 工程:
 * #0000     mwy     创建日期: 2017-06-06 14:43
 * @author Administrator
 */

public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
