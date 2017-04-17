package com.shanfu.tianxia.listener;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.lzy.okgo.request.BaseRequest;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.view.LoadingDialog;

/**
 * @author tardis_tao 
 * @version 创建时间：2016-11-14 上午11:21:08
 * @describe
 */
public abstract class DialogCallback<T> extends JsonCallback<T> {
//	 private ProgressDialog dialog;
	 private LoadingDialog dialog;

	    private void initDialog(Activity activity) {
//	        dialog = new ProgressDialog(activity);
//	        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	        dialog.setCanceledOnTouchOutside(false);
//	        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//	        dialog.setMessage("请求网络中...");
	    	dialog = new LoadingDialog(activity, "", R.style.dialog);
	    }

	    public DialogCallback(Activity activity) {
	        super();
	        initDialog(activity);
	    }

	    @Override
	    public void onBefore(BaseRequest request) {
	        super.onBefore(request);
	        //网络请求前显示对话框
	        if (dialog != null && !dialog.isShowing()) {
	            dialog.show();
	        }
	    }

	    @Override
	    public void onAfter(@Nullable T t, @Nullable Exception e) {
	        super.onAfter(t, e);
	        //网络请求结束后关闭对话框
	        if (dialog != null && dialog.isShowing()) {
	            dialog.dismiss();
	        }
	    }
}
