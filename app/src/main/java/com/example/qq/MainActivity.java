package com.example.qq;

import android.os.Bundle;

import com.example.qq.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        MyApplication.addActivity(this);
    }
}
