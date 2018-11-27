package com.wanpiao.master.mvp.model;

import com.wanpiao.master.mvp.contract.MoviePageFragmentContract;

import javax.inject.Inject;

public class MoviePageFragmentModel implements MoviePageFragmentContract.Model {
    @Inject
    public MoviePageFragmentModel(){

    }
    @Override
    public void onDestroy() {

    }
}
