package com.shanfu.tianxia.utils;

import android.content.Context;
import android.location.LocationManager;

import java.util.List;

/**
 * Created by qing on 2017/3/1.
 * 获取手机的经纬度
 */
public class GetLocation {
    private LocationManager locationManager;
    private Context mContext;
    private String locationProvider =null;

    public GetLocation(Context mContext){
        this.mContext = mContext;
        //获取地理位置管理器
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        //  获取所有可用的位置提供器
        getAllLocation();
    }
    public String  getAllLocation(){
        List<String> providers = locationManager.getProviders(true);
        if(providers.contains(LocationManager.GPS_PROVIDER)){
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        }else if(providers.contains(LocationManager.NETWORK_PROVIDER)){
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        return locationProvider;
    }
}
