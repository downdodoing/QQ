package com.example.qq.presenter.interfaceV;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.entity.Param;
import com.example.qq.entity.User;


public interface IUserP {

    public void saveUser(JSONObject joo);

    public void getUser(Param[] params);

}
