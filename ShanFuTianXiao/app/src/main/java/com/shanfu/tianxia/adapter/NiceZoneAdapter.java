package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.recyclerviewholder.Home_Normal;
import com.shanfu.tianxia.ui.NiceZoneActivity;

import java.util.List;

/**
 * Created by xuchenchen on 2017/7/3.
 */

public class NiceZoneAdapter extends BasicAdapter {

    private List<ZoneProductBean.DataBean.ListBean> listBeans;
    private Context mContext;

    public NiceZoneAdapter(List<ZoneProductBean.DataBean.ListBean> list, Context context) {
        this.listBeans = list;
        this.mContext = context;

    }

    @Override
    protected BasicViewHolder createRelViewHolder(ViewGroup parent) {
        return new Home_Normal(LayoutInflater.from(mContext)
                .inflate(R.layout.item_zone_grid, parent, false), listBeans, listener,mContext);
    }

    @Override
    protected int getRelCount() {
        return listBeans.size();
    }

}
