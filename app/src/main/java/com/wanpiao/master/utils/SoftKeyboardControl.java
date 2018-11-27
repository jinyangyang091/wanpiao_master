package com.wanpiao.master.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftKeyboardControl {
    //隐藏键盘
    public static void closeInputMethod(Context context, EditText tv_works_name) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if(isOpen) {
            imm.hideSoftInputFromWindow(tv_works_name.getWindowToken(), 0); //强制隐藏键盘
            isOpen=false;
        }
    }
    //开启键盘
    public static void openInputMethod(Context context,EditText tv_works_name) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tv_works_name,InputMethodManager.SHOW_FORCED);
    }
}
