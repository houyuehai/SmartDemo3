package com.example.inf.smarthome3.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.android.databinding.library.baseAdapters.BR;
import com.example.inf.smarthome3.bean.HomeStateBean;
import com.example.inf.smarthome3.model.model.BmobModel;
import com.example.inf.smarthome3.model.model.HomeModel;
import com.example.inf.smarthome3.model.modelImpl.BmobModelImpl;
import com.example.inf.smarthome3.model.modelImpl.HomeModelImpl;
import com.example.inf.smarthome3.model.modelImpl.RealTimeListener;
import com.example.inf.smarthome3.utils.Config;
import com.example.inf.smarthome3.utils.HomeStateUtil;


import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by INF on 2017/3/10.
 * 这里是家具状态的保存Bean类 ，也是板上aPP主界面 Databinding 所绑定的对象
 *
 */

public class HomeState extends BaseObservable {

    static final String TAG = "HomeState";

    public transient HomeModel homemodel;
    public transient BmobModel bmobModel;
    public transient Context   context;

    public HomeState (Context   context){
        homemodel = new HomeModelImpl();//创建HomeModel对象，
        bmobModel = new BmobModelImpl(this);
        this.context = context;
    }

    public boolean openListen  =Config.DOWNLOAD_STADE_TO_BMOB; //是否接受云控制
    public boolean allowUploadState = Config.UPLOAD_STADE_TO_BMOB ; //是上传家居信息

    public boolean roomLight;     //房间灯
    public boolean realy;         //继电器


    @Bindable //在成员变量对应的get方法上 配置@Bindable 才能在BR文件里生成 对应的整形int 类似 我们的R文件
    public boolean getRoomLight() {
        return roomLight;
    }
    public void setRoomLight(boolean roomLight) {
        this.roomLight = roomLight;
        if(roomLight){
            homemodel.openLightWithJNI();

        }else{
            homemodel.closeLightWithJNI();
            }

            //上传数据到bmob
            bmobModel.saveStateToBmob(new HomeStateBean().buildRoomLight(roomLight), new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    }
                );

        //BR.roomLight 是从上面配置了@bindable 编译之后自动生成的
        notifyPropertyChanged(BR.roomLight);
    }


    @Bindable
    public boolean isRealy() {
        return realy;
    }

    public void setRealy(boolean realy) {
        this.realy = realy;
        if(realy){
            homemodel.openRealy();
            }else {
            homemodel.closeRealy();
            }
        notifyPropertyChanged(BR.realy); //
        }



    @Bindable
    public boolean isOpenListen() {
        return openListen;
    }

    public void setOpenListen(boolean openListen) {
        this.openListen = openListen;
        notifyPropertyChanged(BR.openListen);

        if (openListen){
            //bmobModel.saveStateToBmob();
            bmobModel.startHomeStateListen(new RealTimeListener<HomeStateBean>(){
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
            });
        }else {
            bmobModel.stopHomeStateListen(new RealTimeListener<HomeStateBean>() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailed(Exception e) {

                }

                @Override
                public void onDataChange(HomeStateBean obj) {

                }
            });
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



}
