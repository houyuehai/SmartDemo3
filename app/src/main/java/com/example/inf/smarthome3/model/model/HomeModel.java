package com.example.inf.smarthome3.model.model;


/**
 * Created by INF on 2017/3/10.
 * 这里是HomeModelImlp的描述
 */

public interface HomeModel {



    void closeLightWithJNI();  //guan 灯
    void openLightWithJNI();//kai灯


    void openRealy();  //继电器 开门
    void closeRealy();//继电器 关门



}
