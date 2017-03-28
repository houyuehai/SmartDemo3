package com.example.inf.smarthome3.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.android.databinding.library.baseAdapters.BR;
import com.dalong.library.listener.OnItemClickListener;
import com.dalong.library.view.LoopRotarySwitchView;
import com.example.inf.smarthome3.R;
import com.example.inf.smarthome3.animator.MyAnimatorUtil;
import com.example.inf.smarthome3.bean.ErrorInfo;
import com.example.inf.smarthome3.bean.HomeStateBean;
import com.example.inf.smarthome3.bean.UserBean;
import com.example.inf.smarthome3.customView.ProgressRegulator;
import com.example.inf.smarthome3.model.model.BmobModel;
import com.example.inf.smarthome3.model.model.HomeModel;
import com.example.inf.smarthome3.model.modelImpl.BmobModelImpl;
import com.example.inf.smarthome3.model.modelImpl.HomeModelImpl;
import com.example.inf.smarthome3.model.modelImpl.RealTimeListener;
import com.example.inf.smarthome3.utils.Config;
import com.example.inf.smarthome3.utils.HomeStateUtil;


import org.greenrobot.eventbus.EventBus;

import java.util.List;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by INF on 2017/3/10.
 * 这里是家具状态的保存Bean类 ，也是板上aPP主界面 Databinding 所绑定的对象
 */



public class HomeState extends BaseObservable {

    static final String TAG = "HomeState";

    public transient HomeModel homemodel;
    public transient BmobModel bmobModel;
    public transient Context   context;
    public transient List<UserBean> users;

    public HomeState (Context   context){
        //创建HomeModel对象，
        homemodel = new HomeModelImpl();
        bmobModel = new BmobModelImpl(this);
        this.context = context;
        //EventBus.getDefault().register(context);
    }

    public boolean openListen  =Config.DOWNLOAD_STADE_TO_BMOB; //是否接受云控制
    public boolean allowUploadState = Config.UPLOAD_STADE_TO_BMOB ; //是上传家居信息

    public boolean roomLight;     //房间灯
    public boolean parlour1Light; //客厅灯
    public boolean door;         //继电器

    public boolean humiditySwitch;
    public boolean infraredSwitch;
    public boolean lightSensorSwitch;
    public boolean gasSwitch;

    public float temperature;
    public float humidity;
    public float infraredSensor;
    public float lightSensor;
    public float gasSensor;

    public int acState;
    public int curtainData;




    @Bindable
    public boolean isOpenListen() {
        return openListen;
    }

