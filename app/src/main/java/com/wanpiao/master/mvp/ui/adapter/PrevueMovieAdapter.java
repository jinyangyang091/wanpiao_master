package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.InformationBean;

import java.util.List;

import javax.inject.Inject;

public class PrevueMovieAdapter extends BaseMultiItemQuickAdapter<InformationBean, BaseViewHolder> {
    private Context context;
    @Inject
    public PrevueMovieAdapter( List<InformationBean> data, Context context) {
        super(data);
        Log.d("jinyangyang+","PrevueMovieAdapter Constructor be called");
        addItemType(InformationBean.TYPE_NORMAL, R.layout.home_information_item);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, InformationBean item) {
        //Log.d("jinyangyang+","name is "+item.getName());
        helper.setText(R.id.tv_movie_name, item.getName());
        ImageView iv_movie = (ImageView)helper.getView(R.id.iv_movie);
        User.showIcon(iv_movie,item.getBackground_picture());
        //User.showIcon(iv_movie, "https://wanpiao-hk-test.oss-cn-shanghai.aliyuncs.com/wanpiao-hk/information/img-movie/test.jpg");
    }
}
