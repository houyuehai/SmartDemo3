package com.example.smarthomeforphone.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.smarthomeforphone.R;
import com.example.smarthomeforphone.bean.HomeStateBean;
import com.example.smarthomeforphone.myIflytek.IflytekService;
import com.example.smarthomeforphone.myIflytek.MyAudioRecord;
import com.example.smarthomeforphone.presenter.LightPresenter;
import com.example.smarthomeforphone.presenter.LightPresenterImpl;
import com.example.smarthomeforphone.presenter.RefreshDataPresenter;
import com.example.smarthomeforphone.presenter.RefreshDataPresenterImpl;


public class MainActivity extends AppCompatActivity implements LightContrlView ,RefreshStateView {
    ImageView imgLight ;
    LightPresenter lightPresenter;
    RefreshDataPresenter refreshDataPresenter;
    Switch yuyinSwitch;
    MyAudioRecord myAudioRecord;
    IflytekService iflytekService;
    boolean flag;


    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        lightPresenter = new LightPresenterImpl(this);
        refreshDataPresenter = new RefreshDataPresenterImpl(this);

        myAudioRecord = new MyAudioRecord();
        iflytekService = new IflytekService(this);

    }

    private void initView() {
        imgLight = (ImageView) findViewById(R.id.img_light);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimaryDark,R.color.colorPrimary);
        yuyinSwitch = (Switch) findViewById(R.id.myswitch);
        yuyinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    iflytekService.openListen(new IflytekService.CallBack() {
                        @Override
                        public void onError(Exception e) {
                            Log.i("aaa", "onError: ");
                        }

                        @Override
                        public void onSuccess(String result) {
                            if("打开房间灯".equals(result)){
                                lightPresenter.lightContrl(true);
                            }else if("关闭房间灯".equals(result)){
                                lightPresenter.lightContrl(false);
                            }
                            Log.i("aaa", "onSuccess: "+result);
                        }
                    });
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDataPresenter.toGetHomeState();
            }
        });

        imgLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightPresenter.lightContrl(flag);
                flag = !flag;

            }
        });
    }

    @Override
    public void img_LightSwitvh(boolean flag) {
        if (flag){
            imgLight.setImageResource(R.drawable.appliance_control_lamp_open);
        }else {
            imgLight.setImageResource(R.drawable.appliance_control_lamp_close);
        }
    }


    @Override
    public void setHomeState(HomeStateBean homeState) {
        swipeRefreshLayout.setRefreshing(false);
        if (homeState.getRoomLight()){
            imgLight.setImageResource(R.drawable.appliance_control_lamp_open);
        }else {
            imgLight.setImageResource(R.drawable.appliance_control_lamp_close);
        }
    }
}
