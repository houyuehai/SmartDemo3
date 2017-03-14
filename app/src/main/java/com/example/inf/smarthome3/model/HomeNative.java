package com.example.inf.smarthome3.model;

import android.os.Handler;
import android.os.Message;

/**
 * Created by INF on 2017/3/10.
 */

public class HomeNative {
    public static final int WHAT_MSG = 1;
    public static Handler handler;

    static{
        System.loadLibrary("homeNative-lib");
    }

    public static native void openACDev();
    public static native void operateACDev(int op); //0 1 4 9 暂停 正 反 停止
    public static native void closeACDev();

    public static native void openCurtainDev();
    public static native void changeCurtainDev(int state ,int angle); // 1 3 正反转 angle是角度
    public static native void closeCurtainDev();

    public static native void  openDoorDev();
    public static native void  operateDoor(int op); // 传入0开门，1关门
    public static native void  closeDoorDev();


    public static native void openGasDev();
    public static native int operateGasDev(); //烟雾较浓 返回非-1
    public static native void closeGasDev();

    public static native void openInfraredDev();
    public static native int nowInfrared();   //-1  阻断发生时时返回1
    public static native void closeInfraredDev();

    public static native void openLightDev();
    // lightOp(打开或关闭， 房间灯或客厅灯);
    public static native void lightOp(int openOrClose, int type);// 0开 1关  0房间等 1 客厅灯
    public static native void closeLightDev();

    public static native void openLightSenseDev();
    public static native int nowLightSense();   //返回当前光感 值越大越暗
    public static native void closeLightSenseDev();

    // 打开设备
    public static native void openDev();
    // 开始启动获取数据
    public static native void readData(); //读取温度数据 ，返回在getDataCallBackListener中捕获
    // 关闭设备
    public static native void closeDev();

    // 在.c文件中获取数据，然后回调到这里，再通过handler将数据传递到Activity
    public static void getDataCallBackListener(int tempz,int humidiyz){
        int temp = tempz;      // 温度
        int humidiy = humidiyz;// 湿度
        Message message = Message.obtain();
        message.what = WHAT_MSG;
        message.arg1 = tempz;
        message.arg2 = humidiyz;
        //if (handler!=null)
        handler.sendMessage(message); // 将数据发送到handler
    }


}
