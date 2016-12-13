package com.example.qq;

import android.os.Bundle;
import android.view.View;

import com.example.qq.activity.BaseActivity;
import com.jph.takephoto.app.TakePhotoActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends BaseActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        MyApplication.addActivity(this);
    }
}
