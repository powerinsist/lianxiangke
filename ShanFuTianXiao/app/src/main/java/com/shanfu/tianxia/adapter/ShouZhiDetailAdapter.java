package com.shanfu.tianxia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mtxc.universallistview.ViewHolder;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.AdateBean;
import com.shanfu.tianxia.bean.UserPayMentBean;
import com.shanfu.tianxia.ui.ShouZhiDetailActivity;
import com.shanfu.tianxia.ui.SzDetialNextActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuchenchen on 2017/5/9.
 */

public class ShouZhiDetailAdapter extends BaseAdapter{
    private List<UserPayMentBean.DataBean.ListBean> list = new ArrayList<>();
    private LayoutInflater inflater;
    public ShouZhiDetailAdapter(Context context, List<UserPayMentBean.DataBean.ListBean> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        /**
         * 1.convertView 缓存复用--- 包括 item内控件复用
         * 2.数据填充
         * */
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_shouzhi_detail_item,null,false);
            viewHolder.detail_name = (TextView) convertView.findViewById(R.id.detail_name);
            viewHolder.detail_money = (TextView) convertView.findViewById(R.id.detail_money);
            viewHolder.detail_date = (TextView) convertView.findViewById(R.id.detail_date);
            Log.e("LOG","2222");

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

            Log.e("LOG","3333");

            UserPayMentBean.DataBean.ListBean userBean = list.get(position);
            String source = userBean.getSource();
            String payment = userBean.getPayment();
            String money = userBean.getMoney();
            String time = userBean.getTime();

            if ("+".equals(payment)){
                //#1AAD19
                viewHolder.detail_money.setText("+ "+money);
                viewHolder.detail_money.setTextColor(Color.rgb(26, 173, 25));
            }else {
                viewHolder.detail_money.setText("- "+money);
                viewHolder.detail_money.setTextColor(Color.BLACK);
            }
                viewHolder.detail_name.setText(source);
                viewHolder.detail_date.setText(time);

            return convertView;
    }

    class ViewHolder {
        TextView detail_name;
        TextView detail_date;
        TextView detail_time;
        TextView detail_money;


//        public ViewHolder(View convertView) {

//            ButterKnife.bind(this, convertView);
//        }
    }

    /**
     * 添加列表项
     */
   /* public void addItem(UserPayMentBean lists) {
        list.add(lists);
    }*/
}
