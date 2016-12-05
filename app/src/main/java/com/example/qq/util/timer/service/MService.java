package com.example.qq.util.timer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.qq.util.timer.Timer;

public class MService extends Service {
    public final IBinder binder = new LocalBinder();
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = Timer.getInstance();
        timer.startCountDown();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.stopTimer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //通过该内部类获取Binder
    public class LocalBinder extends Binder {
        public String string = "BINDER";

        public MService getService() {
            return MService.this;
        }
    }
}
