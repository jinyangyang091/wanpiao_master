package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.liuzhuang.rcimageview.RoundCornerImageView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.MovieItemBean;
import com.wanpiao.master.widgets.views.RatingBar;

import java.util.List;

public class MovieOneTabItemAdapter extends BaseMultiItemQuickAdapter<MovieItemBean, BaseViewHolder> {
    private Context context;
    //此处先将数据初始化
    public MovieOneTabItemAdapter(List<MovieItemBean> data, Context context) {
        super(data);
        addItemType(MovieItemBean.TYPE_NORMAL, R.layout.movie_tab_one_item);
//        addItemType(InterViewBean2.TYPE_FOOT, R.layout.foot_future_movie);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieItemBean item) {
        //iv_movie,movie_item_name,ratingbar,rating_text,comment_text,want_text
        //加载网络图片
        RoundCornerImageView iv_movie = helper.getView(R.id.iv_movie);
        User.showIcon(iv_movie, item.getBackground_picture());
        helper.setText(R.id.movie_item_name, item.getName());
//        if(item.getRate()>5){
//            helper.setText(R.id.rating_text,"5");
//        }else {
            helper.setText(R.id.rating_text, item.getRate()+"");
//        }
        int intRate = Integer.parseInt(new java.text.DecimalFormat("0").format(item.getRate()))/2;
        if(intRate>5){
            intRate = 5;
        }
        Log.d("intRate = ", ""+intRate);
        RatingBar ratingBar = (RatingBar)helper.getView(R.id.ratingbar);
        ratingBar.setSelectedNumber(intRate);
        helper.setText(R.id.comment_text, item.getCommentNums()+"");
        helper.setText(R.id.want_text, item.getLaudNums()+"");
        if(item.getUserGoodNum().equals("1")){
            helper.setImageResource(R.id.land_show, R.mipmap.icon_laud_red);
        }else {
            helper.setImageResource(R.id.land_show, R.mipmap.tab_icon_laud);
        }

    }
}
