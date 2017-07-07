package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.HelpCenterBean;
import com.shanfu.tianxia.ui.HelpCenterWebActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchenchen on 2017/7/7.
 */

public class HelpCenterAdapter extends BaseAdapter {

    private Context mContext;
    private List<HelpCenterBean.DataBean.ListBean> list = new ArrayList<>();

    public HelpCenterAdapter(Context context, List<HelpCenterBean.DataBean.ListBean> list){
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
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_help_center_item,null,false);
            viewHolder.help_rl = (RelativeLayout) convertView.findViewById(R.id.help_rl);
            viewHolder.help_tv = (TextView) convertView.findViewById(R.id.help_tv);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();
        final HelpCenterBean.DataBean.ListBean listBean = list.get(position);
        viewHolder.help_tv.setText(listBean.getTitle());
        viewHolder.help_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HelpCenterWebActivity.class);
                intent.putExtra("url",listBean.getUrl());
                intent.putExtra("title",listBean.getTitle());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        RelativeLayout help_rl;
        TextView help_tv;
    }
}
