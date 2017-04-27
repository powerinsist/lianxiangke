package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.umeng.socialize.PlatformConfig;


public class SplashActivity extends BaseFragmentActivity {

	public static final String TAG = SplashActivity.class.getSimpleName();

	private ImageView mSplashItem_iv = null;

    private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Constants.SCREEN_DENSITY = metrics.density;
		Constants.SCREEN_HEIGHT = metrics.heightPixels;
		Constants.SCREEN_WIDTH = metrics.widthPixels;

		mHandler = new Handler(getMainLooper());

//		//微信平台的配置
//		PlatformConfig.setWeixin("wx3fca228ffb8ecee1","bd51b52b4ccfbed57f317e92fbd58818");

		initView();
		initData();
	}

	private void initView() {
		mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
	}

	private void initData() {
		// TODO Auto-generated method stub
				Animation translate = AnimationUtils.loadAnimation(this,
						R.anim.splash_loading);
				translate.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						//启动homeactivty，相当于Intent
						//openActivity(MainActivity.class);
						Intent intent = new Intent(SplashActivity.this, MainActivity.class);
						startActivity(intent);
						overridePendingTransition(R.anim.push_left_in,
								R.anim.push_left_out);
						SplashActivity.this.finish();
					}
				});
				mSplashItem_iv.setAnimation(translate);
		
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.itau.jingdong.ui.base.BaseActivity#initView()
	 */
	/*@Override
	protected void initView() {


	}*/

}
