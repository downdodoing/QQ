package com.example.qq.presenter.interfaceV;

import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Response;

public interface ISetDataListener {
    public void failed(Exception io);

    public void success(Response response);
}
