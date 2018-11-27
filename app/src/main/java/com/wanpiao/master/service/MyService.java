package com.wanpiao.master.service;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import cn.jpush.android.service.PushService;

public class MyService extends PushService {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("jinyangyang+","Service Service Service Service Service Service Service Service Service Service");
    }
}
