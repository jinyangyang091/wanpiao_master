package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.UpdateFragmentContract;

import javax.inject.Inject;

public class UpdateFragmentPresenter extends BasePresenter<UpdateFragmentContract.View, UpdateFragmentContract.Model>
        implements  UpdateFragmentContract.Presenter {
    @Inject
    public UpdateFragmentPresenter(UpdateFragmentContract.View rootView, UpdateFragmentContract.Model model) {
        super(rootView, model);
    }
}
