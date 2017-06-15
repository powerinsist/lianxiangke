package com.shanfu.tianxia.adapter;

import android.app.Application;
import android.content.Context;
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
    private String[] tabNames;
    public ZonedetailsPagerAdapter(FragmentManager fm) {
        super(fm);
//        tabNames = LianXiangKeApplication.context.getResources().getStringArray(R.array.tabs);
        tabNames = new String[]{"商品", "详情", "参数"};
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new ZoneShopFragment();
                break;
            case 1:
                fragment = new ZoneDetailsFragment();
                break;
            case 2:
                fragment = new ZoneCanShuFragment();
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
