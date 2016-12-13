package com.example.qq.util.okhttpclientmanager.interfaceV;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public interface CallBackListener {
    public void error(Request re, IOException io);

    public void success(Response response);
}
