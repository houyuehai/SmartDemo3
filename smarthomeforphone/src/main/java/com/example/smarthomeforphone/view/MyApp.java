package com.example.smarthomeforphone.view;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.bmob.v3.Bmob;

/**
 * Created by INF on 2017/3/15.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"b0a1ac88a07f994e03cf6c082b77b875");
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=58ca3a3a");
    }
}
