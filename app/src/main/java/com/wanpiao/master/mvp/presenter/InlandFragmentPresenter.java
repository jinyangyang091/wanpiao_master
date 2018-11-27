package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.InlandFragmentContract;

import javax.inject.Inject;

public class InlandFragmentPresenter extends BasePresenter<InlandFragmentContract.View, InlandFragmentContract.Model>
        implements  InlandFragmentContract.Presenter {
    @Inject
    public InlandFragmentPresenter(InlandFragmentContract.View rootView, InlandFragmentContract.Model model){
        super(rootView, model);
    }
}
