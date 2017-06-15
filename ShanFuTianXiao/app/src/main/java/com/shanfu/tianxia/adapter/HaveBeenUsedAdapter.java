package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.RedLxpBean;
import com.shanfu.tianxia.bean.TicktListBean;
import com.shanfu.tianxia.utils.RotateTextView;

import java.util.List;

/**
 * Created by xuchenchen on 2017/5/19.
 *
 * 未使用
 */

public class HaveBeenUsedAdapter extends BaseAdapter {
    private Context context;
    private List<TicktListBean.DataBean.List1Bean> list;
    private View view;

    public HaveBeenUsedAdapter(Context context,List<TicktListBean.DataBean.List1Bean> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.lxp_unused_item,null,false);
        }else {
            view = convertView;
        }
        TicktListBean.DataBean.List1Bean ticktListBean = list.get(position);
        ImageView color_iv = (ImageView) view.findViewById(R.id.color_iv);
        TextView number_tv = (TextView) view.findViewById(R.id.number_tv);

        String money = ticktListBean.getMoney();
        if ("0".equals(money)){
            color_iv.setImageResource(R.mipmap.blueticket_bg_lianxp);
        }else
            color_iv.setImageResource(R.mipmap.redticket_bg_lianxp);
            number_tv.setText(ticktListBean.getDeadline());

        return view;
    }

    class ViewHolder{
        ImageView color_iv;
        TextView number_tv;
        TextView date_tv;
//        RotateTextView rotate_tv;
    }
}
