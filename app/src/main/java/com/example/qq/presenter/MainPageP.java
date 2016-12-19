package com.example.qq.presenter;

import com.example.qq.entity.Param;
import com.example.qq.model.MainPageModel;
import com.example.qq.model.interfaceV.IMainPageModel;
import com.example.qq.presenter.interfaceV.IMainPageP;
import com.example.qq.presenter.interfaceV.ISetDataListener;

public class MainPageP implements IMainPageP {
    private ISetDataListener setDataListener;
    private IMainPageModel mainPageModel;

    public MainPageP() {
        mainPageModel = new MainPageModel();
    }

    public void setSetDataListener(ISetDataListener setDataListener) {
        this.setDataListener = setDataListener;
    }

    @Override
    public void getUser(Param param) {
        mainPageModel.getUser(param, setDataListener);
    }
}