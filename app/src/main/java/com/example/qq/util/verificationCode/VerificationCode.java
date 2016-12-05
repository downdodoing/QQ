package com.example.qq.util.verificationCode;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.qq.MyApplication;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class VerificationCode {
    private String phoneNum;

    private Handler handler;

    private EventHandler ev;

    public VerificationCode(Activity activity, String phoneNum, Handler handler) {
        //初始化SDK
        SMSSDK.initSDK(activity, MyApplication.APPKEY, MyApplication.APPSECRET);
        this.phoneNum = phoneNum;
        this.handler = handler;
    }

    public void get_request() {
        ev = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(ev);
    }

    public void submitCode(String mCode) {
        SMSSDK.submitVerificationCode("86", phoneNum, mCode);
    }

    public void getVerificationCode() {
        SMSSDK.getVerificationCode("86", phoneNum);
    }

    public void unRegister() {
        SMSSDK.unregisterEventHandler(ev);
    }
}

