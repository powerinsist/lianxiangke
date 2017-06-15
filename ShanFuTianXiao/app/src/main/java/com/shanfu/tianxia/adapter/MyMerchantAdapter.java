package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.MyMerchantBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/30.
 */
public class MyMerchantAdapter extends BaseAdapter {

    private List<MyMerchantBean.DataBean.ListBean> lists;
    private LayoutInflater inflater;
    private String shop_name, sign_time, discount, recommend;
    private String shoptype;

    public MyMerchantAdapter(Context mContext, List<MyMerchantBean.DataBean.ListBean> lists) {
        inflater = LayoutInflater.from(mContext);
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_mymerchant_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        shop_name = lists.get(position).getShop_name();
        sign_time = lists.get(position).getSign_time();
        discount = lists.get(position).getDiscount();
        shoptype = lists.get(position).getShoptype();
        //recommend = lists.get(position).getRecommend();
        holder.shop_name_tv.setText(shop_name);
        holder.sign_time_tv.setText(sign_time);
//        holder.discount_tv.setText(discount);
       // holder.recommend_tv.setText(recommend);
        if (shoptype != null){
            switch (shoptype){
                case "A":
                    holder.discount_tv.setText("A类95折");
                    break;
                case "B":
                    holder.discount_tv.setText("B类9折");
                    break;
                case "C":
                    holder.discount_tv.setText("C类85折");
                    break;
                case "D":
                    holder.discount_tv.setText("D类8折");
                    break;
                case "E":
                    holder.discount_tv.setText("E类7折");
                    break;
                case "F":
                    holder.discount_tv.setText("F类6折");
                    break;
                case "G":
                    holder.discount_tv.setText("G类5折");
                    break;
                case "H":
                    holder.discount_tv.setText("H类4折");
                    break;
                case "I":
                    holder.discount_tv.setText("I类3折");
                    break;
                case "J":
                    holder.discount_tv.setText("J类2折");
                    break;
                case "K":
                    holder.discount_tv.setText("K类1折");
                    break;
                case "L":
                    holder.discount_tv.setText("L类0折");
                    break;
            }
        }else {
            holder.discount_tv.setText("");
        }


        return convertView;

    }

    class ViewHolder {

        @Bind(R.id.shop_name_tv)
        TextView shop_name_tv;
        @Bind(R.id.sign_time_tv)
        TextView sign_time_tv;
        @Bind(R.id.discount_tv)
        TextView discount_tv;
       // @Bind(R.id.recommend_tv)
       // TextView recommend_tv;


        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}

