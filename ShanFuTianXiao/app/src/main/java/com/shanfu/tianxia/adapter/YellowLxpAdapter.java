package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.RedLxpBean;
import com.shanfu.tianxia.utils.RotateTextView;

import java.util.List;

/**
 * Created by xuchenchen on 2017/5/16.
 */

public class YellowLxpAdapter extends BaseAdapter {

    private List<RedLxpBean> list;
    private Context context;

    public YellowLxpAdapter(Context context,List<RedLxpBean> list){
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_yellow_detail_item,null,false);
            viewHolder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
            viewHolder.rotate_tv = (RotateTextView) convertView.findViewById(R.id.rotate_tv);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

        RedLxpBean redLxpBean = list.get(position);
        String time = redLxpBean.getTime();
        String date = redLxpBean.getDate();
        viewHolder.time_tv.setText(time);
        viewHolder.rotate_tv.setText(date);

        return convertView;
    }

    class ViewHolder{
        TextView time_tv;
        RotateTextView rotate_tv;
    }
}
