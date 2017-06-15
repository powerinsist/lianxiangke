package com.shanfu.tianxia.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import com.shanfu.tianxia.R;

/**
 * Created by xuchenchen on 2017/6/9.
 */

public class MyDialogUtils extends AlertDialog {

    Context mContext;

    public MyDialogUtils(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager mWmManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.gravity=Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;
//        layoutParams.gravity = Gravity.CENTER;
        layoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        int mScreenHeight = mWmManager.getDefaultDisplay().getHeight();
        int mScreenWidth = mWmManager.getDefaultDisplay().getWidth();
        layoutParams.x = mScreenWidth/4;
        layoutParams.y = mScreenHeight/4;

        layoutParams.width= LayoutParams.MATCH_PARENT;
        layoutParams.height= LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
}
 /*dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

lp.x = (int) (mScreenWidth * 0.15); // 新位置X坐标
lp.y = (int) (mScreenHeight * 0.1); // 新位置Y坐标
lp.width = (int) (mScreenWidth * 0.7); // 宽度
lp.height = (int) (mScreenHeight * 0.8); // 高度*/