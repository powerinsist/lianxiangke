package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.UnusedBean;

import java.util.List;

/**
 * Created by xuchenchen on 2017/5/19.
 */

public class OutOfDataAdapter extends BaseAdapter {
    private Context context;
    private List<UnusedBean> list;

    public OutOfDataAdapter(Context context,List<UnusedBean> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.lxp_outof_item,null,false);
        }else {
            view = convertView;
        }
        UnusedBean unusedBean = list.get(position);
        TextView number_tv = (TextView) view.findViewById(R.id.number_tv);
        TextView date_tv = (TextView) view.findViewById(R.id.date_tv);

        number_tv.setText(unusedBean.getNumber());
        date_tv.setText(unusedBean.getDate());

        return view;
    }
}
