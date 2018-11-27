package com.wanpiao.master.mvp.ui.java_script;


import android.webkit.JavascriptInterface;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/22      onCreate
 */
public class CurrencyJS extends Object {

    public static CurrencyJS newInstance() {
        CurrencyJS js=new CurrencyJS();
        return js;
    }

    private onJSClick jsClick;

    /**
     * 注册删除事件
     */
    @JavascriptInterface
    public void out(){
        if (jsClick!=null){
            jsClick.out();
        }
    }


    public CurrencyJS setJsClick(onJSClick jsClick) {
        this.jsClick = jsClick;
        return this;
    }

    public interface onJSClick{
        void out();
    }
}
