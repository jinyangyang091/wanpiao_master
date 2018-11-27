package com.wanpiao.master.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityRegisterBinding;
import com.wanpiao.master.mvp.contract.RegisterContract;
import com.wanpiao.master.mvp.presenter.RegisterPresenter;
import com.wanpiao.master.utils.SPUtils;
import com.wanpiao.master.utils.SoftKeyboardControl;
import com.wanpiao.master.utils.WordReplacement;
import com.wanpiao.master.widgets.popup_windows.SexPopup;

import java.util.concurrent.TimeUnit;

/**
 * @Description: #注册
 * #0000      @Author: tianxiao     2018/10/18      onCreate
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter, ActivityRegisterBinding>
        implements RegisterContract.View {

    /**
     * 此为登陆跳转过渡动画方法
     * @param context
     * @param imageView
     * @param llToRegister
     */
    public static void startRegisterActivity(LoginActivity context, View imageView, View llToRegister) {
        Intent intent = new Intent(context, RegisterActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context,
                Pair.create(imageView, "logo"),
                Pair.create(llToRegister, "register"));
        context.startActivity(intent, options.toBundle());
        context.overridePendingTransition(0, 0);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        bin.tvPrivacyPolicy.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        bin.tvPrivacyPolicy.getPaint().setAntiAlias(true);
        bin.tvTermsOfService.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        bin.tvTermsOfService.getPaint().setAntiAlias(true);
//        bin.tvSex.setOnClickListener(v -> {
//            SexPopup.newInstance(this)
//                    .setSelelctClick(sex -> {
//                        bin.tvSex.setText(sex);
//                    }).showPopupWindow(v);
//        });
        bin.edPwd.setTransformationMethod(new WordReplacement());
        bin.tvSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoftKeyboardControl.closeInputMethod(mContext, bin.etName);
                showPopwindow();
            }
        });

        bin.tvOut.setOnClickListener(v -> {
            this.finish();
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

//        RxView.clicks(bin.tvRegister)
//                .throttleFirst(2, TimeUnit.SECONDS)//防止抖动
//                .subscribe(v -> {
//                    //此处需要传递真实的
//                    int userSex = 2;
//                    Log.d("jinyangyang+","curSex is "+bin.tvSex.getText().toString());
//                    if(bin.tvSex.getText().toString().equals("男")){
//                        userSex = 1;
//                    }else if(bin.tvSex.getText().toString().equals("女")){
//                        userSex = 0;
//                    }else{
//                        userSex = 2;
//                    }
//                    //SPUtils.put(App.getInstance(), "token","");
//                    Log.d("jinyangyang+"," userSex ++++++++++++ "+userSex);
//                    presenter.registerUser(bin.etName.getText().toString(), userSex, bin.edEmail.getText().toString(), bin.edPwd.getText().toString());
//                });

        bin.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处需要传递真实的
                int userSex = 2;
                Log.d("jinyangyang+","curSex is "+bin.tvSex.getText().toString());
                if(bin.tvSex.getText().toString().equals("男")){
                    userSex = 1;
                }else if(bin.tvSex.getText().toString().equals("女")){
                    userSex = 0;
                }else{
                    userSex = 2;
                }
                //SPUtils.put(App.getInstance(), "token","");
                Log.d("jinyangyang+"," userSex ++++++++++++ "+userSex);
                presenter.registerUser(bin.etName.getText().toString(), userSex, bin.edEmail.getText().toString(), bin.edPwd.getText().toString());
            }
        });
        bin.tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 注册成功回调
     */
    @Override
    public void registerSuccess(String userAccount, String  userPassword) {
        //这里需要直接登录，返回
        presenter.requestLanding(mContext, userAccount, userPassword);
    }
    /**
     * 注册之后直接登录,然后登录成功返回
     */
    @Override
    public void landingSuccess() {
        finish();
        MainActivity.startMainActivity(this);
    }


    private void showPopwindow() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.popu_select, null);

        TextView tv_man = (TextView) popView.findViewById(R.id.tv_man);
        TextView tv_woman = (TextView) popView.findViewById(R.id.tv_woman);
        TextView tv_cancel = (TextView) popView.findViewById(R.id.tv_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        //popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setBackgroundDrawable(new BitmapDrawable());

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_man:
                        bin.tvSex.setText(tv_man.getText().toString());
                        break;
                    case R.id.tv_woman:
                        bin.tvSex.setText(tv_woman.getText().toString());
                        break;
                    case R.id.tv_cancel:

                        break;
                }
                popWindow.dismiss();
            }
        };
        tv_man.setOnClickListener(listener);
        tv_woman.setOnClickListener(listener);
        tv_cancel.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}
