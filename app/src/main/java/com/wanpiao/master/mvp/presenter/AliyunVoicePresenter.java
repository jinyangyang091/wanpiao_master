package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.AliyunVoiceContract;

import javax.inject.Inject;

public class AliyunVoicePresenter extends BasePresenter<AliyunVoiceContract.View, AliyunVoiceContract.Model> implements AliyunVoiceContract.Presenter {
    @Inject
    public AliyunVoicePresenter(AliyunVoiceContract.View rootView, AliyunVoiceContract.Model model) {
        super(rootView, model);
    }
}
