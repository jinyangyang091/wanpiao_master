package com.wanpiao.master.common;

import android.content.Context;

import androidx.fragment.app.Fragment;

import dagger.android.support.AndroidSupportInjection;

/**
 * BaseInjectFragment
 */

    public abstract class BaseInjectFragment extends Fragment {

    protected void inject() {
        AndroidSupportInjection.inject(this);
    }

    protected boolean injectRouter() {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        inject();
        super.onAttach(context);
    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

}
