package com.example.qq.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qq.MyApplication;
import com.example.qq.R;
import com.example.qq.activity.interfaceV.IPhoneNum;
import com.example.qq.util.timer.Timer;
import com.example.qq.util.timer.interfaceV.TimerListener;
import com.example.qq.util.timer.service.MService;
import com.example.qq.util.verificationCode.VerificationCode;

import cn.smssdk.SMSSDK;

public class PhoneNum extends BaseActivity implements IPhoneNum {
    private TextView title_text, show_service, secret, back, get_verifaction;

    private EditText phone_num, input_verifaction;

    private Button next;

    private CheckBox checkBox;

    private boolean isValid;

    private MyHandler handler;

    private VerificationCode verificationCode;
    private String phoneNum;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.phonenum);
        //将当前Activity添加在数据结构中以便结束
        MyApplication.addActivity(this);
        init();
    }

    @Override
    public void init() {

        handler = new MyHandler();

        get_verifaction = (TextView) findViewById(R.id.get_verifaction);
        title_text = (TextView) findViewById(R.id.title_text);
        show_service = (TextView) findViewById(R.id.p_show_service);
        secret = (TextView) findViewById(R.id.secret);
        back = (TextView) findViewById(R.id.back);

        phone_num = (EditText) findViewById(R.id.phoneNum);
        input_verifaction = (EditText) findViewById(R.id.input_verifaction);

        next = (Button) findViewById(R.id.next);
        checkBox = (CheckBox) findViewById(R.id.phone_check);

        title_text.setText("验证手机号码");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNum.this.finish();
            }
        });
        setOnTouch();
        getData();
    }

    @Override
    public void setOnTouch() {
        this.setOnTouch(show_service, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
        this.setOnTouch(secret, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
        this.setOnTouch(get_verifaction, Color.rgb(132, 218, 249), Color.rgb(0, 165, 224), this, true);
    }

    /*
    * 对输入框的输入实现监听
    * */
    @Override
    public void getData() {
        phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNum = phone_num.getText().toString();
                if (11 == phoneNum.length()) {
                    isValid = true;
                    clickCheckBox();
                } else {
                    isValid = false;
                }
                setColor(isValid);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setColor(boolean isValidC) {
        if (!isValidC) {
            next.setBackgroundResource(R.drawable.next_bnt_back_invalid);
            next.setTextColor(Color.rgb(187, 187, 187));
        } else {
            //将按钮设置为可点击状态
            next.setClickable(true);
            next.setBackgroundResource(R.drawable.bnt_back_up);
            next.setTextColor(Color.rgb(255, 255, 255));
        }
        //当按钮可点击是，点击按钮有点击效果
        this.setOnTouch(next, R.drawable.bnt_back_down, R.drawable.bnt_back_up, this, isValidC);
    }

    @Override
    public void clickCheckBox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isValid) {
                    if (isChecked) {
                        setColor(true);
                    } else {
                        setColor(false);
                    }
                }
            }
        });
    }

    @Override
    public void intentToNext() {
        String mCode = input_verifaction.getText().toString();
        phoneNum = phone_num.getText().toString();

        if (11 == phoneNum.length()) {
//            if (!mCode.equals("")) {
//                //提交输入的验证码
//                handleVerificationCode(phoneNum);
//                verificationCode.submitCode(mCode);
//            } else {
//                this.showToast(this, "请输入验证码");
//            }
            Intent intent = new Intent(this, Register.class);
            intent.putExtra("phoneNum", phoneNum);
            startActivity(intent);
        } else {
            this.showToast(this, "请输入正确的手机号码");
        }
    }

    @Override
    public void afterClickgetVerifaction() {
        //当发送验证码为可点击状态时才能进行发送验证码请求
        if (get_verifaction.isClickable() && get_verifaction()) {
            Intent intent = new Intent(this, MService.class);
            startService(intent);
            begin();
        }
    }

    //开始倒计时
    private void begin() {
        Timer timer = Timer.getInstance();
        timer.setmListener(new TimerListener() {
            @Override
            public void setTime(int second) {
                Message msg = new Message();
                if (second > 0) {
                    msg.what = 0;
                    msg.arg1 = second;
                } else {
                    Intent intent = new Intent(PhoneNum.this, MService.class);
                    stopService(intent);
                }
                handler.sendMessage(msg);
            }
        });
    }

    //短信验证码的发送
    public boolean get_verifaction() {
        phoneNum = phone_num.getText().toString();
        if (phoneNum.length() == 11) {
            handleVerificationCode(phoneNum);
            verificationCode.getVerificationCode();
            return true;
        } else {
            this.showToast(this, "请输入完整的电话号码");
            return false;
        }
    }

    public void handleVerificationCode(String phoneNum) {
        verificationCode = new VerificationCode(this, phoneNum, handler);
        verificationCode.get_request();
    }

    public void handler_verifaction(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        if (result == SMSSDK.RESULT_COMPLETE) {
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                this.showToast(this, "验证码发送成功");
            } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//验证码校验成功
                Intent intent = new Intent(this, Register.class);
                intent.putExtra("phoneNum", phoneNum);
                startActivity(intent);
            } else {
                this.showToast(this, "验证码错误");
            }
        } else {
            this.showToast(this, "验证码发送失败");
        }
        verificationCode.unRegister();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (0 == msg.what) {//处理倒计时器
                int second = msg.arg1;
                if (second >= 1) {
                    get_verifaction.setClickable(false);
                    get_verifaction.setText(second + "秒");
                } else {
                    get_verifaction.setClickable(true);
                    get_verifaction.setText("重新发送");
                }
            } else if (1 == msg.what) {//处理获取验证码
                handler_verifaction(msg);
            }
        }
    }
}
