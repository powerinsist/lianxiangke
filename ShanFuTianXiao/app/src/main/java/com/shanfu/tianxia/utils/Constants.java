package com.shanfu.tianxia.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 网络请求常量类
 */
public class Constants {

	/**
	 * 设备的sn码
	 * */
	private static String sn = "";
	/**
	 * 网络请求的url
	 */
	public static final String URL = "http://192.168.0.104:8080/VolleyTest/servlet/TestServlet";
	public static final String APPKEY = "888";
	public static final int RTIMES = 1;
	public static final String SECRET = "567745674567544";

	public static final int QUEST_CODE_LOCTION =1;
	public static final int QUEST_CODE_CAMERA =2;
	public static final int QUEST_CODE_SEND_SMS =3;
	public static final int QUEST_CODE_ALL =4;
	public static final int QUEST_CODE_CALL_PHONE =5;



	/**
	 * 获取设备的sn码
	 * */
	public static String getSN(Context context) {
		if (sn.equals("")) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			sn = tm.getSimSerialNumber();
		}
		return sn;
	}
}
