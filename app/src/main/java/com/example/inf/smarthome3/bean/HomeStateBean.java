package com.example.inf.smarthome3.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by INF on 2017/3/13.
 *
 */

public class HomeStateBean extends BmobObject {

    private  Boolean roomLight;
    private Boolean parlour1Light;
    private Boolean door;
    private Integer acState;

    private Integer curtainData;


     public Boolean getRoomLight() {
        return roomLight;
    }
     public void setRoomLight(Boolean roomLight) {
        this.roomLight = roomLight;
    }

    public Boolean getParlour1Light() {
        return parlour1Light;
    }

    public void setParlour1Light(Boolean parlour1Light) {
        this.parlour1Light = parlour1Light;
    }

    public Boolean getDoor() {
        return door;
    }

    public void setDoor(Boolean door) {
        this.door = door;
    }

    public Integer getAcState() {
        return acState;
    }

    public void setAcState(Integer acState) {
        this.acState = acState;
    }
    public Integer getCurtainData() {
        return curtainData;
    }

    public void setCurtainData(Integer curtainData) {
        this.curtainData = curtainData;
    }

    public HomeStateBean buildRoomLight(boolean flag) {
        this.roomLight = flag;
        return this;
    }
    public HomeStateBean buildParlour1Light(boolean flag) {
        parlour1Light = flag;
        return this;
    }
    public HomeStateBean buildDoor(boolean flag) {
        door = flag;
        return this;
    }
    public HomeStateBean buildAcState(int flag) {
        acState = flag;
        return this;
    }
    public HomeStateBean buildCurtainData(int data) {
        curtainData = data;
        return this;
    }

}
