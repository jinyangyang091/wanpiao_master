package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.UpdateFragmentContract;

import javax.inject.Inject;

public class UpdateFragmentModel implements UpdateFragmentContract.Model {

    @Inject
    public UpdateFragmentModel(){

    }

    @Override
    public void onDestroy() {

    }
}
