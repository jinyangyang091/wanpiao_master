package com.wanpiao.master.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.lany.uniqueid.DeviceUtils;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityLoginBinding;
import com.wanpiao.master.mvp.contract.LoginContract;
import com.wanpiao.master.mvp.presenter.LoginPresenter;
import com.wanpiao.master.utils.SoftKeyboardControl;
import com.wanpiao.master.utils.WordReplacement;

import java.util.concurrent.TimeUnit;

import static android.view.KeyEvent.KEYCODE_BACK;


/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View {

    private static String jumpLogin="";
    public static void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void startLoginActivity(Context context, String s) {
        LoginActivity.jumpLogin = s;
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.tvPrivacyPolicy.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        bin.tvPrivacyPolicy.getPaint().setAntiAlias(true);
        bin.tvTermsOfService.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        bin.tvTermsOfService.getPaint().setAntiAlias(true);
        bin.etPwd.setTransformationMethod(new WordReplacement());
//        RxView.clicks(bin.llLogin)
//                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
//                .subscribe(v -> {
//                    String mail = bin.etMail.getText().toString();
//                    String pwd = bin.etPwd.getText().toString();
//                    //String deviceId = DeviceUtils.getUniqueDeviceId(mContext);
//                    //Log.d("jinyangyang+","deviceId is "+deviceId);
//                    presenter.requestLanding(mContext, mail, pwd);
//                });
        bin.llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                    String mail = bin.etMail.getText().toString();
                    String pwd = bin.etPwd.getText().toString();
                    //String deviceId = DeviceUtils.getUniqueDeviceId(mContext);
                    //Log.d("jinyangyang+","deviceId is "+deviceId);
                    presenter.requestLanding(mContext, mail, pwd);
            }
        });

        bin.llToRegister.setOnClickListener(v -> {
            RegisterActivity.startRegisterActivity(this,bin.imageView,bin.llToRegister);
        });

        RxView.clicks(bin.tvTermsOfService)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    CurrencyWebActivity.startCurrencyWeb(this, CurrencyWebActivity.TERMS_OF_SERVICE, bin.tvTermsOfService.getText().toString());
                });

        RxView.clicks(bin.tvPrivacyPolicy)
                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
                .subscribe(v -> {
                    CurrencyWebActivity.startCurrencyWeb(this, CurrencyWebActivity.PRIVACY_POLICY, bin.tvPrivacyPolicy.getText().toString());
                });

        bin.tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jinyangyang+","   jumpLogin   ============ "+jumpLogin);
                SoftKeyboardControl.closeInputMethod(mContext, bin.etMail);
                if(jumpLogin.equals("myfragment")){
                    jumpLogin = "";
                    Intent intent2 = new Intent(MainActivity.ACTION_LOGOUT_BACK);
                    intent2.putExtra("position", "12");
                    mContext.sendBroadcast(intent2);
                }
                if (jumpLogin.equals("16")){

                }else {
                    finish();
                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 登陆成功
     */
    @Override
    public void landingSuccess() {
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            if(jumpLogin.equals("myfragment")){
                jumpLogin = "";
                Intent intent2 = new Intent(MainActivity.ACTION_LOGOUT_BACK);
                intent2.putExtra("position", "12");
                mContext.sendBroadcast(intent2);
            }
            if (jumpLogin.equals("16")){

            }else {
                finish();
            }
            return true;
        }
        return false;
    }
}
