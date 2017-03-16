package com.example.inf.smarthome3.utils;


import com.example.inf.smarthome3.bean.HomeStateBean;
import com.example.inf.smarthome3.viewmodel.HomeState;

public class HomeStateUtil {
    //同化状态
    public static void  assimilation(HomeState homeState, HomeStateBean homeStateBean){
        //房间灯
        if(homeState.getRoomLight()!= homeStateBean.getRoomLight()) {
            homeState.setRoomLight(homeStateBean.getRoomLight());
        }
        if(homeState.isParlour1Light()!= homeStateBean.getParlour1Light()) {
            homeState.setParlour1Light(homeStateBean.getParlour1Light());
        }
        if(homeState.getAcState()!= homeStateBean.getAcState()) {
            homeState.setAcState(homeStateBean.getAcState());
        }

    }

    public static void  assimilation( HomeStateBean homeStateBean,HomeState homeState){
        //房间灯
        if(homeStateBean.getRoomLight()!= homeState.getRoomLight()) {
            homeStateBean.setRoomLight(homeState.getRoomLight());
        }
    }
}
