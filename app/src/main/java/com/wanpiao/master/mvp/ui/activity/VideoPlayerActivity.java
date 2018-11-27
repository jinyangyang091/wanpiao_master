package com.wanpiao.master.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityPlayerBinding;
import com.wanpiao.master.mvp.contract.VideoPlayerContract;
import com.wanpiao.master.mvp.presenter.VideoPlayerPresenter;
import com.wanpiao.master.utils.MyJzvdStd;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoPlayerActivity extends BaseActivity<VideoPlayerPresenter, ActivityPlayerBinding> implements VideoPlayerContract.View {
    private static String playerUrl = "";
    private static String playerTitle = "";
    private MyJzvdStd myJzvdStd;
    public static void startVideoPlayerActivity(Context context, String playerUrl, String playerTitle) {
        VideoPlayerActivity.playerUrl = playerUrl;
        //VideoPlayerActivity.playerUrl = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
        VideoPlayerActivity.playerTitle = playerTitle;
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        MyJzvdStd.bindActivity(this);
        myJzvdStd = (MyJzvdStd) findViewById(R.id.videoplayer);
        MyJzvdStd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        MyJzvdStd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向
        myJzvdStd.setUp(VideoPlayerActivity.playerUrl, VideoPlayerActivity.playerTitle , JzvdStd.SCREEN_WINDOW_NORMAL);
        //jzvdStd.setUp(VideoPlayerActivity.playerUrl, VideoPlayerActivity.playerTitle , JzvdStd.SCREEN_WINDOW_NORMAL);
        //jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        //Glide.with(this).load("https://wanpiao-hk-test.oss-cn-shanghai.aliyuncs.com/wanpiao-hk/advertise/img/img_1@3x.png").into(jzvdStd.thumbImageView);
        myJzvdStd.startButton.performClick();
        //JzvdStd.startFullscreen(this, JzvdStd.class, VideoPlayerActivity.playerUrl, playerTitle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_player;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onBackPressed() {
        if (MyJzvdStd.backPress()) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyJzvdStd.goOnPlayOnPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyJzvdStd.goOnPlayOnResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("jinyangyang+","VideoPlayerActivity onStop be called");
        myJzvdStd.release();
    }
}
