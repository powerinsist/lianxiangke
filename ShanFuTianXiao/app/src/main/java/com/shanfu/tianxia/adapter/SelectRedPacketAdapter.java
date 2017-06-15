package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.RedPacketBean;

import java.util.List;

/**
 * Created by xuchenchen on 2017/5/19.
 */

public class SelectRedPacketAdapter extends BaseAdapter {

    private Context context;
    private List<RedPacketBean.DataBean.ListBean> list;

    public SelectRedPacketAdapter(Context context,List<RedPacketBean.DataBean.ListBean> list){
        this.context = context;
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
        View view = null;
        if (convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.select_red_packet_item,null,false);
        }else {
            view = convertView;
        }
        TextView date = (TextView) view.findViewById(R.id.date_tv);
        TextView money = (TextView) view.findViewById(R.id.money_tv);

        RedPacketBean.DataBean.ListBean redPacketBean = list.get(position);

        date.setText(redPacketBean.getCollectiontime());
        money.setText(redPacketBean.getMoney()+"元");

        return view;
    }


}
