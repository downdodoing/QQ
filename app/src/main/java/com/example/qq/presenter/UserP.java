package com.example.qq.presenter;

import com.alibaba.fastjson.JSONObject;
import com.example.qq.activity.interfaceV.IRegister;
import com.example.qq.entity.User;
import com.example.qq.model.UserModel;
import com.example.qq.model.interfaceV.IUserModel;
import com.example.qq.presenter.interfaceV.ISetDataListener;
import com.example.qq.presenter.interfaceV.IUserP;

public class UserP implements IUserP {

    private ISetDataListener setDataListener;
    private IUserModel userModel;

    public UserP() {
        this.userModel = new UserModel();
    }

    public void setSetDataListener(ISetDataListener setDataListener) {
        this.setDataListener = setDataListener;
    }

    @Override
    public void saveUser(JSONObject joo) {
        userModel.saveUser(joo,setDataListener);
    }

    @Override
    public User getUser() {
        return null;
    }
}

