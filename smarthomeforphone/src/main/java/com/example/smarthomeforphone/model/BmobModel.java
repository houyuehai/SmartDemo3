package com.example.smarthomeforphone.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by INF on 2017/3/16.
 */

public interface BmobModel {
    void updataStateToBmob(BmobObject bmobObject , UpdateListener updateListener);
    void findOneObjectOnBmob(String objectId, QueryListener listener);
}
