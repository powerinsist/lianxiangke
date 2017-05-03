package com.shanfu.tianxia.utils;

import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 林枕祥 on 2015/10/13.
 *
 * 倒计时的工具类
 */
public class TimeCountUtil extends CountDownTimer {
    private TextView v;
    private RelativeLayout relativeLayout;

    public TimeCountUtil(long millisInFuture, long countDownInterval, TextView v,RelativeLayout relativeLayout) {

        super(millisInFuture, countDownInterval);
        this.relativeLayout = relativeLayout;
        this.v = v;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        v.setText("重新验证");
        relativeLayout.setClickable(true);
        v.setClickable(true);
    }
    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        relativeLayout.setClickable(false);
        v.setClickable(false);
        v.setText("剩余"+millisUntilFinished / 1000 + "秒");
    }

}
