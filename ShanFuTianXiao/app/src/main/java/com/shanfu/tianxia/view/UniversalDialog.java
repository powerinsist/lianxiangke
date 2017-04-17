package com.shanfu.tianxia.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;


/**
 * 通用dialog,只有一个确定按钮用来消除dialog,还有一个textview用以展示提示信息
 * @author Administrator
 *
 */
public class UniversalDialog {

	private Context context;
	private TextView dialog_title;
	private RelativeLayout dialg_ok;
    public UniversalDialog(Context context, String title) {
        this.context = context;
        
    }
    public  void showMyDialog(String title){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_my_dialog, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// builder.setView(view);

		final AlertDialog dialog = builder.create();
		//dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.show();
		Window m = dialog.getWindow();
		WindowManager.LayoutParams d = m.getAttributes(); // 获取屏幕宽、高用
		Display dis = m.getWindowManager().getDefaultDisplay(); // 获取屏幕宽、高用
		d.height = (int) (dis.getHeight() * 0.2); // 高度设置为屏幕的0.6
		d.width = (int) (dis.getWidth() * 0.8); // 宽度设置为屏幕的0.65
		m.setAttributes(d);

		// 这一行最主要
		dialog.getWindow().setContentView(view);
		
		dialog_title = (TextView) view.findViewById(R.id.dialog_title);
		dialg_ok = (RelativeLayout) view.findViewById(R.id.dialg_ok);
		
		dialog_title.setText(title);
		dialg_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				
			}
		});
    }
}
