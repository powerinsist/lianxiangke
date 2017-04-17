package com.shanfu.tianxia.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.shanfu.tianxia.appconfig.AppManager;


/**
 * @author tardis_tao
 * @version 创建时间：2016-11-4 上午9:36:34
 * @describe
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
	private long exitTime = 0;
	private static final String TAG = "BaseFragmentActivity";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		//ButterKnife.bind(this);
		//ButterKnife.bind(this);

		//initView();

		// 修改状态栏颜色，4.4+生效
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// setTranslucentStatus();
		// }
		// SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// tintManager.setStatusBarTintEnabled(true);
		// tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色

	}

	/**
	 * 初始化控件
	 */
	//protected abstract void initView();
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
		OkGo.getInstance().cancelTag(this);
	}


	@TargetApi(19)
	protected void setTranslucentStatus() {
		Window window = getWindow();
		// Translucent status bar
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	}


	@Override
	protected void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
		}
	}
	public boolean checkPermission(@NonNull String permission) {
		return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
	}

	public void showToast(String toastText) {
		Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);
	}
}
