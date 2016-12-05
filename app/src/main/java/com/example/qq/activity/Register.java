package com.example.qq.activity;

import android.os.Bundle;

import com.example.qq.R;
import com.example.qq.activity.interfaceV.IRegister;

/**
 * Created by 小凳子 on 2016/12/2.
 */

public class Register extends BaseActivity implements IRegister {
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.register);
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void setOnTouch() {

    }
}
