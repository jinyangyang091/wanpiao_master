package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.SoundRecordContract;

import javax.inject.Inject;

public class SoundRecordModel implements SoundRecordContract.Model {
    @Inject
    public SoundRecordModel(){

    }

    @Override
    public void onDestroy() {

    }
}
