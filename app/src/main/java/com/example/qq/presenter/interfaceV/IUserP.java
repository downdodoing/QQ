package com.example.qq.presenter.interfaceV;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.entity.Param;
import com.example.qq.entity.User;


public interface IUserP {

    public void saveUser(Param param);

    public void login(Param[] params);

}
