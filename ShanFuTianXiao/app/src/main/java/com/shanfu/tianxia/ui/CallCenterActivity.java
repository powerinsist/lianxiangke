package com.shanfu.tianxia.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jxccp.im.callback.JXUserSelfQueueListener;
import com.jxccp.im.chat.manager.JXImManager;
import com.jxccp.im.util.log.JXLog;
import com.shanfu.tianxia.R;

public class CallCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);
    }

}