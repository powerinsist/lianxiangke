package com.shanfu.tianxia.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.view.gifview.GifView;


/**
 * loading Dialog
 */
public class LoadingDialog extends Dialog {
    private Handler handler = new Handler();
    private ImageView iv;
    private TextView tv;
    private Animation ad;
    private String text;
    private GifView mGifView;
    

    /**
     * 设定Loading框的文字显示与主题样式
     * 
     * @param context
     * @param msg
     */
    public LoadingDialog(Context context, String msg) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        text = msg;
    }

    /**
     * 设定Loading框的文字显示与主题样式
     * 
     * @param context
     * @param msg
     * @param theme
     */
    public LoadingDialog(Context context, String msg, int theme) {
        super(context, theme);
        text = msg;
    }

    /**
     * 设定Loading框的文字显示与主题样式
     * 
     * @param context
     * @param msgId
     * @param theme
     */
    public LoadingDialog(Context context, int msgId, int theme) {
        super(context, theme);
        text = context.getResources().getString(msgId);
    }

    public LoadingDialog(Context context, int msgId) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        text = context.getResources().getString(msgId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_loading);
//        iv = (ImageView) findViewById(R.id.tips_icon);
//        tv = (TextView) findViewById(R.id.tips_msg);
//
//        iv.setBackgroundResource(R.drawable.loading01);
//        ad = AnimationUtils.loadAnimation(getContext(), R.anim.bg_loading_dialog);
//        tv.setText(text);
        
        setContentView(R.layout.layout_image_loading);
        mGifView=(GifView) findViewById(R.id.gifView);

        mGifView.setGifResource(R.mipmap.loading);
        mGifView.play();
    }

    @Override
    public void show() {
    	Window mwindow=this.getWindow(); 
		WindowManager.LayoutParams lp=mwindow.getAttributes();
//      lp.x=50;
//		lp.y=50;
		mwindow.setAttributes(lp); 
    	
        super.show();
        
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mGifView.pause();
    }
}
