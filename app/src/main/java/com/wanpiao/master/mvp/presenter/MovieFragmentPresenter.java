package com.wanpiao.master.mvp.presenter;

import com.wanpiao.master.common.BasePresenter;
import com.wanpiao.master.mvp.contract.MoviePageFragmentContract;

import javax.inject.Inject;

public class MovieFragmentPresenter
        extends BasePresenter<MoviePageFragmentContract.View, MoviePageFragmentContract.Model>
        implements  MoviePageFragmentContract.Presenter{

    @Inject
    public MovieFragmentPresenter(MoviePageFragmentContract.View rootView, MoviePageFragmentContract.Model model){
        super(rootView, model);
    }
}
