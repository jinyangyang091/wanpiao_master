package com.wanpiao.master.mvp.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.wanpiao.master.R;
import com.wanpiao.master.di.module.entity.User;
import com.wanpiao.master.utils.L;
import com.wanpiao.master.utils.SPUtils;

/**
 * @Description: #
 * #0000      @Author: tianxiao     2018/10/17      onCreate
 */
public class UserProxy {
    private User user;
    private static UserProxy instance;

    public User getUser(Context context){
        if (user == null){
            if (context == null){
                L.i("getUser context is null");
                return null;
            }
            if (SPUtils.contains(context,"user")){
                user = SPUtils.getObject(context,"user",User.class);
                if (user == null){
                    SPUtils.remove(context,"user");
                    user = new User();
                }
                SPUtils.setObject(context,"user",user);
            }else{
                user = new User();
                SPUtils.setObject(context,"user",user);
            }
            return user;
        }
        return user;
    }

    public void setUser(Context context,User user){
        if (user == null){
            return;
        }else{
            this.user = user;
            SPUtils.setObject(context,"user",user);
        }
    }


    /**
     * 移除用户信息
     * @param context
     */
    public void removeUser(Context context){
        if (SPUtils.contains(context,"user")){
            SPUtils.remove(context,"user");
        }
        this.user = null;
    }

    private UserProxy(){

    }

    public static synchronized UserProxy getInstance(){
        if (instance == null){
            instance = new UserProxy();
        }
        return instance;
    }


    /**
     * 验证是否登录
     * @param context
     * @return
     */
    public boolean isOnline(Context context){
        return SPUtils.get(context,"isOnline",false);
    }

    /**
     * 设置登录状态
     * @param context
     * @param online
     */
    public void setOnline(Context context,boolean online){
        SPUtils.put(context,"isOnline",online);
    }

    /**
     * 退出登录
     */
    public void loginOut(Activity context){
        SPUtils.setObject(context,"user",new User());
        SPUtils.put(context,"isOnline",false);
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//        context.overridePendingTransition(R.anim.slide_down_in,R.anim.slide_down_out);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            context.finishAndRemoveTask();
//        }else{
//            context.finish();
//        }
    }


}

