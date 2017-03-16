package com.example.inf.smarthome3.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by INF on 2017/3/15.
 */

public class UserBean extends BmobObject{

    public String username;
    public String password;
    public HomeStateBean homeStateBean;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HomeStateBean getHomeStateBean() {
        return homeStateBean;
    }

    public void setHomeStateBean(HomeStateBean homeStateBean) {
        this.homeStateBean = homeStateBean;
    }
}
