package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.MyCommentBean;
import com.wanpiao.master.mvp.model.bean.MyInfoBean;

import java.util.List;

import javax.inject.Inject;

public class VersionDescAdapter extends BaseMultiItemQuickAdapter<MyCommentBean, BaseViewHolder> {
    private Context context;
    @Inject
    public VersionDescAdapter(List<MyCommentBean> data, Context context){
        super(data);
        addItemType(MyInfoBean.TYPE_NORMAL, R.layout.version_update_details);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommentBean item) {
        helper.setText(R.id.version_item, item.getActor());
    }
}
