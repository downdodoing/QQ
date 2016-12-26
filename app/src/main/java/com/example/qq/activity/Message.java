package com.example.qq.activity;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qq.R;
import com.example.qq.activity.interfaceV.IMessage;
import com.example.qq.util.animation_.Animation_;

public class Message extends TabActivity implements IMessage, View.OnClickListener {
    private TabHost tabHost;

    private TextView news, phoneCall;
    private ImageView more, shade;

    private PopupWindow popupWindow;
    private Animation_ animation_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        init();
    }

    public void init() {
        tabHost = getTabHost();

        news = (TextView) findViewById(R.id.news);
        phoneCall = (TextView) findViewById(R.id.phoneCall);

        more = (ImageView) findViewById(R.id.more);
        shade = (ImageView) findViewById(R.id.shade);

        more.setOnClickListener(this);
        news.setOnClickListener(this);
        phoneCall.setOnClickListener(this);

        setTag();
    }

    @Override
    public void setTag() {
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("news").setIndicator("news").setContent(new Intent(this, News.class));
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("phoneCall").setIndicator("phoneCall").setContent(new Intent(this, PhoneCall.class));

        tabHost.addTab(tabSpec1);
        tabHost.addTab(tabSpec2);

        tabHost.setCurrentTab(0);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            //底部导航栏
            case R.id.news:
                setSelectColor(news, true);
                news.setBackground(getResources().getDrawable(R.drawable.message_title_show_ba_left_se));

                setSelectColor(phoneCall, false);
                phoneCall.setBackground(getResources().getDrawable(R.drawable.message_title_show_ba_right));

                tabHost.setCurrentTab(0);
                break;
            case R.id.phoneCall:
                setSelectColor(news, false);
                news.setBackground(getResources().getDrawable(R.drawable.message_title_show_ba_left));

                setSelectColor(phoneCall, true);
                phoneCall.setBackground(getResources().getDrawable(R.drawable.message_title_show_ba_right_se));

                tabHost.setCurrentTab(1);
                break;
            //其他
            case R.id.more:
                animation_ = new Animation_(more);
                animation_.setRotateAnimation();

                shade.setVisibility(View.VISIBLE);
                showPopupwindow(view);
                break;
            //popupwindow组件点击事件
            case R.id.more_chat:
                break;
            case R.id.addfriend:
                break;
            case R.id.scan:
                break;
            case R.id.deliver:
                break;
            case R.id.password:
                break;
            default:
                break;
        }
    }

    @Override
    public void showPopupwindow(View view) {
        //获取浮动框的布局
        View convertView = LayoutInflater.from(this).inflate(R.layout.show_popupwindow, null);
        //设置为true点击外部区域，弹框消失
        popupWindow = new PopupWindow(convertView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        //返回true，touch事件将会被拦截，拦截后popupwindow的ontouch事件将不会被调用，这样点击外部区域将不会消失
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //设置了背景点击外部区域，popupwindow才会退出最前台，其他点击事件才会生效
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.message_title_popupwindow));
        //显示在点击的view下面
        popupWindow.showAsDropDown(view, dpTopx(-135), more.getBottom() / 2 + 5);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                animation_.setRotateAnimationOpposite();
                shade.setVisibility(View.GONE);
            }
        });
        initPopupwindow(convertView);
    }

    //将dp转化为px
    public int dpTopx(int dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (scale * dpValue);
    }

    //初始化popupwindow中的组件
    public void initPopupwindow(View convertView) {
        TextView more_chat = (TextView) convertView.findViewById(R.id.more_chat);
        TextView addfriend = (TextView) convertView.findViewById(R.id.addfriend);
        TextView scan = (TextView) convertView.findViewById(R.id.scan);
        TextView deliver = (TextView) convertView.findViewById(R.id.deliver);
        TextView pay = (TextView) convertView.findViewById(R.id.pay);

        more_chat.setOnClickListener(this);
        addfriend.setOnClickListener(this);
        scan.setOnClickListener(this);
        deliver.setOnClickListener(this);
        pay.setOnClickListener(this);
    }

    //设置点击事件
    public void setPopupWindowOnclick(View view) {
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
    }

    //设置底部导航栏textView中的图片
    @Override
    public void setDrawable(TextView textView, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        //必须经过这一步不然不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    //设置点击后的字体颜色
    @Override
    public void setSelectColor(TextView textView, boolean isSelected) {
        if (isSelected) {
            textView.setTextColor(Color.parseColor("#6f564b"));
        } else {
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    public void showToast(String hintStr) {
        Toast.makeText(this, hintStr, Toast.LENGTH_SHORT).show();
    }
}
