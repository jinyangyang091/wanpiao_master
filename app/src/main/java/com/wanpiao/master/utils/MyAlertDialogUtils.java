package com.wanpiao.master.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wanpiao.master.mvp.ui.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

public class MyAlertDialogUtils {
    public static void showAlertDialog(Context context, Intent intent){

        Bundle bundle = intent.getExtras();
        Log.d("jinyangyang++++++++++++", "bundle - " + bundle);
        //接收后台推送过来的消息
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE); //字符串
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA); //json格式

        AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(context);
        dialogBuilder.setTitle("极光推送通知");
        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog = dialogBuilder.create();
        //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        alertDialog.show();

    }
}
