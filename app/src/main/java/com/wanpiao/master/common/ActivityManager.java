package com.wanpiao.master.common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: Activity管理类
 * --------------------------------------------
 * 工程:
 * #0000     @author mwy     创建日期: 2018-04-03 14:54
 */
public class ActivityManager {
    private static ActivityManager mActivityManager;

    private List<Activity> mActivities=new ArrayList<Activity>();
    private Activity currentActivity;
    //将构造方法私有化，所以不能通构造方法来初始化ActivityManager
    private ActivityManager(){}

    //采用单例模式初始化ActivityManager，使只初始化一次
    public static ActivityManager getInstance(){
        if(mActivityManager==null){
            synchronized (ActivityManager.class){
                if (mActivityManager == null){
                    mActivityManager=new ActivityManager();
                }
            }
        }
        return mActivityManager;
    }
    //添加activity
    public void addActivity(Activity activity){
        mActivities.add(activity);
    }
    //移除activity
    public void removeActivity(Activity activity){
        mActivities.remove(activity);
    }
    //将activity全部关闭掉
    public void clear(){
        for(Activity activity:mActivities){
            if (activity != null){
                activity.finish();
            }
        }
    }

    public <T>  void closeActivity(Class<T> clazz){
        for(Activity activity:mActivities){
            if (clazz.isInstance(activity)){
                if (activity != null){
                    activity.finish();
                }
            }
        }
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public boolean isContains(Activity mainActivity) {
        return mActivities.contains(mainActivity);
    }

    public boolean isContains(Class<?> cls){
        if (null!=mActivities){
            for (Activity activity : mActivities){
                if (activity.getClass().equals(cls)){
                    return true;
                }
            }
        }
        return false;
    }
}