    public void setOpenListen(boolean openListen) {
        this.openListen = openListen;
        notifyPropertyChanged(BR.openListen);

        if (openListen){
            bmobModel.addRowsUpdateListen(new RealTimeListener<HomeStateBean>(){
                @Override
                public void onSuccess() {
                    Log.i(TAG, "onSuccess: ");
                }

                @Override
                public void onFailed(Exception e) {
                    Log.i(TAG, "onFailed: "+e.toString());
                }

                @Override
                public void onDataChange(HomeStateBean obj) {

                    Log.i(TAG, "onDataChange: "+obj.getRoomLight());

                    HomeStateUtil.assimilation(HomeState.this,obj);
                }
            },Config.HOME_STATE_RECORD_ID);
        }
        else {
            bmobModel.removeRowUpdateListen(new RealTimeListener<HomeStateBean>() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onDataChange(HomeStateBean obj) {

                }
            },Config.HOME_STATE_RECORD_ID);
        }
    }


    @Bindable
    public boolean isAllowUploadState() {
        return allowUploadState;
    }

    public void setAllowUploadState(boolean allowUploadState) {
        this.allowUploadState = allowUploadState;
        notifyPropertyChanged(BR.allowUploadState);
    }







    @Bindable //在成员变量对应的get方法上 配置@Bindable 才能在BR文件里生成 对应的整形int 类似 我们的R文件
    public boolean getRoomLight() {
        return roomLight;
    }
    public void setRoomLight(boolean roomLight) {
        this.roomLight = roomLight;
        notifyPropertyChanged(BR.roomLight); //BR.roomLight 是从上面配置了@bindable 编译之后自动生成的
        if(roomLight){
            homemodel.openLightWithJNI(1);
        }else{
            homemodel.closeLightWithJNI(1);
            }
            //上传数据到bmob
            HomeStateBean state = new HomeStateBean();
            state.setObjectId(Config.HOME_STATE_RECORD_ID);
            state.setRoomLight(roomLight);
            bmobModel.updataStateToBmob(state, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e!=null){
                        EventBus.getDefault().post(new ErrorInfo("上传数据失败"));
                    }
                }
            });
    }


    @Bindable
    public boolean isParlour1Light() {
        return parlour1Light;
    }
    public void setParlour1Light(boolean parlour1Light) {
        this.parlour1Light = parlour1Light;
        notifyPropertyChanged(BR.parlour1Light);

        if(parlour1Light){
            homemodel.openLightWithJNI(0);
        }else{
            homemodel.closeLightWithJNI(0);
        }

        //上传数据到bmob
        HomeStateBean state = new HomeStateBean();
        state.setObjectId(Config.HOME_STATE_RECORD_ID);
        state.buildParlour1Light(parlour1Light);
        bmobModel.updataStateToBmob(state, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null){
                    EventBus.getDefault().post(new ErrorInfo("上传数据失败"));
                }
            }
        });
    }


    @Bindable
    public boolean isDoor() {
        return door;
    }

    public void setDoor(boolean door) {
        this.door = door;
        if(door){
            homemodel.realyOperate(0);
            }else {
            homemodel.realyOperate(1);
            }
        notifyPropertyChanged(BR.door);


        //上传数据到bmob
        HomeStateBean state = new HomeStateBean();
        state.setObjectId(Config.HOME_STATE_RECORD_ID);
        state.buildDoor(door);
        bmobModel.updataStateToBmob(state, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null){
                    EventBus.getDefault().post(new ErrorInfo("上传数据失败"));
                }
            }
        });
        }


    @Bindable
    public int getAcState() {
        return acState;
    }

    public void setAcState(int acState) {
        this.acState = acState;
        homemodel.acOperate(acState);
        notifyPropertyChanged(BR.acState);

        //上传数据到bmob
        HomeStateBean state = new HomeStateBean();
        state.setObjectId(Config.HOME_STATE_RECORD_ID);
        state.buildAcState(acState);
        bmobModel.updataStateToBmob(state, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e!=null){
                    EventBus.getDefault().post(new ErrorInfo("上传数据失败"));
                }
            }
        });


    }




    public boolean isHumiditySwitch() {
        return humiditySwitch;
    }

    public void setHumiditySwitch(boolean humiditySwitch) {
        this.humiditySwitch = humiditySwitch;
    }

    public boolean isInfraredSwitch() {
        return infraredSwitch;
    }

    public void setInfraredSwitch(boolean infraredSwitch) {
        this.infraredSwitch = infraredSwitch;
    }

    public boolean isLightSensorSwitch() {
        return lightSensorSwitch;
    }

    public void setLightSensorSwitch(boolean lightSensorSwitch) {
        this.lightSensorSwitch = lightSensorSwitch;
    }

    public boolean isGasSwitch() {
        return gasSwitch;
    }

    public void setGasSwitch(boolean gasSwitch) {
        this.gasSwitch = gasSwitch;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getInfraredSensor() {
        return infraredSensor;
    }

    public void setInfraredSensor(float infraredSensor) {
        this.infraredSensor = infraredSensor;
    }

    public float getLightSensor() {
        return lightSensor;
    }

    public void setLightSensor(float lightSensor) {
        this.lightSensor = lightSensor;
    }

    public float getGasSensor() {
        return gasSensor;
    }

    public void setGasSensor(float gasSensor) {
        this.gasSensor = gasSensor;
    }

    public int getCurtainData() {
        return curtainData;
    }

    public void setCurtainData(int curtainData) {
        this.curtainData = curtainData;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.appliance_control_roomLightId :  this.setRoomLight(!roomLight); break;
            case R.id.appliance_control_parlourRoomLightId :this.setParlour1Light(!parlour1Light);break;
            case R.id.appliance_control_ACStopId :setAcState(0);break;
            case R.id.appliance_control_ACCCWId :setAcState(1);break;
            case R.id.appliance_control_ACCLockwiseId :setAcState(4);break;
        }

    }

    @BindingAdapter({"bind:onTouch"})
    public static void setListener(ProgressRegulator view, ProgressRegulator.ProgressActionListener listener) {
        view.setProgressActionListener(listener);
    }
    public ProgressRegulator.ProgressActionListener listener = new ProgressRegulator.ProgressActionListener() {
        @Override
        public void onProgressChange(int progress) {
            HomeState.this.curtainData = progress;
        }

        @Override
        public void onProgressActionDown() {

        }

        @Override
        public void onProgressActionUp() {
            Log.i(TAG, "onProgressActionUp: ");
            HomeStateBean h = new HomeStateBean();
            h.buildCurtainData( HomeState.this.curtainData);
            h.setObjectId(Config.HOME_STATE_RECORD_ID);
            bmobModel.updataStateToBmob(h, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e!=null){
                        EventBus.getDefault().post(new ErrorInfo(e.toString()));
                    }
                }
            });
        }
    };


    @BindingAdapter({"bind:onChildViewScale"})
    public static void setListener(final LoopRotarySwitchView loopView, Object listener) {
        loopView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int item, View view) {
                Log.i(TAG, "onItemClick: "+loopView.getLayoutParams());

            }
        });
    }

}
