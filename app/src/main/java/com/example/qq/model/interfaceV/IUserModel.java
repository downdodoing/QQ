package com.example.qq.model.interfaceV;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.entity.Param;
import com.example.qq.presenter.interfaceV.ISetDataListener;

public interface IUserModel {

    public void getUser(Param[] params, ISetDataListener setDataListener);

    public void saveUser(JSONObject jooo, ISetDataListener setDataListener);

}
