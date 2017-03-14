package com.example.inf.smarthome3.model.modelImpl;


import com.example.inf.smarthome3.model.model.BmobModel;

import com.example.inf.smarthome3.viewmodel.HomeState;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by INF on 2017/3/14.
 */

public class BmobModelImpl implements BmobModel {
    HomeState homeState ;
    BmobRealTimeData bmobRealTimeData ;

    public BmobModelImpl( HomeState homeState){
        this.homeState = homeState;
    }

    @Override
    public void saveStateToBmob(BmobObject bmobObject , SaveListener savelistener) {
        if (homeState.allowUploadState)
        bmobObject.save(savelistener);
    }

    @Override
    public void startHomeStateListen(final RealTimeListener listener) {

        bmobRealTimeData = new BmobRealTimeData();
        bmobRealTimeData.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                if (e==null){
                    String tableName = listener.getmClass().getSimpleName();
                    bmobRealTimeData.subTableUpdate(tableName);
                    listener.onSuccess();
                }else {
                    listener.onFailed(e);
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                Gson mGson = new Gson();
                try {
                    String data = jsonObject.getJSONObject("data").toString();
                    Object obj = mGson.fromJson(data, listener.getType());
                    listener.onDataChange(obj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void stopHomeStateListen(RealTimeListener listener) {

        bmobRealTimeData.unsubTableDelete(listener.getmClass().getSimpleName());
    }


}
