package com.wanpiao.master.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wanpiao.master.common.BaseActivity;
import com.wanpiao.master.mvp.ui.activity.LoginActivity;


/**
 * 描述: 全局广播监听
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-05-08 10:06
 */
public class GlobalReceiver extends BroadcastReceiver {
    public final static String ACTION_APP_LOGIN_OUT = "ACTION_APP_LOGIN_OUT";
    public final static String ACTION_APP_NEED_UPDATE = "ACTION_APP_NEED_UPDATE";
    public final static  String ACTION_TOKEN_QUESTION = "ACTION_TOKEN_QUESTION";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("jinyangyang+","GlobalReceiver onReceive be called ");
        switch (intent.getAction()) {

            case ACTION_TOKEN_QUESTION:
//                UserProxy.getInstance().deleteJpushAlias(context);
//                Intent loginIntent = new Intent(context, LoginActivity.class);
//                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                UserProxy.getInstance().setOnline(context,false);
//                context.startActivity(loginIntent);
//                ToastUtil.showLong(context,context.getString(R.string.token_invalid_tips));
//                abortBroadcast();
                LoginActivity.startLoginActivity(BaseActivity.mContext);
                break;
            case ACTION_APP_NEED_UPDATE:
//                if (!(ActivityManager.getInstance().getCurrentActivity() instanceof  LoginActivity)) {
//                    Intent updateIntent = new Intent(context, LoginActivity.class);
//                    updateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    UserProxy.getInstance().setOnline(context, false);
//                    context.startActivity(updateIntent);
//                }
//                EventBus.getDefault().post("update",LoginActivity.TO_UPDATE_APP);
//                abortBroadcast();
                break;
            default:
                break;
        }
    }
}
