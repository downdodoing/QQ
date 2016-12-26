package com.example.qq.activity.interfaceV;

import android.view.View;
import android.widget.TextView;

public interface IMessage {
    public void init();

    public void setTag();

    public void setDrawable(TextView textView, int drawableId);

    public void setSelectColor(TextView textView, boolean isSelected);

    public void showPopupwindow(View view);
}
