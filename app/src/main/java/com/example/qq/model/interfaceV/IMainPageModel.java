package com.example.qq.model.interfaceV;

import com.example.qq.entity.Param;
import com.example.qq.presenter.interfaceV.ISetDataListener;

public interface IMainPageModel {
    public void getUser(Param param, ISetDataListener setDataListener);
}
