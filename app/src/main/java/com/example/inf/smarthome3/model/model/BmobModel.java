package com.example.inf.smarthome3.model.model;

import com.example.inf.smarthome3.model.modelImpl.RealTimeListener;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.FindListener;

import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by INF on 2017/3/14.
 */

public interface BmobModel {

    void updataStateToBmob(BmobObject bmobObject , UpdateListener updateListener);//通知bmob 家具的灯关了 （即 修改云端数据库）

    void queryTableAll(FindListener listener);

    void addRowsUpdateListen( RealTimeListener listener,String ... objectId);

    void removeRowUpdateListen(RealTimeListener listener,String objectId);
}
