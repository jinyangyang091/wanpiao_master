package com.wanpiao.master.receiver;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wanpiao.master.App;
import com.wanpiao.master.R;
import com.wanpiao.master.mvp.ui.activity.DetailsFilmActivity;
import com.wanpiao.master.mvp.ui.activity.MainActivity;
import com.wanpiao.master.utils.MyAlertDialogUtils;

import androidx.core.app.NotificationCompat;
import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d("jinyangyang++++++++","极光推送通知来了");
            //ToastUtil.show(context, "极光推送来啦！！！！！！");
            Bundle bundle = intent.getExtras();
            Log.d("jinyangyang++++++++++++", "bundle - " + bundle);
            //接收后台推送过来的消息
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE); //字符串
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA); //json格式
            Log.d("jinyangyang++++++++++++", " 字符串 "+message);
            Log.d("jinyangyang++++++++++++", "JSON数据"+extras);
            //ToastUtil.show(context, message);
            //abortBroadcast();
            //弹框
//            Log.d("jinyangyang+","curClass Name =========== "+getClass().getSimpleName());
            AlertDialog.Builder dialogBuilder =  new AlertDialog.Builder(MainActivity.mContext);
            dialogBuilder.setTitle("极光推送通知");
            dialogBuilder.setMessage(message);
            dialogBuilder.setCancelable(false);
            dialogBuilder.setPositiveButton("知道了",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog alertDialog = dialogBuilder.create();
            //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
            //alertDialog.show();
            //Intent intent2 = new Intent("com.wanpiao.jiguang.cn");
            //intent2.putExtras(bundle);
            //intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.sendBroadcast(intent2);
            //MyAlertDialogUtils.showAlertDialog(MainActivity.mContext,intent);
        }else if("cn.jpush.android.intent.NOTIFICATION_OPENED".equals(intent.getAction())){
            Log.d("jinyangyang+","hhhhhhhhhhhh");
            Intent getIntent = new Intent(context, DetailsFilmActivity.class);
            getIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b=new Bundle();
            b.putString("movieId","1");
            getIntent.putExtras(b);
            context.startActivity(getIntent);
        }else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            Log.i("jinyangyang", "接受到推送下来的通知");

        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        // 使用notification
        // 使用广播或者通知进行内容的显示
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setContentText(message).setSmallIcon(R.mipmap.icon_camera).setContentTitle(JPushInterface.EXTRA_TITLE);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(1,builder.build());
    }
}
