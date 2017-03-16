package com.example.inf.smarthome3.model.modelImpl;



import android.util.Log;

import com.example.inf.smarthome3.bean.UserBean;
import com.example.inf.smarthome3.model.model.BmobModel;

import com.example.inf.smarthome3.viewmodel.HomeState;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;



import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
    public void updataStateToBmob(BmobObject bmobObject , final UpdateListener updateListener) {
        if (homeState.allowUploadState){
            bmobObject.update(updateListener);
        }

    }

    @Override
    public void queryTableAll(FindListener listener ) {
        BmobQuery query = new BmobQuery();
        //query.setLimit(10);
        query.findObjects(listener);

    }

    @Override
    public void addRowsUpdateListen(final RealTimeListener listener, final String ... objectIds) {
            String tableName = listener.getmClass().getSimpleName();//反射获取类名，即bmob表名

            bmobRealTimeData = new BmobRealTimeData();
            bmobRealTimeData.start(new ValueEventListener() {
                @Override
                public void onConnectCompleted(Exception e) {
                    if (e == null) {
                        String tableName = listener.getmClass().getSimpleName();
                        for(String id : objectIds){
                            bmobRealTimeData.subRowUpdate(tableName,id);
                            Log.i("aaa",tableName+id);
                        }
                        listener.onSuccess();
                    } else {
                        listener.onFailed(e);
                    }
                }

                @Override
                public void onDataChange(JSONObject jsonObject) {
                    Gson mGson = new Gson();
                    Log.i("aaa", "onDataChange: "+jsonObject.toString());
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
    public void removeRowUpdateListen(RealTimeListener listener,String objectId) {
        Log.i("test", "removeRowUpdateListen: ");
        if(bmobRealTimeData !=null && bmobRealTimeData.isConnected()){
            bmobRealTimeData.unsubRowUpdate(listener.getmClass().getSimpleName(),objectId);
            Log.i("test", "removeRowUpdateListen: ");
        }

    }


}
