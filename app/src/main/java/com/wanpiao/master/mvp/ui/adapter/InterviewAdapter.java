package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.InformationBean0;

import java.util.List;

import javax.inject.Inject;

public class InterviewAdapter extends BaseMultiItemQuickAdapter<InformationBean0, BaseViewHolder> {
    private Context context;
    @Inject
    //此处先将数据初始化
    public InterviewAdapter(List<InformationBean0> data, Context context) {
        super(data);
        addItemType(InformationBean0.TYPE_NORMAL, R.layout.home_information2_item);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, InformationBean0 item) {

    }
}
