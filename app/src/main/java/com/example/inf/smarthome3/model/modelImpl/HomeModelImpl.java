package com.example.inf.smarthome3.model.modelImpl;

import com.example.inf.smarthome3.model.HomeNative;
import com.example.inf.smarthome3.model.model.HomeModel;
import com.example.inf.smarthome3.viewmodel.HomeState;

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
        HomeNative.openACDev();
        HomeNative.openCurtainDev();
        HomeNative.openGasDev();
        HomeNative.openLightSenseDev();
        HomeNative.openDev();
        HomeNative.openInfraredDev();

    }
    @Override
    public void closeLightWithJNI(int type) {
        HomeNative.lightOp(1,type);
        HomeNative.lightOp(1,type);
    }

    @Override
    public void openLightWithJNI(int type) {
        HomeNative.lightOp(0,type);
        HomeNative.lightOp(0,type);
    }

    @Override
    public void acOperate(int type) {
        HomeNative.operateACDev(type);
    }

    @Override
    public void realyOperate(int type) {
        HomeNative.operateDoor(type);
    }

    @Override
    public void curtainOperate(int type) {
        
    }

    @Override
    public void closeDev() {
        HomeNative.closeLightDev();
        HomeNative.closeDoorDev();
        HomeNative.closeACDev();
        HomeNative.closeCurtainDev();
        HomeNative.closeGasDev();
        HomeNative.closeLightSenseDev();
        HomeNative.closeDev();
        HomeNative.closeInfraredDev();
    }


}
