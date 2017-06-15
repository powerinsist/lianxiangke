package com.shanfu.tianxia.utils;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shanfu.tianxia.R;


public class SetBnakImage {
    private int nameid;
    ImageView bankmanage_im;
    ImageView bank_bg;

//    int[] drawables = {R.mipmap.wallet_bankcard_ccb, R.mipmap.wallet_bankcard_cmbc, R.mipmap.wallet_bankcard_agricultural, R.mipmap.wallet_bankcard_communications,
//            R.mipmap.wallet_bankcard_ceb, R.mipmap.wallet_bankcard_zhongxin, R.mipmap.wallet_bankcard_hxb, R.mipmap.wallet_bankcard_pufa, R.mipmap.wallet_bankcard_pingan,
//            R.mipmap.bgbank, R.mipmap.bohaib, R.mipmap.gfbank, R.mipmap.gsbank, R.mipmap.msbank, R.mipmap.yzbank, R.mipmap.zgbank,
//            R.mipmap.hbbank, R.mipmap.hsbank, R.mipmap.hzbank, R.mipmap.nsbank, R.mipmap.shbank, R.mipmap.xybank};
//    String[] strings = {"中国建设银行", "中国招商银行", "中国农业银行", "中国交通银行", "中国光大银行", "中信银行", "华夏银行", "浦发银行", "平安银行"
//            , "北京银行", "渤海银行", "广发银行", "中国工商银行", "中国民生银行", "中国邮政储蓄", "中国银行", "河北银行", "徽商银行", "杭州银行", "北京农商银行",
//            "上海银行", "兴业银行"};
    public void set(String bank_code,ImageView bankmanage_im){
        //建设
        if ("01050000".equals(bank_code)) {
            nameid = 1;
            //招商
        } else if ("03080000".equals(bank_code)) {
            nameid = 2;
            //农业
        } else if ("01030000".equals(bank_code)) {
            nameid = 3;
            //交通
        } else if ("03010000".equals(bank_code)) {
            nameid = 4;
            //光大
        } else if ("03030000".equals(bank_code)) {
            nameid = 5;
            //中信
        } else if ("03020000".equals(bank_code)) {
            nameid = 6;
            //华夏
        } else if ("03040000".equals(bank_code)) {
            nameid = 7;
            //浦发
        } else if ("03100000".equals(bank_code)) {
            nameid = 8;
            //平安
        } else if ("03070000".equals(bank_code)) {
            nameid = 9;
            //北京
        } else if ("04031000".equals(bank_code)) {
            nameid = 10;
            //渤海
        } else if ("03170000".equals(bank_code)) {
            nameid = 11;
            //广发
        } else if ("03060000".equals(bank_code)) {
            nameid = 12;
            //工商
        } else if ("01020000".equals(bank_code)) {
            nameid = 13;
            //民生
        } else if ("03050000".equals(bank_code)) {
            nameid = 14;
            //邮政
        } else if ("01000000".equals(bank_code)) {
            nameid = 15;
            //中国银行
        } else if ("01040000".equals(bank_code)) {
            nameid = 16;
            //河北银行
        } else if ("64221210".equals(bank_code)) {
            nameid = 17;
            //徽商
        } else if ("04403600".equals(bank_code)) {
            nameid = 18;
            //杭州
        } else if ("04233310".equals(bank_code)) {
            nameid = 19;
            //北京农商银行
        } else if ("14181000".equals(bank_code)) {
            nameid = 20;
            //上海
        } else if ("04012900".equals(bank_code)) {
            nameid = 21;
            //兴业
        } else if ("03090000".equals(bank_code)) {
            nameid = 22;
        }
        switch (nameid) {

            case 22:
                bankmanage_im.setImageResource(R.mipmap.xybank);

                break;
            case 21:
                bankmanage_im.setImageResource(R.mipmap.shbank);

                break;
            case 20:
                bankmanage_im.setImageResource(R.mipmap.nsbank);

                break;
            case 19:
                bankmanage_im.setImageResource(R.mipmap.hzbank);

                break;
            case 18:
                bankmanage_im.setImageResource(R.mipmap.hsbank);

                break;
            case 17:
                bankmanage_im.setImageResource(R.mipmap.hbbank);

                break;
            case 16:
                bankmanage_im.setImageResource(R.mipmap.zgbank);

                break;

            case 15:
                bankmanage_im.setImageResource(R.mipmap.yzbank);

                break;

            case 14:
                bankmanage_im.setImageResource(R.mipmap.msbank);

                break;

            case 13:
                bankmanage_im.setImageResource(R.mipmap.gsbank);

                break;
            case 12:
                bankmanage_im.setImageResource(R.mipmap.gfbank);

                break;

            case 11:
                bankmanage_im.setImageResource(R.mipmap.bohaib);

                break;

            case 10:
                bankmanage_im.setImageResource(R.mipmap.bgbank);
                break;

            case 9:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_pingan);
                break;
            case 8:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_pufa);
                break;

