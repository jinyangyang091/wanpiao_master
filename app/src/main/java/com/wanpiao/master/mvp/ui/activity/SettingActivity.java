package com.wanpiao.master.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.luck.picture.lib.tools.Constant;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivitySettingBinding;
import com.wanpiao.master.mvp.contract.SettingContract;
import com.wanpiao.master.mvp.presenter.SettingPresenter;
import com.wanpiao.master.utils.LanguageUtil;
import com.wanpiao.master.utils.MessageEvent;
import com.wanpiao.master.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.internal.Util;

/**
 * @Description: #设置
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public class SettingActivity extends BaseActivity<SettingPresenter, ActivitySettingBinding> implements SettingContract.View {

    public static void startSettingActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RxView.clicks(bin.tvAboutUs)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    CurrencyWebActivity.startCurrencyWeb(this, CurrencyWebActivity.ABOUT_US, bin.tvAboutUs.getText().toString());
                    //CurrencyWebActivity.startCurrencyWeb(this, CurrencyWebActivity.Land_Info, "内地资讯");
                });

        RxView.clicks(bin.tvService)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    CurrencyWebActivity.startCurrencyWeb(this, CurrencyWebActivity.TERMS_OF_SERVICE, "\"玩票\"用户服务协议");
                });

        RxView.clicks(bin.tvPrivacy)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    CurrencyWebActivity.startCurrencyWeb(this, CurrencyWebActivity.PRIVACY_POLICY, bin.tvPrivacy.getText().toString());
                });

        RxView.clicks(bin.tvMyInformation)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    MyInformationActivity.startMyInformationActivity(this);
                });
        RxView.clicks(bin.textView3)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    //此处退出登录
                    presenter.requestLogOut();
                });
        bin.languageChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jinyangyang+","languageChinese");
                LanguageUtil.changeLanguage(SettingActivity.this);
                SPUtils.put(App.getInstance(), "isEnglish", 0);
                finish();
//                restartApp();
                MainActivity.startMainActivity(mContext);
//                onCreate(null);
                //EventBus.getDefault().post(new MessageEvent("changeLanguage"));

            }
        });
        bin.languageEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jinyangyang+","languageEnglish");
                LanguageUtil.changeLanguage(SettingActivity.this);
                SPUtils.put(App.getInstance(), "isEnglish", 1);
                finish();
//                restartApp();
                MainActivity.startMainActivity(mContext);
//                onCreate(null);
                //EventBus.getDefault().post(new MessageEvent("changeLanguage"));

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {
        //presenter.privacy();
    }

    @Override
    public void logOutSucess() {
//        Intent intent = new Intent(mContext, LoginActivity.class);
//        Bundle b=new Bundle();
//        b.putString("jumpLogin","myfragment");
//        intent.putExtras(b);
//        startActivity(intent);
        finish();
        LoginActivity.startLoginActivity(mContext, "myfragment");
    }

    @Override
    public void showPrivacy(String reponse) {
        Log.d("jinyangyang+","showPrivacy ====== "+reponse);
    }

    @Override
    public void logOutFail() {
        finish();
        LoginActivity.startLoginActivity(mContext);
        Intent intent2 = new Intent(MainActivity.ACTION_LOGOUT_FAIL);
        intent2.putExtra("position", "12");
        mContext.sendBroadcast(intent2);

    }

    public void restartApp(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getApplication().getPackageName());
                LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(LaunchIntent);
            }
        }, 500);

    }
}
