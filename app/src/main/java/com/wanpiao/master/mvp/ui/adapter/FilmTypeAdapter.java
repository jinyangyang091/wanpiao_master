package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.InformationBean;

import java.util.List;

import javax.inject.Inject;

public class FilmTypeAdapter extends BaseMultiItemQuickAdapter<InformationBean, BaseViewHolder> {
    private Context context;
    @Inject
    public FilmTypeAdapter(List<InformationBean> data, Context context) {
        super(data);
        Log.d("jinyangyang+","PrevueMovieAdapter Constructor be called");
        addItemType(InformationBean.TYPE_NORMAL, R.layout.item_film_type);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, InformationBean item) {
        helper.setText(R.id.film_type_lable, item.getActor());
    }
}
