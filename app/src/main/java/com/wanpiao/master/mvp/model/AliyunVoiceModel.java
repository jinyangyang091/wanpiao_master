package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.AliyunVoiceContract;

import javax.inject.Inject;

public class AliyunVoiceModel implements AliyunVoiceContract.Model {
    @Inject
    public AliyunVoiceModel(){

    }

    @Override
    public void onDestroy() {

    }
}
