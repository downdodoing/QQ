package com.example.qq.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import com.example.qq.R;
import com.example.qq.activity.interfaceV.IMainPage;
import com.example.qq.entity.Param;
import com.example.qq.presenter.MainPageP;
import com.example.qq.presenter.interfaceV.ISetDataListener;
import com.example.qq.util.circleImage.CircleImage;
import com.example.qq.util.slidslip.SlidingMenu;
import com.squareup.okhttp.Response;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class MainPage extends TabActivity implements IMainPage {
    private TabHost tabHost;

    private TextView message, contact, dynamicIn, netName, signature;

    private FrameLayout collection, album, file, schedule, business_card, member, make_up, wallet, show_signature;
    private LinearLayout set, moon, show_info;
    private SlidingMenu menu;

    private CircleImage head_photo;
    private String userName;

    private boolean isCancelClick;//用于存储是否结束点击事件

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        init();
    }

    @Override
    public void init() {
        tabHost = getTabHost();
        handler = new MyHandler();

        message = (TextView) findViewById(R.id.message);
        contact = (TextView) findViewById(R.id.contact);
        dynamicIn = (TextView) findViewById(R.id.dynamic);

        netName = (TextView) findViewById(R.id.netName);
        signature = (TextView) findViewById(R.id.signature);
        head_photo = (CircleImage) findViewById(R.id.main_page_icircle);

        menu = (SlidingMenu) findViewById(R.id.mMenu);

        initMenuItem();

        message.setOnClickListener(this);
        contact.setOnClickListener(this);
        dynamicIn.setOnClickListener(this);

        getForwardData();
        setTag();
    }

    public void initMenuItem() {
        collection = (FrameLayout) findViewById(R.id.collection);
        album = (FrameLayout) findViewById(R.id.album);
        member = (FrameLayout) findViewById(R.id.member);
        make_up = (FrameLayout) findViewById(R.id.make_up);
        wallet = (FrameLayout) findViewById(R.id.wallet);
        schedule = (FrameLayout) findViewById(R.id.schedule);
        business_card = (FrameLayout) findViewById(R.id.business_card);
        file = (FrameLayout) findViewById(R.id.file);
        show_signature = (FrameLayout) findViewById(R.id.show_signature);

        show_info = (LinearLayout) findViewById(R.id.show_info);

        setOnTouch(collection);
        setOnTouch(album);
        setOnTouch(member);
        setOnTouch(make_up);
        setOnTouch(wallet);
        setOnTouch(schedule);
        setOnTouch(business_card);
        setOnTouch(file);
        setOnTouch(show_signature);

        setOnTouch(show_info);
    }

    @Override
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
    @Override
    public void getForwardData() {
        Intent intent = getIntent();
        //userName = intent.getStringExtra("username");
        userName = "718873250";
        setDataFromInter();
    }

    public void setDataFromInter() {
        MainPageP mainPageP = new MainPageP();
        mainPageP.setSetDataListener(new ISetDataListener() {
            @Override
            public void failed(Exception io) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void success(Response response) {
                String result = null;
                try {
                    result = response.body().string();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                android.os.Message msg = new android.os.Message();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
                try {
                    response.body().close();
                } catch (Exception ex) {

                }
            }
        });
        Param param = new Param("name", userName);
        mainPageP.getUser(param);
    }

    //设置底部导航的点击效果
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message:
                setSelectColor(message, true);
                setDrawable(message, R.mipmap.smessage);

                setSelectColor(contact, false);
                setDrawable(contact, R.mipmap.contact);

                setSelectColor(dynamicIn, false);
                setDrawable(dynamicIn, R.mipmap.ydnamic);
                tabHost.setCurrentTab(0);
                break;
            case R.id.contact:
                setSelectColor(message, false);
                setDrawable(message, R.mipmap.message);

                setSelectColor(contact, true);
                setDrawable(contact, R.mipmap.scontact);

                setSelectColor(dynamicIn, false);
                setDrawable(dynamicIn, R.mipmap.ydnamic);
                tabHost.setCurrentTab(1);
                break;
            case R.id.dynamic:
                setSelectColor(message, false);
                setDrawable(message, R.mipmap.message);

                setSelectColor(contact, false);
                setDrawable(contact, R.mipmap.contact);

                setSelectColor(dynamicIn, true);
                setDrawable(dynamicIn, R.mipmap.sdynamic);
                tabHost.setCurrentTab(2);
                break;
            default:
                break;
        }
    }

    //设置触摸事件
    public void setOnTouch(final ViewGroup view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View child = null;
                if (view instanceof FrameLayout) {
                    child = view.getChildAt(1);
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (null != child) {
                            child.setVisibility(View.VISIBLE);
                        }
                        isCancelClick = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isCancelClick) {
                            clickMenuItem(v);
                            if (null != child) {
                                child.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (null != child) {
                            child.setVisibility(View.GONE);
                        }
                        isCancelClick = true;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    //处理菜单栏点击事件
    public void clickMenuItem(View v) {
        switch (v.getId()) {
            case R.id.show_info:
                showToast("show_info");
                break;
            case R.id.twocode:
                showToast("twocode");
                break;

            case R.id.member:
                showToast("member");
                break;
            case R.id.wallet:
                break;
            case R.id.make_up:
                break;
            case R.id.collection:
                break;
            case R.id.album:
                break;
            case R.id.file:
                break;
            case R.id.schedule:
                break;
            case R.id.business_card:
                break;

            case R.id.set:
                showToast("set");
                break;
            case R.id.moon:
                showToast("set");
                break;
            default:
                break;
        }
    }

    //设置底部导航栏textView中的图片
    @Override
    public void setDrawable(TextView textView, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        //必须经过这一步不然不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    //设置字体颜色
    @Override
    public void setSelectColor(TextView textView, boolean isSelect) {
        if (isSelect) {
            textView.setTextColor(Color.parseColor("#6f564b"));
        } else {
            textView.setTextColor(Color.parseColor("#abadbb"));
        }
    }

    @Override
    public void showToast(String hintStr) {
        Toast.makeText(this, hintStr, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (menu.getIsOpen()) {
                menu.closeMenu();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                MainPage.this.showToast("获取数据失败");
            } else if (msg.what == 1) {
                JSONObject joo = JSON.parseObject(msg.obj.toString());
                Bitmap bitmap = getBitmapByByteStr(joo.getString("photo"));
                Drawable drawable = new BitmapDrawable(bitmap);
                head_photo.setImageDrawable(drawable);
                netName.setText(joo.getString("netname"));
                signature.setText(joo.getString("signature"));
            }
        }
    }

    public Bitmap getBitmapByByteStr(String byteStr) {
        BASE64Decoder decoder = new BASE64Decoder();
        Bitmap bitmap = null;
        try {
            byte[] result = decoder.decodeBuffer(byteStr);
            bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bitmap;
    }
}

