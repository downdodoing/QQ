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

public class UserModel implements IUserModel {
    private OkHttpClientManager okHttpClientManager;
    public final static String ACTION = "/SSM/user/register";
    private final static String URL = "http://" + MyApplication.IPADRESS + ":" + MyApplication.PORT + ACTION;

    private String result;//用于存储返回的结果

    public UserModel() {
        this.okHttpClientManager = OkHttpClientManager.getInstance();
    }

    @Override
    public void getUser() {

    }

    @Override
    public void saveUser(JSONObject jooo, final ISetDataListener setDataListener) {
        Param param = new Param();
        param.key = "user";
        param.value = jooo.toJSONString();
        okHttpClientManager._postAysn(URL, new CallBackListener() {
            @Override
            public void error(Request re, IOException io) {
                Log.i("UserModel", "error");
                setDataListener.failed();
            }

            @Override
            public void success(Response response) {
                Log.i("UserModel", "success");
                try {
                    JSONObject jooo = (JSONObject) JSON.toJSON(response.body().string());
                    setDataListener.success(jooo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, param);
    }
}
