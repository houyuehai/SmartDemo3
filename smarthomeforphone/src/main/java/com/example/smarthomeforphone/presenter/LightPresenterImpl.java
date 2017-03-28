package com.example.smarthomeforphone.presenter;

import android.util.Log;

import com.example.smarthomeforphone.bean.HomeStateBean;
import com.example.smarthomeforphone.model.BmobModel;
import com.example.smarthomeforphone.model.BmobModelImpl;
import com.example.smarthomeforphone.utils.Config;
import com.example.smarthomeforphone.view.LightContrlView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by INF on 2017/3/16.
 */

public class LightPresenterImpl implements LightPresenter {
    BmobModel bmobModel;
    LightContrlView view;

    public LightPresenterImpl(LightContrlView view) {
        this.view = view;
        bmobModel = new BmobModelImpl();
    }

    @Override
    public void lightContrl(boolean flag) {
        view.img_LightSwitvh(flag);
        HomeStateBean homeStateBean = new HomeStateBean();
        homeStateBean.setRoomLight(flag);
        homeStateBean.setObjectId(Config.HOME_STATE_RECORD_ID);
        bmobModel.updataStateToBmob(homeStateBean, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.i("aaa", "done: ");
            }
        });
    }
}
