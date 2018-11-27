package com.wanpiao.master.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.databinding.ActivityCurrencyWebBinding;
import com.wanpiao.master.mvp.contract.CurrencyWebContract;
import com.wanpiao.master.mvp.presenter.CurrencyWebPresenter;
import com.wanpiao.master.mvp.ui.java_script.CurrencyJS;

/**
 * @Description: # 通用web 页面
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public class CurrencyWebActivity extends BaseActivity<CurrencyWebPresenter, ActivityCurrencyWebBinding>
        implements CurrencyWebContract.View {
    private WebView mWebView;
    private String url;
    private static String URL = "url";
    //关于我们
    //public static String ABOUT_US="file:///android_asset/html/page/aboutUsH5.html";
    public static String ABOUT_US ="https://hk.enjoytickets.com/hongkongmovie-mobile/setting/rest/about";
    //隐私政策
    //public static String PRIVACY_POLICY="file:///android_asset/html/page/privacy.html";
    public static String PRIVACY_POLICY="https://hk.enjoytickets.com/hongkongmovie-mobile/setting/rest/privacy";
    //服务条款
    //public static String TERMS_OF_SERVICE="file:///android_asset/html/page/service.html";
    public static String TERMS_OF_SERVICE="https://hk.enjoytickets.com/hongkongmovie-mobile/setting/rest/server";
    public static String curTitle = "";

    //内地资讯
    public static String Land_Info = "https://hk.enjoytickets.com/hongkongmovie-mobile/setting/rest/ranking";
//    public static String Land_Info = "file:///android_asset/html/page/inlandInformation.html";
    public static void startCurrencyWeb(Context context, String url, String titleText) {
        Intent intent = new Intent(context, CurrencyWebActivity.class);
        intent.putExtra(URL, url);
        curTitle = titleText;
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        url = intent.getStringExtra(URL);
        bin.webviewTitle.setText(curTitle);
        mWebView = bin.webView;
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mWebView.setVisibility(View.VISIBLE);
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(CurrencyJS
                .newInstance()
                .setJsClick(CurrencyWebActivity.this::finish),"App");
        loadWeb(url);

        bin.webBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 加载页面
     * @param url
     */
    public void loadWeb(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_currency_web;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    protected void initData() {

    }
}
