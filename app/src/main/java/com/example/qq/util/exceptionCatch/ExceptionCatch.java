package com.example.qq.util.exceptionCatch;

import android.content.Context;

import com.example.qq.MyApplication;

public class ExceptionCatch implements Thread.UncaughtExceptionHandler {
    private Context mContext;
    private static ExceptionCatch instace = new ExceptionCatch();

    private ExceptionCatch() {

    }

    //单例模式
    public static synchronized ExceptionCatch getInstace() {
        return instace;
    }

    public void init(Context mContextontext) {
        this.mContext = mContext;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        MyApplication.clearActivity();
        System.exit(0);
    }
}
