package com.wanpiao.master.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.model.bean.GuideBean;
import com.wanpiao.master.mvp.model.bean.InformationBean;
import com.wanpiao.master.mvp.ui.activity.MainActivity;
import com.wanpiao.master.utils.SPUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class GuideAdapter extends PagerAdapter {


    Context context;
    List<GuideBean> list;

    public GuideAdapter(Context context, List<GuideBean> list) {
        this.context=context;
        this.list=list;
    }

    private Activity activity;

    public void bindActivity(Activity activity){
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("jinyangyang+","instantiateItem position is "+position);
        View view = View.inflate(context, R.layout.item_guide, null);
        ImageView guide_img = (ImageView)view.findViewById(R.id.guide_img);
        Button bt_guide2 = (Button)view.findViewById(R.id.bt_guide2);
        guide_img.setImageResource(list.get(position).getLocalImg());
        if(list.get(position).getItemType()==1){
            bt_guide2.setVisibility(View.VISIBLE);
        }else {
            bt_guide2.setVisibility(View.GONE);
        }
        bt_guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里如果被点击了，那么下次进入就没有引导页了
                SPUtils.put(App.getInstance(), "isFirstInstallation", "1");
                activity.finish();
                MainActivity.startMainActivity(context);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        container.removeView((View) object);
    }
}
