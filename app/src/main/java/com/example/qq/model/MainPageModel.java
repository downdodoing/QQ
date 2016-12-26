package com.example.qq.model;

import com.example.qq.MyApplication;
import com.example.qq.entity.Param;
import com.example.qq.model.interfaceV.IMainPageModel;
import com.example.qq.presenter.interfaceV.ISetDataListener;
import com.example.qq.util.okhttpclientmanager.OkHttpClientManager;
import com.example.qq.util.okhttpclientmanager.interfaceV.CallBackListener;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainPageModel implements IMainPageModel {
    private OkHttpClientManager okHttpClientManager;
    public String action;
    private final static String URL = "http://" + MyApplication.IPADRESS + ":" + MyApplication.PORT + "/SSM/user/";

    public MainPageModel() {
        this.okHttpClientManager = OkHttpClientManager.getInstance();
    }

    @Override
    public void getUser(Param param, final ISetDataListener setDataListener) {
        action = "getUser";
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
