package com.example.qq.util.slidslip;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {
    //使该类不能实例化
    private ScreenUtil(){

    }
    public static  int getScreenWidth(Context context){
        return getDisplayMetrics(context).widthPixels;
    }
    public static int getScreenHeight(Context context){
        return getDisplayMetrics(context).heightPixels;
    }

    private static DisplayMetrics getDisplayMetrics(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return  displayMetrics;
    }
}
