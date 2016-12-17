package com.example.qq.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.qq.R;

public class MainPage extends TabActivity implements View.OnClickListener {
    private TabHost tabHost;

    private TextView message, contact, dynamicIn;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        init();
    }

    public void init() {
        getForwardData();
        tabHost = getTabHost();
        message = (TextView) findViewById(R.id.message);
        contact = (TextView) findViewById(R.id.contact);
        dynamicIn = (TextView) findViewById(R.id.dynamic);

        message.setOnClickListener(this);
        contact.setOnClickListener(this);
        dynamicIn.setOnClickListener(this);

        setTag();
    }

    public void setTag() {
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("message").setIndicator("message").setContent(new Intent(this, Message.class));
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("contact").setIndicator("contact").setContent(new Intent(this, Contact.class));
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("dynamic").setIndicator("dynamic").setContent(new Intent(this, DynamicIn.class));

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);

        tabHost.setCurrentTab(0);
    }

    //获取前一页面的数据
    public void getForwardData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");
    }

    //设置底部导航的点击效果
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message:
                setSelectColor(message, true);
                message.setCompoundDrawables(null, getDrawabl(R.mipmap.smessage), null, null);

                setSelectColor(contact, false);
                contact.setCompoundDrawables(null, getDrawabl(R.mipmap.contact), null, null);

                setSelectColor(dynamicIn, false);
                dynamicIn.setCompoundDrawables(null, getDrawabl(R.mipmap.ydnamic), null, null);
                tabHost.setCurrentTab(0);
                break;
            case R.id.contact:
                setSelectColor(message, false);
                message.setCompoundDrawables(null, getDrawabl(R.mipmap.message), null, null);

                setSelectColor(contact, true);
                contact.setCompoundDrawables(null, getDrawabl(R.mipmap.scontact), null, null);

                setSelectColor(dynamicIn, false);
                dynamicIn.setCompoundDrawables(null, getDrawabl(R.mipmap.ydnamic), null, null);
                tabHost.setCurrentTab(1);
                break;
            case R.id.dynamic:
                setSelectColor(message, false);
                message.setCompoundDrawables(null, getDrawabl(R.mipmap.message), null, null);

                setSelectColor(contact, false);
                contact.setCompoundDrawables(null, getDrawabl(R.mipmap.contact), null, null);

                setSelectColor(dynamicIn, true);
                dynamicIn.setCompoundDrawables(null, getDrawabl(R.mipmap.sdynamic), null, null);
                tabHost.setCurrentTab(2);
                break;
            default:
                break;
        }
    }

    //设置textView中的图片必须经过这一步不然不显示
    public Drawable getDrawabl(int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    //设置字体颜色
    public void setSelectColor(TextView textView, boolean isSelect) {
        if (isSelect) {
            textView.setTextColor(Color.parseColor("#6f564b"));
        } else {
            textView.setTextColor(Color.parseColor("#abadbb"));
        }
    }
}

