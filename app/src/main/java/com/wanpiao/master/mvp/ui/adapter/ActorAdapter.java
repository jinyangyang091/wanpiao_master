package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.MyInfoBean;

import java.util.List;

import javax.inject.Inject;

public class ActorAdapter extends BaseMultiItemQuickAdapter<MyInfoBean, BaseViewHolder> {
    private Context context;
    @Inject
    public ActorAdapter(List<MyInfoBean> data, Context context){
        super(data);
        addItemType(MyInfoBean.TYPE_NORMAL, R.layout.item_actor);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyInfoBean item) {
        helper.setText(R.id.actorname, item.getActor());
    }
}
