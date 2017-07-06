package com.shanfu.tianxia.adapter;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.fragment.ZoneCanShuFragment;
import com.shanfu.tianxia.fragment.ZoneDetailsFragment;
import com.shanfu.tianxia.fragment.ZoneShopFragment;
import com.shanfu.tianxia.utils.LianXiangKeApplication;

/**
 * Created by xuchenchen on 2017/5/11.
 */

public class ZonedetailsPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "LOG";
    private String[] tabNames;
    private String goods_id;
    private String shop_id;
    public ZonedetailsPagerAdapter(FragmentManager fm, String goods_id, String shop_id) {
        super(fm);
        this.shop_id = shop_id;
        this.goods_id = goods_id;
//        tabNames = LianXiangKeApplication.context.getResources().getStringArray(R.array.tabs);
        tabNames = new String[]{"商品", "详情", "参数"};
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new ZoneShopFragment();
                Bundle bundle = new Bundle();
                bundle.putString("goods_id",goods_id);
                bundle.putString("shop_id",shop_id);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new ZoneDetailsFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("goods_id",goods_id);
                bundle1.putString("shop_id",shop_id);
                fragment.setArguments(bundle1);
                break;
            case 2:
                fragment = new ZoneCanShuFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("goods_id",goods_id);
                bundle2.putString("shop_id",shop_id);
                fragment.setArguments(bundle2);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    /**获取条目标题，不写会崩哦！！*/
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }
}
