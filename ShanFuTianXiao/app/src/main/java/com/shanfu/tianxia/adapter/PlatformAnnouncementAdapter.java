package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.PlatformAnnouncementBean;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.PlatformWebActivity;

import java.util.List;

/**
 * Created by xuchenchen on 2017/7/7.
 */

public class PlatformAnnouncementAdapter extends BaseAdapter {

    private List<PlatformAnnouncementBean.DataBean.ListBean> list;
    private Context mContext;

    public PlatformAnnouncementAdapter(Context context, List<PlatformAnnouncementBean.DataBean.ListBean> list){
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_platform_item,null,false);
            holder.platform_rl = (RelativeLayout) convertView.findViewById(R.id.platform_rl);
            holder.picture_netIv = (NetworkImageView) convertView.findViewById(R.id.picture_netIv);
            holder.platform_tv = (TextView) convertView.findViewById(R.id.platform_tv);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();
        final PlatformAnnouncementBean.DataBean.ListBean listBean = list.get(position);
        NetworkManager.getInstance().setImageUrl(holder.picture_netIv,listBean.getPic());
        holder.platform_tv.setText(listBean.getTitle());
        holder.platform_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PlatformWebActivity.class);
                intent.putExtra("title",listBean.getTitle());
                intent.putExtra("url",listBean.getUrl());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        RelativeLayout platform_rl;
        NetworkImageView picture_netIv;
        TextView platform_tv;
    }
}
