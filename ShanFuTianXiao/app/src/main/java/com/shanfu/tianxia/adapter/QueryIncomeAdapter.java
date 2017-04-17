package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.QueryIncomeBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/29.
 */
public class QueryIncomeAdapter extends BaseAdapter {

    private List<QueryIncomeBean.QueryBean> lists;
    private LayoutInflater inflater;
    private String accumulated_income, income_source, income_money, income_time;

    public QueryIncomeAdapter(Context mContext, List<QueryIncomeBean.QueryBean> lists) {
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
            convertView = inflater.inflate(R.layout.layout_query_income_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        accumulated_income = lists.get(position).getAccumulated_income();
        income_money = lists.get(position).getIncome_money();
        income_source = lists.get(position).getIncome_source();
        income_time = lists.get(position).getIncome_time();
        holder.accumulated_income_tv.setText(accumulated_income);
        if ("手续费".equals(income_money) || "消费".equals(income_money) || "提现".equals(income_money)) {
            holder.income_money_tv.setTextColor(Color.RED);
            holder.income_money_tv.setText("-" + income_money);
        } else {
            holder.income_money_tv.setTextColor(Color.GREEN);
            holder.income_money_tv.setText("+" + income_money);
        }
        holder.income_source_tv.setText(income_source);

        holder.income_time_tv.setText(income_time);

        return convertView;

    }

    class ViewHolder {

        @Bind(R.id.income_time_tv)
        TextView income_time_tv;
        @Bind(R.id.income_money_tv)
        TextView income_money_tv;
        @Bind(R.id.income_source_tv)
        TextView income_source_tv;
        @Bind(R.id.accumulated_income_tv)
        TextView accumulated_income_tv;


        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}
