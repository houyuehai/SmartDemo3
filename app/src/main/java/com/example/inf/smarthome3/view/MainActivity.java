package com.example.inf.smarthome3.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.inf.smarthome3.R;
import com.example.inf.smarthome3.bean.UserBean;
import com.example.inf.smarthome3.databinding.Mainbinding;
import com.example.inf.smarthome3.model.HomeNative;
import com.example.inf.smarthome3.viewmodel.HomeState;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {
    MyApp myApp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);

        Mainbinding mainbinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        myApp = (MyApp) getApplication();
        mainbinding.setHomeState(myApp.homeState);


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
}
