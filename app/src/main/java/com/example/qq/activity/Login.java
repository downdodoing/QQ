package com.example.qq.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qq.MyApplication;
import com.example.qq.R;
import com.example.qq.activity.interfaceV.ILogin;
import com.example.qq.util.animation_.Animation_;

public class Login extends BaseActivity implements ILogin {
    private Animation_ animation_;

    private LinearLayout l_login;
    private CheckBox serve_item;
    private Button log_in_bnt;
    private TextView show_service, disable_login, register;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.login);
        MyApplication.addActivity(this);
        init();
    }

    public void init() {
        l_login = (LinearLayout) findViewById(R.id.login);
        //设置动画效果
        animation_ = new Animation_(l_login);
        animation_.setTranslateAnimationranslate();

        serve_item = (CheckBox) findViewById(R.id.serve_item);
        log_in_bnt = (Button) findViewById(R.id.log_in_bnt);
        show_service = (TextView) findViewById(R.id.show_service);
        register = (TextView) findViewById(R.id.register);
        disable_login = (TextView) findViewById(R.id.disable_login);

        setOnTouch();
        clickCheckBox();
    }

    //复选框设置点击事件
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

    public void setOnTouch() {
        this.setOnTouch(log_in_bnt, R.drawable.bnt_back_down, R.drawable.bnt_back_up, this, true);
        this.setOnTouch(show_service, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
        this.setOnTouch(disable_login, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
        this.setOnTouch(register, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
    }
}
