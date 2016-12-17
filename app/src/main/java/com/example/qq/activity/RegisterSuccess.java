package com.example.qq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qq.MyApplication;
import com.example.qq.R;

public class RegisterSuccess extends BaseActivity {
    private TextView back, title_text, show_name, r_s_login;
    private String name;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.register_success);
        MyApplication.addActivity(this);
        init();
        getForwardData();
    }

    //获取前一页面传递数据
    public void getForwardData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        show_name.setText(name);
    }

    public void init() {
        back = (TextView) findViewById(R.id.back);
        title_text = (TextView) findViewById(R.id.title_text);
        show_name = (TextView) findViewById(R.id.show_name);
        r_s_login = (TextView) findViewById(R.id.r_s_login);

        back.setVisibility(View.GONE);
        title_text.setText("注册成功");

        this.setOnTouch(r_s_login, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
    }

    @Override
    public void registerSuccess() {
        MyApplication.removeActitivy(2);
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