            case 7:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_hxb);
                break;

            case 6:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_zhongxin);
                break;

            case 5:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_ceb);

                break;

            case 4:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_communications);

                break;

            case 3:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_agricultural);

                break;

            case 2:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_cmbc);
                break;

            case 1:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_ccb);
                break;

            default:
                bankmanage_im.setImageResource(R.mipmap.moren);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_ccb_bg);
                break;
        }
    }
    public void set(String bank_code, ImageView bankmanage_im, LinearLayout bank_bg) {
        //建设
        if ("01050000".equals(bank_code)) {
            nameid = 1;
            //招商
        } else if ("03080000".equals(bank_code)) {
            nameid = 2;
            //农业
        } else if ("01030000".equals(bank_code)) {
            nameid = 3;
            //交通
        } else if ("03010000".equals(bank_code)) {
            nameid = 4;
            //光大
        } else if ("03030000".equals(bank_code)) {
            nameid = 5;
            //中信
        } else if ("03020000".equals(bank_code)) {
            nameid = 6;
            //华夏
        } else if ("03040000".equals(bank_code)) {
            nameid = 7;
            //浦发
        } else if ("03100000".equals(bank_code)) {
            nameid = 8;
            //平安
        } else if ("03070000".equals(bank_code)) {
            nameid = 9;
            //北京
        } else if ("04031000".equals(bank_code)) {
            nameid = 10;
            //渤海
        } else if ("03170000".equals(bank_code)) {
            nameid = 11;
            //广发
        } else if ("03060000".equals(bank_code)) {
            nameid = 12;
            //工商
        } else if ("01020000".equals(bank_code)) {
            nameid = 13;
            //民生
        } else if ("03050000".equals(bank_code)) {
            nameid = 14;
            //邮政
        } else if ("01000000".equals(bank_code)) {
            nameid = 15;
            //中国银行
        } else if ("01040000".equals(bank_code)) {
            nameid = 16;
            //河北银行
        } else if ("64221210".equals(bank_code)) {
            nameid = 17;
            //徽商
        } else if ("04403600".equals(bank_code)) {
            nameid = 18;
            //杭州
        } else if ("04233310".equals(bank_code)) {
            nameid = 19;
            //北京农商银行
        } else if ("14181000".equals(bank_code)) {
            nameid = 20;
            //上海
        } else if ("04012900".equals(bank_code)) {
            nameid = 21;
            //兴业
        } else if ("03090000".equals(bank_code)) {
            nameid = 22;
        }
        switch (nameid) {

            case 22:
                bankmanage_im.setImageResource(R.mipmap.xybank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_pufa_bg);

                break;
            case 21:
                bankmanage_im.setImageResource(R.mipmap.shbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_ccb_bg);

                break;
            case 20:
                bankmanage_im.setImageResource(R.mipmap.nsbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);

                break;
            case 19:
                bankmanage_im.setImageResource(R.mipmap.hzbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_ccb_bg);

                break;
            case 18:
                bankmanage_im.setImageResource(R.mipmap.hsbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);

                break;
            case 17:
                bankmanage_im.setImageResource(R.mipmap.hbbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_pufa_bg);

                break;
            case 16:
                bankmanage_im.setImageResource(R.mipmap.zgbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);

                break;

            case 15:
                bankmanage_im.setImageResource(R.mipmap.yzbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_agricultural_bg);

                break;

            case 14:
                bankmanage_im.setImageResource(R.mipmap.msbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_agricultural_bg);

                break;

            case 13:
                bankmanage_im.setImageResource(R.mipmap.gsbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);

                break;
            case 12:
                bankmanage_im.setImageResource(R.mipmap.gfbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);

                break;

            case 11:
                bankmanage_im.setImageResource(R.mipmap.bohaib);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_pufa_bg);

                break;

            case 10:
                bankmanage_im.setImageResource(R.mipmap.bgbank);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);
                break;

            case 9:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_pingan);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_pingan_bg);
                break;
            case 8:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_pufa);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_pufa_bg);
                break;

            case 7:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_hxb);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_hxb_bg);
                break;

            case 6:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_zhongxin);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_zhongxin_bg);
                break;

            case 5:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_ceb);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_ceb_bg);

                break;

            case 4:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_communications);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_communications_bg);

                break;

            case 3:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_agricultural);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_agricultural_bg);

                break;

            case 2:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_cmbc);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_cmbc_bg);
                break;

            case 1:
                bankmanage_im.setImageResource(R.mipmap.wallet_bankcard_ccb);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_ccb_bg);
                break;

            default:
                bankmanage_im.setImageResource(R.mipmap.moren);
                bank_bg.setBackgroundResource(R.mipmap.wallet_bankcard_ccb_bg);
                break;
        }

    }
}
