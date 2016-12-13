package com.example.qq.presenter.interfaceV;

import com.alibaba.fastjson.JSONObject;

public interface ISetDataListener {
    public void failed();

    public void success(JSONObject jooo);
}
