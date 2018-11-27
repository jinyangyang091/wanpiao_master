package com.wanpiao.master.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wanpiao.master.R;
import com.wanpiao.master.common.BaseFragment;
import com.wanpiao.master.databinding.FragmentInlandPageBinding;
import com.wanpiao.master.mvp.contract.InlandFragmentContract;
import com.wanpiao.master.mvp.presenter.InlandFragmentPresenter;
import com.wanpiao.master.mvp.ui.activity.CurrencyWebActivity;
import com.wanpiao.master.mvp.ui.activity.MainActivity;
import com.wanpiao.master.mvp.ui.java_script.CurrencyJS;
import com.wanpiao.master.utils.TimeSwitchUtils;

import org.apache.http.util.EncodingUtils;

import androidx.annotation.Nullable;

public class InlandFragment extends BaseFragment<InlandFragmentPresenter, FragmentInlandPageBinding> implements InlandFragmentContract.View {

    private static Context context;
    private WebView mWebView;
    private String url = "https://hk.enjoytickets.com/hongkongmovie-mobile/setting/rest/ranking";
    //public static String url = "file:///android_asset/html/page/inlandInformation.html";
    public static InlandFragment newInstance(Context context) {
        InlandFragment fragment = new InlandFragment();
        InlandFragment.context = context;
        return fragment;
    }
    @Override
    protected void initData() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView(View view) {
        mWebView = bin.webView;
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mWebView.setVisibility(View.VISIBLE);
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        //设置webview缓存与否
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE );
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        //支持缩放
        //mWebView.getSettings().setBuiltInZoomControls(true);
        //允许支持JS脚本（网页动态效果）
        //mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(CurrencyJS
//                .newInstance()
//                .setJsClick(MainActivity.this::finish),"App");
        loadWeb(url);
    }

    /**
     * 加载页面
     * @param url
     */
    public void loadWeb(String url) {
        long curtimeMillis = System.currentTimeMillis();
        String getUrl = url+"/"+TimeSwitchUtils.stampToDate(curtimeMillis);
        Log.d("jinyangyang+","inlandFragment getUrl =========== "+getUrl);
        mWebView.loadUrl(getUrl);
        //post访问需要提交的参数
//        String postDate = "";
//        mWebView.postUrl(getUrl, EncodingUtils.getBytes(postDate, "BASE64"));
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_inland_page;
    }

    class  MyWebViewClient extends WebViewClient{
        @Nullable
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }

}
