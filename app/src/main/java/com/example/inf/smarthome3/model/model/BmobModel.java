package com.example.inf.smarthome3.model.model;

import com.example.inf.smarthome3.model.modelImpl.RealTimeListener;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by INF on 2017/3/14.
 */

public interface BmobModel {
    //通知bmob 家具的灯关了 （即 修改云端数据库）
    void saveStateToBmob(BmobObject bmobObject , SaveListener savelistener);
    void startHomeStateListen(RealTimeListener listener);
    void stopHomeStateListen(RealTimeListener listener);
}
