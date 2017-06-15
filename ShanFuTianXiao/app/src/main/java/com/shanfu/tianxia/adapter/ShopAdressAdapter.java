package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.AdressBean;
import com.shanfu.tianxia.ui.AddAdressActivity;
import com.shanfu.tianxia.ui.MyShopAdressActivity;
import com.shanfu.tianxia.utils.LogType;
import com.shanfu.tianxia.utils.LogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/14.
 */

public class ShopAdressAdapter extends BaseAdapter {
    private List<AdressBean.DataBean.ListBean> list;
    private Context context;
    private String checked;

    public ShopAdressAdapter(Context context,List<AdressBean.DataBean.ListBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_shop_adress_item,null,false);
            viewHolder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.phone_tv = (TextView) convertView.findViewById(R.id.phone_tv);
            viewHolder.adress_tv = (TextView) convertView.findViewById(R.id.adress_tv);
            viewHolder.radio_btn = (CheckBox) convertView.findViewById(R.id.radio_btn);
            viewHolder.moren_tv = (TextView) convertView.findViewById(R.id.moren_tv);
            viewHolder.bianji_tv = (TextView) convertView.findViewById(R.id.bianji_tv);
            viewHolder.delete_tv = (TextView) convertView.findViewById(R.id.delete_tv);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
            AdressBean.DataBean.ListBean adressBean = list.get(position);
            String name = adressBean.getName();
            String telephone = adressBean.getTelephone();
            String adress = adressBean.getAddress();
            String detailaddress = adressBean.getDetailaddress();
            checked = adressBean.getChecked();
            viewHolder.name_tv.setText(name);
            viewHolder.phone_tv.setText(telephone);
            viewHolder.adress_tv.setText(adress+detailaddress);
            Log.e("LOG","111");
            Log.e("LOG", checked);
            viewHolder.radio_btn.setChecked(checked =="1"?true:false);
            Log.e("LOG", checked);
            Log.e("LOG","222");
            viewHolder.radio_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((MyShopAdressActivity)context).requestCode();
//                    checked = "1";


                }
            });
            viewHolder.bianji_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,AddAdressActivity.class);
                    intent.putExtra("name",list.get(position).getName());
                    intent.putExtra("phone",list.get(position).getTelephone());
                    intent.putExtra("address",list.get(position).getAddress()+
                            list.get(position).getDetailaddress());
                    intent.putExtra("checked",list.get(position).getChecked());
                    context.startActivity(intent);

                }
            });

        return convertView;
    }

    class ViewHolder{
        public TextView name_tv;
        public TextView phone_tv;
        public TextView adress_tv;
        public CheckBox radio_btn;
        public TextView moren_tv;
        public TextView bianji_tv;
        public TextView delete_tv;
    }
}
