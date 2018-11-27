package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.MyCommentBean;
import com.wanpiao.master.mvp.model.bean.MyInfoBean;

import java.util.List;

import javax.inject.Inject;

public class ActorAdapter2 extends BaseMultiItemQuickAdapter<MyCommentBean, BaseViewHolder> {
    private Context context;
    @Inject
    public ActorAdapter2(List<MyCommentBean> data, Context context){
        super(data);
        addItemType(MyCommentBean.TYPE_NORMAL, R.layout.item_actor);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyCommentBean item) {
        helper.setText(R.id.actorname, item.getActor());
    }
}
