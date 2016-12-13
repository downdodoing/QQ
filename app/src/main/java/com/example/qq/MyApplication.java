package com.example.qq;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    //用于发送短信验证码使用
    public final static String APPKEY = "1997943b6ce41";
    public final static String APPSECRET = "514ef84e9f6e97732a048b3a994c46b1";

    //用于网络请求数据配置URL
    public final static String IPADRESS = "192.168.191.1";
    public final static String PORT = "8080";

    //用于存储已经出现的activity
    public final static List<Activity> mList = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void addActivity(Activity activity) {
        mList.add(activity);
    }

    /*
    * count:表示从数据结构中删除多少个activity
    * */
    public static void removeActitivy(int count) {
        int size = mList.size();
        if (count > size) {
            throwIndexOutOfBoundsException(count, size);
        }
        for (int i = 0; i < count; i++) {
            size = mList.size();
            mList.get(size - 1).finish();
            mList.remove(size - 1);
        }
    }

    public static void clearActivity() {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).finish();
        }
        mList.clear();
    }

    //数组越界
    public static IndexOutOfBoundsException throwIndexOutOfBoundsException(int index, int size) {
        throw new IndexOutOfBoundsException("Invalid index " + index + ", size is " + size);
    }
}
