package com.example.inf.smarthome3.model.model;


/**
 * Created by INF on 2017/3/10.
 * 这里是HomeModelImlp的描述
 */

public interface HomeModel {



    void closeLightWithJNI(int type);  //guan 灯
    void openLightWithJNI(int type);//kai灯

    void acOperate(int type); //0 1 4

    void realyOperate(int type);  //继电器 开门

    void curtainOperate(int type);


    void closeDev();//继电器 关门



}
