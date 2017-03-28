package com.example.smarthomeforphone.model;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by INF on 2017/3/16.
 */

public class BmobModelImpl implements BmobModel {
    @Override
    public void updataStateToBmob(BmobObject bmobObject , UpdateListener updateListener) {
            bmobObject.update(updateListener);
    }

    @Override
    public void findOneObjectOnBmob(String objectId, QueryListener listener) {
        BmobQuery query = new BmobQuery();
        query.getObject(objectId,listener);
    }
}
