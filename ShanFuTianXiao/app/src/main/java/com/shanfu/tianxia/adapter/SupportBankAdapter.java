package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.SupportBankBean;

import java.util.List;

/**
 * Created by xuchenchen on 2017/5/17.
 */

public class SupportBankAdapter extends BaseAdapter {

    private Context context;
    private List<SupportBankBean> list;

    public SupportBankAdapter(Context context, List<SupportBankBean> list){
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_support_bank_item,null,false);
            viewHolder.bank_image = (ImageView) convertView.findViewById(R.id.bank_image);
            viewHolder.bank_name = (TextView) convertView.findViewById(R.id.bank_name);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        SupportBankBean supportBankBean = list.get(position);
        viewHolder.bank_name.setText(supportBankBean.getBankName());
        viewHolder.bank_image.setImageResource(supportBankBean.getBankImage());

        return convertView;
    }

    class ViewHolder{
        ImageView bank_image;
        TextView bank_name;
    }
}
