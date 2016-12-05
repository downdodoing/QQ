package com.example.qq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq.MyApplication;
import com.example.qq.R;

import cn.smssdk.SMSSDK;

/**
 * Created by 小凳子 on 2016/11/23.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }

    public abstract void initView(Bundle savedInstanceState);

    /*
    * colorB:点击之前的颜色
    * colorE:点击之后的颜色
    * v instanceof TextView/Button都为true
    * */
    public void setOnTouch(final View v, final int colorE, final int colorB, final Activity activity, final boolean isValid1) {
        isValid = isValid1;
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int id = v.getId();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (v instanceof Button) {
                            if (isValid) {
                                ((Button) v).setBackgroundResource(colorE);
                            }
                        } else if (v instanceof TextView) {
                            ((TextView) v).setTextColor(colorE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (v instanceof Button) {
                            if (isValid) {
                                ((Button) v).setBackgroundResource(colorB);
                            }
                        } else if (v instanceof TextView) {
                            ((TextView) v).setTextColor(colorB);
                        }
                        if (id == R.id.show_service) {
                            Intent intent = new Intent(activity, ShowService.class);
                            startActivity(intent);
                        } else if (id == R.id.register) {
                            Intent intent = new Intent(activity, PhoneNum.class);
                            startActivity(intent);
                        } else if (id == R.id.next && isValid) {
                            intentToNext();
                        } else if (id == R.id.get_verifaction) {
                            afterClickgetVerifaction();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });
    }

    //空方法声明,在子类中重写
    public void afterClickgetVerifaction() {
    }

    //跳转到下一个页面
    public void intentToNext() {
    }

    /*
    * 获取验证码
    * handler:调用该方法传递handler用以处理返回的数据
    * */
    public void getVerifavction(Activity activity, Handler handler) {
        //初始化发送短信SDK
        SMSSDK.initSDK(activity, MyApplication.APPKEY, MyApplication.APPSECRET);
    }

    public void showToast(Activity activity, String hintStr) {
        Toast.makeText(activity, hintStr, Toast.LENGTH_SHORT).show();
    }
}
