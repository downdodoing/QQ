package com.example.qq.activity.interfaceV;

import android.view.View;

/**
 * Created by 小凳子 on 2016/12/3.
 */

public interface IRegister extends View.OnClickListener {
    public void init();

    public void setOnTouch();

    public void spinner();

    public void forwardData();

    public void showSelect();

    public void hiddeSelect();
}
