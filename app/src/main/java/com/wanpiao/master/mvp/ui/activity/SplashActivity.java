package com.wanpiao.master.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivitySplashBinding;
import com.wanpiao.master.glide.GlideApp;
import com.wanpiao.master.mvp.contract.SplashContract;
import com.wanpiao.master.mvp.presenter.SplashPresenter;
import com.wanpiao.master.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends BaseActivity<SplashPresenter, ActivitySplashBinding> implements SplashContract.View {
    private  CountDownTimer countDownTimer;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        String curActivityName = getClass().getSimpleName();
        Log.d("jinyangyang+","   curActivityName   ==========="+curActivityName);
        bin.btGuide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {
        presenter.requestSplash();
    }

    @Override
    public void showAd(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String messageId = jsonObject.optString("messageId");
            JSONObject data = jsonObject.optJSONObject("data");
            if(data != null){
                bin.btGuide2.setVisibility(View.VISIBLE);
                int continueTime = data.optInt("continueTime");
                String advertiseUrl = data.optString("advertiseUrl");
                String jumpType = data.optString("jumpType");
                String jumpId = data.optString("jumpId");
                bin.splashBgImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(jumpType.equals("0")){
                            if(countDownTimer!=null){
                                countDownTimer.cancel();
                            }
                            Intent intent = new Intent(mContext, DetailsFilmActivity.class);
                            Bundle b=new Bundle();
                            b.putString("movieId",jumpId);
                            intent.putExtras(b);
                            //跳转首页
                            finish();
                            startActivity(intent);
                        }
                    }
                });
                GlideApp.with(this).load(
                        advertiseUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(bin.splashBgImg);
                startSplash(continueTime);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void startSplash(int data){

         countDownTimer = new CountDownTimer((data+1) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String miao = (Integer.parseInt((millisUntilFinished / 1000 % 60) + "")) < 10 ? "" + (Integer.parseInt((millisUntilFinished / 1000 % 60) + "")) : (Integer.parseInt((millisUntilFinished / 1000 % 60) + "")) + "";
                //一秒调用一次
                Log.d("jinyangyang+","miao is "+miao);
                if (!miao.equals("0")) {
                    bin.readSeconds.setText(" "+miao + " ");
                }
            }
            //读秒计数器完成之后需要直接跳转到首页
            @Override
            public void onFinish() {
                goActivity();
            }
        };
        countDownTimer.start();
    }

    public void goActivity(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        //此处需要判定是否是第一次安装,需要记住一个本地变量
        String isFirstInstallation = SPUtils.get(App.getInstance(), "isFirstInstallation","");
        if (isFirstInstallation.equals("")){
            //跳转开机引导页
            finish();
            GuideActivity.startGuideActivity(mContext);
        }else{
            //跳转首页
            finish();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}
