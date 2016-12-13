package com.example.qq.model.interfaceV;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.presenter.interfaceV.ISetDataListener;

public interface IUserModel {

    public void getUser();

    public void saveUser(JSONObject jooo, ISetDataListener setDataListener);

}
