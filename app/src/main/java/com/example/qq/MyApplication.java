package com.example.qq;

import android.app.Application;

/**
 * Created by 小凳子 on 2016/12/4.
 */

public class MyApplication extends Application {
    //用于发送短信验证码使用
    public final static String APPKEY = "1997943b6ce41";
    public final static String APPSECRET = "514ef84e9f6e97732a048b3a994c46b1";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
