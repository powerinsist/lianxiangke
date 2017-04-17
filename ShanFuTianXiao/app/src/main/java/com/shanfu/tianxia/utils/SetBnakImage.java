package com.shanfu.tianxia.utils;

import android.widget.ImageView;

import com.shanfu.tianxia.R;


public class SetBnakImage {
	private int nameid;
	ImageView bankmanage_im;

	public void set(String bankname,ImageView bankmanage_im){
		
		if ("中国银行".equals(bankname)) {
			nameid = 1;

		} else if ("中国建设银行".equals(bankname)) {
			nameid = 2;

		} else if ("中国工商银行".equals(bankname)) {
			nameid = 3;

		} else if ("中国光大银行".equals(bankname)) {
			nameid = 4;

		}else if ("中国农业银行".equals(bankname)) {
			nameid = 5;

		} else if ("平安银行".equals(bankname)) {
			nameid = 6;
			
		}else if ("浦发银行".equals(bankname)) {
			nameid = 7;

		}else if ("招商银行".equals(bankname)) {
			nameid = 8;

		}else if ("兴业银行".equals(bankname)) {
			nameid = 9;

		}else if ("中国邮政储蓄银行".equals(bankname)) {
			nameid = 10;

		}else if ("华夏银行".equals(bankname)) {
			nameid = 11;

		}else if ("中信银行".equals(bankname)) {
			nameid = 12;

		}else if ("广东发展银行".equals(bankname)) {
			nameid = 13;

		}else if ("中国民生银行".equals(bankname)) {
			nameid = 14;

		}else if (bankname.contains("交通银行")) {
			nameid = 15;
			
		}else if("上海银行".equals(bankname)){
			nameid =16;
		}
		switch (nameid) {
		case 16:
			bankmanage_im.setImageResource(R.mipmap.shanghaibank);
			break;
		case 15:
			bankmanage_im.setImageResource(R.mipmap.jiaotong);
			break;
		case 14:
			bankmanage_im.setImageResource(R.mipmap.minsheng);
			break;
		case 13:
			bankmanage_im.setImageResource(R.mipmap.guangfa);
			break;
		case 12:
			bankmanage_im.setImageResource(R.mipmap.zhongxin);
			break;
			
		case 11:
			bankmanage_im.setImageResource(R.mipmap.huaxia);
			break;
			
		case 10:
			bankmanage_im.setImageResource(R.mipmap.youzheng);
			break;
			
		case 9:
			bankmanage_im.setImageResource(R.mipmap.xingye);
			break;
		case 8:
			bankmanage_im.setImageResource(R.mipmap.zhaoshang);
			break;
			
		case 7:
			bankmanage_im.setImageResource(R.mipmap.pufa);
			break;
			
		case 6:
			bankmanage_im.setImageResource(R.mipmap.pingan);
			break;

		case 5:
			bankmanage_im.setImageResource(R.mipmap.nongye);
			break;

		case 4:
			bankmanage_im.setImageResource(R.mipmap.guangda);
			break;
		
		case 3:
			bankmanage_im.setImageResource(R.mipmap.gongshang);
			break;
			
		case 2:
			bankmanage_im.setImageResource(R.mipmap.jianshe);
			break;
			
		case 1:
			bankmanage_im.setImageResource(R.mipmap.china);
			break;
	
		default:
			bankmanage_im.setImageResource(R.mipmap.moren);
			break;
		}
		
	}
}
