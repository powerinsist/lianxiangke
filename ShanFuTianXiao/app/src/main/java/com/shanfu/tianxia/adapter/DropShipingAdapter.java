package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.DropShipingBean;

import java.util.List;

/**
 * Created by xuchenchen on 2017/5/16.
 */

public class DropShipingAdapter extends BaseAdapter {

    private Context context;
    private List<DropShipingBean> list;

    public DropShipingAdapter(Context context,List<DropShipingBean> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_drop_shiping_item,null,false);
            viewHolder.change_status = (TextView) convertView.findViewById(R.id.change_status);
            viewHolder.shop_image = (ImageView) convertView.findViewById(R.id.shop_image);
            viewHolder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
            viewHolder.shop_count_x = (TextView) convertView.findViewById(R.id.shop_count_x);
            viewHolder.shop_count = (TextView) convertView.findViewById(R.id.shop_count);
            viewHolder.count_lxp = (TextView) convertView.findViewById(R.id.count_lxp);
            viewHolder.delete_tv = (TextView) convertView.findViewById(R.id.delete_tv);
            viewHolder.contact_seller = (TextView) convertView.findViewById(R.id.contact_seller);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();

            DropShipingBean dropShipingBean = list.get(position);
            String status = dropShipingBean.getStatus();
            String imageurl = dropShipingBean.getImageurl();
            String shopname = dropShipingBean.getShopname();
            String shopcount = dropShipingBean.getShopcount();
            String lxpcount = dropShipingBean.getLxpcount();

            viewHolder.change_status.setText(status);
            viewHolder.shop_name.setText(shopname);
            viewHolder.shop_count.setText(shopcount);
            viewHolder.count_lxp.setText(lxpcount);

        return convertView;
    }

    class ViewHolder{
        TextView change_status;
        ImageView shop_image;
        TextView shop_name;
        TextView shop_count_x;
        TextView shop_count;
        TextView count_lxp;
        TextView delete_tv;
        TextView contact_seller;
    }
}
