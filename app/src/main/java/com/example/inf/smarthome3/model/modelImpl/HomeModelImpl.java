package com.example.inf.smarthome3.model.modelImpl;

import com.example.inf.smarthome3.model.HomeNative;
import com.example.inf.smarthome3.model.model.HomeModel;

import org.json.JSONObject;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by INF on 2017/3/10.
 * homeModel接口的实现类
 */

public class HomeModelImpl implements HomeModel {

    //构造函数开启驱动
    public HomeModelImpl (){

        HomeNative.openLightDev();
        HomeNative.openDoorDev();

    }
    @Override
    public void closeLightWithJNI() {
        HomeNative.lightOp(1,0);
        HomeNative.lightOp(1,1);
    }

    @Override
    public void openLightWithJNI() {
        HomeNative.lightOp(0,0);
        HomeNative.lightOp(0,1);
    }

    @Override
    public void openRealy() {

        HomeNative.operateDoor(0);
    }

    @Override
    public void closeRealy() {

        HomeNative.operateDoor(1);
    }


}
