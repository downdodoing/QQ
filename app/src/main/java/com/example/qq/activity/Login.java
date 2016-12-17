package com.example.qq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.MyApplication;
import com.example.qq.R;
import com.example.qq.activity.interfaceV.ILogin;
import com.example.qq.entity.Param;
import com.example.qq.presenter.UserP;
import com.example.qq.presenter.interfaceV.ISetDataListener;
import com.example.qq.util.animation_.Animation_;
import com.squareup.okhttp.Response;

public class Login extends BaseActivity implements ILogin {
    private Animation_ animation_;

    private LinearLayout l_login;
    private CheckBox serve_item;
    private Button log_in_bnt;
    private TextView show_service, disable_login, register, show_login_text;

    private EditText username, password;

    private LoginHanlder handler;

    private String mUsername;
    private String mPassword;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.login);
        MyApplication.addActivity(this);
        init();
        getForwardData();
    }

    //获取前一页面传递的数据
    public void getForwardData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name");
            username.setText(name);
        }
    }

    public void init() {

        handler = new LoginHanlder();

        l_login = (LinearLayout) findViewById(R.id.login);
        //设置动画效果
        animation_ = new Animation_(l_login);
        animation_.setTranslateAnimationranslate();

        serve_item = (CheckBox) findViewById(R.id.serve_item);
        log_in_bnt = (Button) findViewById(R.id.log_in_bnt);

        show_service = (TextView) findViewById(R.id.show_service);
        register = (TextView) findViewById(R.id.register);
        disable_login = (TextView) findViewById(R.id.disable_login);
        show_login_text = (TextView) findViewById(R.id.show_login_text);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        progressBar = (RelativeLayout) findViewById(R.id.progress_bar);
        show_login_text.setText("正在登录...");

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

    @Override
    public void login() {
        if (progressBar.getVisibility() == View.GONE) {
            mUsername = username.getText().toString();
            mPassword = password.getText().toString();
            if (isNull(mUsername)) {
                this.showToast(this, "请输入用户名");
            } else if (isNull(mPassword)) {
                this.showToast(this, "请输入密码");
            } else if (isConnect()) {
                this.setProgressBar();
                intentData(mUsername, mPassword);
            } else {
                this.showToast(this, "请检查网络设置");
            }
        }
    }

    //将数据提交到后台
    public void intentData(String mUsername, String mPassword) {
        UserP userP = new UserP();
        userP.setSetDataListener(new ISetDataListener() {
            @Override
            public void failed() {
                Login.this.showToast(Login.this, "登录失败！");
            }

            @Override
            public void success(Response response) {
                try {
                    String result = response.body().string();

                    Message msg = new Message();
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        userP.getUser(setParams(mUsername, mPassword));
    }

    //设置参数
    public Param[] setParams(String mUsername, String mPassword) {
        Param[] params = new Param[2];

        Param uName = new Param();
        uName.key = "name";
        uName.value = mUsername;
        params[0] = uName;

        Param psd = new Param();
        psd.key = "password";
        psd.value = mPassword;
        params[1] = psd;

        return params;
    }

    class LoginHanlder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            setProgressBar();
            String result = (String) msg.obj;
            if (result.equals("0")) {
                Login.this.showToast(Login.this, "用户名错误");
            } else if (result.equals("1")) {
                Login.this.showToast(Login.this, "密码错误");
            } else {
                Intent intent = new Intent(Login.this, MainPage.class);
                intent.putExtra("username", mUsername);
                startActivity(intent);
            }
        }
    }
}
