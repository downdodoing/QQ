package com.example.qq.activity.interfaceV;

import android.view.View;
import android.widget.TextView;

public interface IMainPage extends View.OnClickListener {
    public void setTag();

    public void getForwardData();

    public void setDrawable(TextView textView, int drawableId);

    public void setSelectColor(TextView textView, boolean isSelect);

    public void init();

    public void showToast(String hintStr);
}
