package com.example.smarthomeforphone.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by INF on 2017/3/13.
 */

public class HomeStateBean extends BmobObject {

     private  Boolean roomLight;
     public Boolean getRoomLight() {
        return roomLight;
    }
     public void setRoomLight(Boolean roomLight) {
        this.roomLight = roomLight;
    }


    public HomeStateBean buildRoomLight(boolean flag) {
        this.roomLight = flag;
        return this;
    }
}
