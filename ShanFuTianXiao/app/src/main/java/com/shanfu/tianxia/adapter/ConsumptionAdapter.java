package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.ConsumptionBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/30.
 */
public class ConsumptionAdapter extends BaseAdapter {

    private List<ConsumptionBean.ConsumptionDataListBean> lists;
    private LayoutInflater inflater;
    private String consume_shop, consume_time, remission_coins, consume_money;

    public ConsumptionAdapter(Context mContext, List<ConsumptionBean.ConsumptionDataListBean> lists) {
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
            convertView = inflater.inflate(R.layout.layout_consumption_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        consume_shop = lists.get(position).getConsume_shop();
        consume_time = lists.get(position).getConsume_time();
        remission_coins = lists.get(position).getRemission_coins();
        consume_money = lists.get(position).getConsume_money();
        holder.consume_shop_tv.setText(consume_shop);
        holder.consume_time_tv.setText(consume_time);
        holder.remission_coins_tv.setText(remission_coins);
        holder.consume_money_tv.setText(consume_money);

        return convertView;

    }

    class ViewHolder {

        @Bind(R.id.consume_time_tv)
        TextView consume_time_tv;
        @Bind(R.id.consume_shop_tv)
        TextView consume_shop_tv;
        @Bind(R.id.consume_money_tv)
        TextView consume_money_tv;
        @Bind(R.id.remission_coins_tv)
        TextView remission_coins_tv;


        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}

