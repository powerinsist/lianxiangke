package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.RevenueInquiryBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/29.
 */
public class RevenueInquiryAdapter extends BaseAdapter {

    private List<RevenueInquiryBean.RevenueBean> lists;
    private LayoutInflater inflater;
//    private String changed_money,cases,time,balance;
    private String changed_money,cases,time;
    public RevenueInquiryAdapter(Context mContext,List<RevenueInquiryBean.RevenueBean> lists){
        inflater =  LayoutInflater.from(mContext);
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
            convertView = inflater.inflate(R.layout.layout_revenue_inquiry_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
//        balance = lists.get(position).getBalance();
        changed_money = lists.get(position).getChanged_money();
        cases = lists.get(position).getCases();
        time = lists.get(position).getIncome_time();

        if("手续费".equals(cases)||"消费".equals(cases)||"提现".equals(cases)){
            holder.changed_money_tv.setTextColor(Color.RED);
            holder.changed_money_tv.setText("-" + changed_money);
        }else{
            holder.changed_money_tv.setTextColor(Color.GREEN);
            holder.changed_money_tv.setText("+"+changed_money);
        }
//        holder.balance_tv.setText(balance);

        holder.case_tv.setText(cases);
        holder.time_tv.setText(time);
        return convertView;

    }
    class ViewHolder{

//        @Bind(R.id.balance_tv)
//        TextView balance_tv;
        @Bind(R.id.changed_money_tv)
        TextView changed_money_tv;
        @Bind(R.id.case_tv)
        TextView case_tv;
        @Bind(R.id.time_tv)
        TextView time_tv;


        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}
