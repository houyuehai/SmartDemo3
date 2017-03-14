package com.example.inf.smarthome3.view;

import android.app.Application;

import com.example.inf.smarthome3.bean.HomeStateBean;
import com.example.inf.smarthome3.viewmodel.HomeState;

import cn.bmob.v3.Bmob;

/**
 * Created by INF on 2017/3/13.
 */

public class MyApp extends Application {
    public HomeState homeState ;
    @Override
    public void onCreate() {
        super.onCreate();
        homeState = new HomeState(this);
        Bmob.initialize(this,"b0a1ac88a07f994e03cf6c082b77b875");//初始化Bmob
    }
}
