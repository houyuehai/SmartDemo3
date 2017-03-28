package com.example.smarthomeforphone.presenter;

import com.example.smarthomeforphone.bean.ErrorInfo;
import com.example.smarthomeforphone.bean.HomeStateBean;
import com.example.smarthomeforphone.model.BmobModel;
import com.example.smarthomeforphone.model.BmobModelImpl;
import com.example.smarthomeforphone.utils.Config;
import com.example.smarthomeforphone.view.RefreshStateView;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by INF on 2017/3/16.
 */

public class RefreshDataPresenterImpl implements RefreshDataPresenter {
    RefreshStateView refreshStateView;
    BmobModel bmobModel ;

    public RefreshDataPresenterImpl(RefreshStateView view) {
        refreshStateView = view;
        bmobModel = new BmobModelImpl();
    }

    @Override
    public void toGetHomeState() {
        bmobModel.findOneObjectOnBmob(Config.HOME_STATE_RECORD_ID, new QueryListener<HomeStateBean>() {
            @Override
            public void done(HomeStateBean homeStateBean, BmobException e) {
                if (e == null){
                    refreshStateView.setHomeState(homeStateBean);
                }else {
                    EventBus.getDefault().post(new ErrorInfo("查询失败"));
                }
            }
        });



    }
}
