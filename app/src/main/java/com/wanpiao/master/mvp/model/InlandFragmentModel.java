package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.InlandFragmentContract;

import javax.inject.Inject;

public class InlandFragmentModel implements InlandFragmentContract.Model {
    @Inject
    public InlandFragmentModel(){

    }

    @Override
    public void onDestroy() {

    }
}
