package com.example.qq.util.okhttpclientmanager;


import android.util.Log;

import com.example.qq.entity.Param;
import com.example.qq.util.okhttpclientmanager.interfaceV.CallBackListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private OkHttpClient okHttpClient;
    private static OkHttpClientManager okHttpClientManager;

    private OkHttpClientManager() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpClientManager getInstance() {
        if (null == okHttpClientManager) {
            synchronized (OkHttpClientManager.class) {
                okHttpClientManager = new OkHttpClientManager();
            }
        }
        return okHttpClientManager;
    }

    /*
    * 同步的get请求
    * @param url
    * @return response
    * */
    public Response _getAsyn(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        Response excute = call.execute();
        return excute;
    }

    /*
    * 同步的get请求
    *
    * @param url
    * @return string
    * */
    public String _getAsynString(String url) throws IOException {
        Response excute = _getAsyn(url);
        return excute.body().string();

    }

    /*
    * 异步get 请求
    *
    * @param url
    * @param callback
    * */
    public void _getAsyn(String url, CallBackListener callBackListener) throws IOException {
        Request request = new Request.Builder().url(url).build();
        deliveryResult(request, callBackListener);
    }

    /*
    * 同步post请求
    * @param url
    * @param params post请求参数
    * @return Response
    * */
    public Response _postAsyn(String url, Param... params) throws IOException {
        Request request = buildPostRequst(url, params);
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    /*
    * 同步post请求
    * @param url
    * @param params post请求参数
    * @return String
    * */
    public String _posyAsynString(String url, Param... params) throws IOException {
        Response response = _postAsyn(url, params);
        return response.body().string();
    }

    /*
    * 异步post请求
    * */
    public void _postAysn(String url, CallBackListener callBackListener, Param... param) {
        Request request = buildPostRequst(url, param);
        deliveryResult(request, callBackListener);
    }

    private Request buildPostRequst(String url, Param... params) {
        if (null == params) {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    private void deliveryResult(Request request, final CallBackListener callBackListener) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBackListener.error(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                callBackListener.success(response);
            }
        });
    }
}
