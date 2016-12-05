package com.example.qq.util.timer;

import android.util.Log;

import com.example.qq.util.timer.interfaceV.TimerListener;

public class Timer {
    private int seconds;//用于存储需要倒计时的总时间数
    private static Timer timer;
    private TimerListener mListener;
    private Thread thread;
    private boolean isContinue;

    public static Timer getInstance() {
        if (null == timer) {
            timer = new Timer();
        }
        return timer;
    }

    //指定特定秒数
    public static Timer getInstance(int seconds) {
        if (null == timer) {
            timer = new Timer(seconds);
        }
        return timer;
    }

    //默认为60秒
    private Timer() {
        this(60);
    }

    private Timer(int seconds) {
        this.seconds = seconds;
    }

    //开始倒计时
    public void startCountDown() {
        isContinue = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isContinue) {
                    seconds--;
                    mListener.setTime(seconds);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void setmListener(TimerListener mListener) {
        this.mListener = mListener;
    }

    public void stopTimer() {
        if (null != thread) {
            thread = null;
        }
        if (null != timer) {
            timer = null;
        }
        isContinue = false;
    }
}
