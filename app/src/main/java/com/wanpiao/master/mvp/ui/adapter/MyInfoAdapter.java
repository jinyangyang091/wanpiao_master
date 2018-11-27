package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.MyInfoBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyInfoAdapter extends BaseMultiItemQuickAdapter<MyInfoBean, BaseViewHolder> {
    private Context context;

    @Inject
    public MyInfoAdapter(List<MyInfoBean> data, Context context){
        super(data);
        addItemType(MyInfoBean.TYPE_NORMAL, R.layout.item_myinfo);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyInfoBean item) {
        helper.setText(R.id.info_name, item.getName());
        ImageView info_img = (ImageView)helper.getView(R.id.info_img);
        User.showIcon(info_img, item.getBackground_picture());
        helper.setText(R.id.info_showtime, item.getShowtime()+" 上映");
        if(item.getScore()>5){
            helper.setText(R.id.info_score_text,"5.0");
        }else {
            helper.setText(R.id.info_score_text, ""+item.getScore());
        }
        if(item.getStatus()==0){
            helper.setVisible(R.id.info_corner, true);
        }else {
            helper.setVisible(R.id.info_corner, false);
        }
        //这里也要写一个横向布局的Recyclerview
        RecyclerView  info_type = (RecyclerView)helper.getView(R.id.info_type);
        LinearLayoutManager interviewLinearLayoutManager = new LinearLayoutManager(context);
        interviewLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        info_type.setLayoutManager(interviewLinearLayoutManager);
        info_type.addItemDecoration(new MyInfoAdapter.SpacesItemDecoration(1));
        //设置该属性让recyclerview滑动没有黏连的感觉
        info_type.setNestedScrollingEnabled(false);
        Log.d("jinyangyang+","item.getMovieType ======== "+item.getMovieType());
        String[] movieTypes = item.getMovieType().split(",");
        List<MyInfoBean> myInfoBeans = new ArrayList<>();
        for(int i=0;i<movieTypes.length;i++){
            MyInfoBean myInfoBean = new MyInfoBean();
            myInfoBean.setActor(movieTypes[i]);
            myInfoBeans.add(myInfoBean);
        }
        ActorAdapter actorAdapter = new ActorAdapter(myInfoBeans, context);
        actorAdapter.setNewData(myInfoBeans);
        info_type.setAdapter(actorAdapter);
    }


    class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.right = space;
        }
    }
}
