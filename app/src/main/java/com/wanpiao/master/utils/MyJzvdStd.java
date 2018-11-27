package com.wanpiao.master.utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.wanpiao.master.mvp.ui.activity.VideoPlayerActivity;

import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;

public class MyJzvdStd extends JzvdStd {
    private Context context;
    private static Activity activity;
    public MyJzvdStd(Context context) {
        super(context);
        this.context = context;
    }

    public MyJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setUp(JZDataSource jzDataSource, int screen) {
        super.setUp(jzDataSource, screen);
        titleTextView.setText(jzDataSource.title);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            fullscreenButton.setImageResource(cn.jzvd.R.drawable.jz_shrink);
            backButton.setVisibility(View.VISIBLE);
            tinyBackImageView.setVisibility(View.INVISIBLE);
            batteryTimeLayout.setVisibility(View.VISIBLE);
            if (jzDataSource.urlsMap.size() == 1) {
                clarity.setVisibility(GONE);
            } else {
                clarity.setText(jzDataSource.getCurrentKey().toString());
                clarity.setVisibility(View.VISIBLE);
            }
            changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_fullscreen));
        } else if (currentScreen == SCREEN_WINDOW_NORMAL
                || currentScreen == SCREEN_WINDOW_LIST) {
            fullscreenButton.setImageResource(cn.jzvd.R.drawable.jz_enlarge);
            backButton.setVisibility(View.VISIBLE);
            tinyBackImageView.setVisibility(View.INVISIBLE);
            changeStartButtonSize((int) getResources().getDimension(cn.jzvd.R.dimen.jz_start_button_w_h_normal));
            batteryTimeLayout.setVisibility(View.GONE);
            clarity.setVisibility(View.GONE);
        } else if (currentScreen == SCREEN_WINDOW_TINY) {
            tinyBackImageView.setVisibility(View.VISIBLE);
            setAllControlsVisiblity(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                    View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
            batteryTimeLayout.setVisibility(View.GONE);
            clarity.setVisibility(View.GONE);
        }
        setSystemTimeAndBattery();
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jinyangyang+","player backButton be pressed");
                backPress();

                activity.finish();
            }
        });
        titleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jinyangyang+","player titleTextView be pressed");
                backPress();
                activity.finish();
            }
        });
    }
    public static void bindActivity(Activity activity) {
        MyJzvdStd.activity = activity;
    }
}
