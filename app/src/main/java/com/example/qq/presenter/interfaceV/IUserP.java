package com.example.qq.presenter.interfaceV;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.entity.User;


public interface IUserP {

    public void saveUser(JSONObject joo);

    public User getUser();

}
