package com.example.qq.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.qq.MyApplication;
import com.example.qq.entity.Param;
import com.example.qq.model.interfaceV.IUserModel;
import com.example.qq.presenter.interfaceV.ISetDataListener;
import com.example.qq.util.okhttpclientmanager.OkHttpClientManager;
import com.example.qq.util.okhttpclientmanager.interfaceV.CallBackListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import cn.smssdk.gui.layout.Res;

public class UserModel implements IUserModel {
    private OkHttpClientManager okHttpClientManager;
    public String action;
    private final static String URL = "http://" + MyApplication.IPADRESS + ":" + MyApplication.PORT + "/SSM/user/";

    public UserModel() {
        this.okHttpClientManager = OkHttpClientManager.getInstance();
    }

    @Override
    public void login(Param[] params, final ISetDataListener setDataListener) {
        action = "login";
        String url = URL + action;

        okHttpClientManager._postAysn(url, new CallBackListener() {
            @Override
            public void error(Request re, IOException io) {
                setDataListener.failed(io);
            }

            @Override
            public void success(Response response) {
                setDataListener.success(response);
            }
        }, params);
    }

    @Override
    public void saveUser(Param param, final ISetDataListener setDataListener) {
        action = "register";
        String url = URL + action;

        okHttpClientManager._postAysn(url, new CallBackListener() {
            @Override
            public void error(Request re, IOException io) {
                setDataListener.failed(io);
            }

            @Override
            public void success(Response response) {
                setDataListener.success(response);
            }
        }, param);
    }
}
