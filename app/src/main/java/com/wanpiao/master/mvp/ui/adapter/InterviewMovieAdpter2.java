package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.InformationBean2;

import java.util.List;

import javax.inject.Inject;

public class InterviewMovieAdpter2 extends BaseMultiItemQuickAdapter<InformationBean2, BaseViewHolder> {
    protected Context context;
    //此处先将数据初始化
    @Inject
    public InterviewMovieAdpter2(List<InformationBean2> data, Context context) {
        super(data);
        addItemType(InformationBean2.TYPE_NORMAL, R.layout.home_information2_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, InformationBean2 item) {
        helper.setText(R.id.tv_movie_name, item.getName());
        //helper.setText(R.id.tv_movie_length, item.getMovieLength());
        ImageView iv_movie = (ImageView)helper.getView(R.id.iv_movie);
        User.showIcon(iv_movie, item.getBackground_picture());
    }
}
