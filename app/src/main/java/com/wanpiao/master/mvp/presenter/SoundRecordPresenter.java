package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.SoundRecordContract;

import javax.inject.Inject;

public class SoundRecordPresenter extends BasePresenter<SoundRecordContract.View, SoundRecordContract.Model> implements SoundRecordContract.Presenter {
    @Inject
    public SoundRecordPresenter(SoundRecordContract.View rootView, SoundRecordContract.Model model) {
        super(rootView, model);
    }
}
