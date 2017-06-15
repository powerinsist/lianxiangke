package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.bean.TicktListBean;
import com.shanfu.tianxia.fragment.HaveBeenUsedFragment;
import com.shanfu.tianxia.fragment.OutOfDateFragment;
import com.shanfu.tianxia.fragment.UnusedFragment;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.ui.MyLianxpActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xuchenchen on 2017/5/15.
 */

public class MylianxpAdapter extends FragmentPagerAdapter {
    private String[] tabNames;
    private FragmentManager fm;

    public MylianxpAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        tabNames = new String[]{"未 使 用", "已 使 用", "已 过 期"};
    }
    //
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HaveBeenUsedFragment();
                break;
            case 1:
                fragment = new UnusedFragment();
                break;
            case 2:
                fragment = new OutOfDateFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    /**
     * 获取条目标题，不写会崩哦！！
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

}
