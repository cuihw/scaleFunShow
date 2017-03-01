package com.example.scalefunshow.bean;

import android.content.Context;
import android.provider.Settings;

import java.security.MessageDigest;

/**
 * Created by cuihuawei on 3/1/2017.
 */

public class LoginBean {
    private String userid;
    private String password;
    private String deviceid;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
}
