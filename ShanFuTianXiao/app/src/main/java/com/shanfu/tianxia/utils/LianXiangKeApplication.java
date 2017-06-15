package com.shanfu.tianxia.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by xuchenchen on 2017/5/11.
 */

public class LianXiangKeApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
