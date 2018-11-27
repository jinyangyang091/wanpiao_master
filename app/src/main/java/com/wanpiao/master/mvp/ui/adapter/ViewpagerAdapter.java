package com.wanpiao.master.mvp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.mvp.model.bean.InformationBean;
import com.wanpiao.master.mvp.ui.activity.CurrencyWebActivity;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    List<InformationBean> list;
    public ViewpagerAdapter(Context context, List<InformationBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("jinyangyang+","instantiateItem position is "+position);
        View view = View.inflate(context, R.layout.home_banner_item, null);
        ImageView iv_movie = (ImageView)view.findViewById(R.id.iv_movie);
        User.showIcon(iv_movie, list.get(position).getBackground_picture());
        //暂时使用这张网络图片
        //User.showIcon(iv_movie, "https://wanpiao-hk-test.oss-cn-shanghai.aliyuncs.com/wanpiao-hk/information/img-movie/test.jpg");
        iv_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jinyangyang+","instantiateItem be called ========= img is "+list.get(position).getBackground_picture());
                String infoDetailUrl = list.get(position).getInfoDetailUrl();
                CurrencyWebActivity.startCurrencyWeb(context, infoDetailUrl,"");
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
