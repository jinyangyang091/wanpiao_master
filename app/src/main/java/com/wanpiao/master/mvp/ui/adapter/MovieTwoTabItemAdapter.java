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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MovieTwoTabItemAdapter extends BaseMultiItemQuickAdapter<MovieItemBean, BaseViewHolder> {
    private Context context;

    //此处先将数据初始化
    public MovieTwoTabItemAdapter(List<MovieItemBean> data, Context context) {
        super(data);
        addItemType(MovieItemBean.TYPE_NORMAL, R.layout.movie_tab_two_item);
//        addItemType(InterViewBean2.TYPE_FOOT, R.layout.foot_future_movie);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieItemBean item) {
        //iv_movie,movie_item_name,ratingbar,rating_text,comment_text,want_text
        //加载网络图片
        TextView want_text = helper.getView(R.id.want_text);
        RoundCornerImageView iv_movie = helper.getView(R.id.iv_movie);
        User.showIcon(iv_movie, item.getBackground_picture());
        helper.setText(R.id.movie_item_name, item.getName());
        helper.setText(R.id.rating_text, item.getRate()*2+"");
        //helper.setRating(R.id.ratingbar, item.getRate());
        RatingBar ratingBar = (RatingBar)helper.getView(R.id.ratingbar);
        //ratingBar.setSelectedNumber(3);
        helper.setText(R.id.comment_text, item.getCommentNums()+"");
        helper.setText(R.id.want_text, item.getLaudNums()+"");
        helper.setText(R.id.public_time, item.getShowtime());
        if(item.getUserGoodNum().equals("1")){
            helper.setImageResource(R.id.land_show, R.mipmap.icon_laud_red);
        }else {
            helper.setImageResource(R.id.land_show, R.mipmap.tab_icon_laud);
        }

        int intRate = Integer.parseInt(new java.text.DecimalFormat("0").format(item.getRate()))/2;
        if(intRate>5){
            intRate = 5;
        }
        Log.d("intRate = ", ""+intRate);
        ratingBar.setSelectedNumber(intRate);

    }
}
