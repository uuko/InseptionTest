package com.example.inseptiontest.ui.main;

import androidx.databinding.Bindable;

import java.io.Serializable;

public class MainModel implements Serializable {

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    private boolean loginStatus;
    private String routeCodeData="";
    private String deviceNumberData="";
    private boolean deviceWheatherInBefore=true;

    public boolean isDeviceWheatherInBefore() {
        return deviceWheatherInBefore;
    }

    public void setDeviceWheatherInBefore(boolean deviceWheatherInBefore) {
        this.deviceWheatherInBefore = deviceWheatherInBefore;
    }

    public String getRouteCodeData() {
        return routeCodeData;
    }

    public void setRouteCodeData(String routeCodeData) {
        this.routeCodeData = routeCodeData;
    }

    public String getDeviceNumberData() {
        return deviceNumberData;
    }

    public void setDeviceNumberData(String deviceNumberData) {
        this.deviceNumberData = deviceNumberData;
    }


}
