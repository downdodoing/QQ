package com.example.qq.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.qq.R;
import com.example.qq.activity.interfaceV.ILogin;
import com.example.qq.util.animation_.Animation_;

/**
 * Created by 小凳子 on 2016/11/23.
 */

public class Login extends BaseActivity implements ILogin {
    private Animation_ animation_;

    private LinearLayout l_login;
    private CheckBox serve_item;
    private Button log_in_bnt;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.login);
        init();
        clickCheckBox();
    }

    public void init() {
        l_login = (LinearLayout) findViewById(R.id.login);

        animation_ = new Animation_(l_login);
        animation_.setTranslateAnimationranslate();

        serve_item = (CheckBox) findViewById(R.id.serve_item);
        log_in_bnt = (Button) findViewById(R.id.log_in_bnt);
    }

    public void clickCheckBox() {
        serve_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    log_in_bnt.setTextColor(Color.rgb(255, 255, 255));
                } else {
                    log_in_bnt.setTextColor(Color.rgb(204, 204, 204));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
