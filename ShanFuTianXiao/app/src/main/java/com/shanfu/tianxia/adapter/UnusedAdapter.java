package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.TicktListBean;
import com.shanfu.tianxia.bean.UnusedBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/19.
 */

public class UnusedAdapter extends BaseAdapter {
    private Context context;
//    private List<UnusedBean> list;
    private List<TicktListBean.DataBean.List2Bean> list = new ArrayList<>();

    private View view;

    public UnusedAdapter(Context context,List<TicktListBean.DataBean.List2Bean> list){
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
        ViewHolder viewHolder = null;
        if (convertView == null){
            view = View.inflate(context,R.layout.lxp_used_item,null);
        }else {
            view = convertView;
        }
        TicktListBean.DataBean.List2Bean list2Bean = list.get(position);
        TextView number_tv = (TextView) view.findViewById(R.id.number_tv);
        TextView date_tv = (TextView) view.findViewById(R.id.date_tv);

        number_tv.setText(list2Bean.getId());
        date_tv.setText(list2Bean.getTime());

        return view;
    }

    class ViewHolder{
        TextView number_tv;
        TextView date_tv;
    }

}
