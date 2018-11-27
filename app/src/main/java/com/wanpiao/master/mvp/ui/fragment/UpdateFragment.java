package com.wanpiao.master.mvp.ui.fragment;

import android.view.View;

import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.FragmentUpdateBinding;
import com.wanpiao.master.mvp.contract.UpdateFragmentContract;
import com.wanpiao.master.mvp.presenter.UpdateFragmentPresenter;

public class UpdateFragment extends BaseFragment<UpdateFragmentPresenter, FragmentUpdateBinding> implements UpdateFragmentContract.View {
    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutRes() {
        return 0;
    }
}
