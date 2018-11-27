package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.GuideContract;

import javax.inject.Inject;

public class GuidePresenter extends BasePresenter<GuideContract.View, GuideContract.Model>
        implements GuideContract.Presenter {

    @Inject
    public GuidePresenter(GuideContract.View rootView, GuideContract.Model model) {
        super(rootView, model);
    }
}
