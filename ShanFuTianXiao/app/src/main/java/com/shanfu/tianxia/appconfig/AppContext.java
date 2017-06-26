package com.shanfu.tianxia.appconfig;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.widget.ImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.listener.PicassoImageLoaderByLzy;
import com.squareup.picasso.Picasso;

import java.util.logging.Level;

/**
 * @author tardis_tao
 * @version 创建时间：2016-11-3 下午8:40:29
 * @describe
 */
public class AppContext extends Application {
	private static AppContext app;

	public AppContext() {
		app = this;
	}

	public static synchronized AppContext getInstance() {
		if (app == null) {
			app = new AppContext();
		}
		return app;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(base);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		registerUncaughtExceptionHandler();
		setOkGoConfig();

		NineGridView.setImageLoader(new PicassoImageLoader());
		//initImagePicker();

//		JXImManager.getInstance().init(getApplicationContext(), "adjynnhzexc0eg#lxk255#10001");
//		JXImManager.getInstance().setDebugMode(true);
//		JXImManager.Login.getInstance().setAutoLogin(false);
	}

	private void initImagePicker(){
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new PicassoImageLoaderByLzy());   //设置图片加载器
		imagePicker.setShowCamera(true);  //显示拍照按钮
		imagePicker.setCrop(true);        //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true); //是否按矩形区域保存
		imagePicker.setSelectLimit(9);    //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
	}
	/** Picasso 加载 */
	private class PicassoImageLoader implements NineGridView.ImageLoader {

		@Override
		public void onDisplayImage(Context context, ImageView imageView, String url) {
			Picasso.with(context).load(url)//
					.placeholder(R.drawable.ic_default_image)//
					.error(R.drawable.ic_default_image)//
					.into(imageView);
		}

		@Override
		public Bitmap getCacheImage(String url) {
			return null;
		}
	}




	/**
	 * https://github.com/tardisTao/okhttp-OkGo
	 */
	private void setOkGoConfig() {
		// 必须调用初始化
		OkGo.init(this);





		try {


			// 以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
			OkGo.getInstance()
			// 打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
					.debug("OkGo", Level.INFO,true)
					// 如果使用默认的 60秒,以下三行也不需要传
					.setConnectTimeout(15000) // 全局的连接超时时间
					.setReadTimeOut(15000) // 全局的读取超时时间
					.setWriteTimeOut(15000) // 全局的写入超时时间
					// 可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍
					//这个是用来设置信任所有的https网站
					//.setCertificates()
					// https://github.com/jeasonlzy/
					.setCacheMode(CacheMode.NO_CACHE);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 注册App异常崩溃处理器
	private void registerUncaughtExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(AppException
				.getAppExceptionHandler());

	}
	

	
	

}
