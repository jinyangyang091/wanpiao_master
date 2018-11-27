package com.wanpiao.master.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.wanpiao.master.App;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

public class LanguageUtil {
    //设置语言，但是必须重启APP
    public static void changeLanguage(Context context) {
        String language = null;
        String country = null;
        int isEnglish = SPUtils.get(App.getInstance(), "isEnglish", 0);
        switch (isEnglish) {
            case 1:
                language = "en";
                country = "US";
                break;
            default:
                language = "zh";
                country = "CN";
                break;
        }
        Locale locale = new Locale(language, country);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(configuration, context .getResources().getDisplayMetrics());
        
    }



//    public static void set(boolean isEnglish) {
//        Configuration configuration = MainActivity.instance.getResources().getConfiguration();
//        DisplayMetrics displayMetrics = MainActivity.instance.getResources().getDisplayMetrics();
//        if (isEnglish) {
//            //设置英文
//            configuration.locale = Locale.ENGLISH;
//        } else {
//            //设置中文
//            configuration.locale = Locale.SIMPLIFIED_CHINESE;
//        }
//        //更新配置
//        MainActivity.instance.getResources().updateConfiguration(configuration, displayMetrics);
//    }


    public static void changeAppLanguage(Context context) {
        int type = SPUtils.get(App.getInstance(), "isEnglish", 0);
        Resources resources = context.getResources();  // 获得res资源对象
        Configuration config = resources.getConfiguration();  // 获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();  // 获得屏幕参数：主要是分辨率，像素等。
        if(type== 1){
            config.locale = Locale.CHINESE;  // 设置APP语言设置为英文
        }else if(type== 0){
            config.locale = Locale.ENGLISH;  // 设置APP语言设置为英文
        }
        resources.updateConfiguration(config, dm);
    }
}
