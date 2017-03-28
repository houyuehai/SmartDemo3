package com.example.inf.smarthome3.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.inf.smarthome3.R;
import com.example.inf.smarthome3.bean.ErrorInfo;
import com.example.inf.smarthome3.bean.UserBean;
import com.example.inf.smarthome3.customView.ProgressRegulator;
import com.example.inf.smarthome3.databinding.Mainbinding;
import com.example.inf.smarthome3.model.HomeNative;
import com.example.inf.smarthome3.viewmodel.HomeState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity {
    MyApp myApp ;


    ProgressRegulator progressRegulator ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_main);
        Mainbinding mainbinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        myApp = (MyApp) getApplication();
        mainbinding.setHomeState(myApp.homeState);
        EventBus.getDefault().register(this);
        //initView();
    }

    private void initView() {
        progressRegulator = (ProgressRegulator) findViewById(R.id.progress_currain);
        progressRegulator.setProgressActionListener(new ProgressRegulator.ProgressActionListener() {
            @Override
            public void onProgressChange(int progress) {
                myApp.homeState.curtainData = progress;
            }

            @Override
            public void onProgressActionDown() {

            }

            @Override
            public void onProgressActionUp() {

            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(ErrorInfo errorInfo){
        Toast.makeText(this,errorInfo.errorMsg,Toast.LENGTH_LONG).show();
    }
}
