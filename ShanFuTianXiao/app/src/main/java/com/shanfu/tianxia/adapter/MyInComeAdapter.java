package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mtxc.universallistview.ViewHolder;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.MyInComeBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuchenchen on 2017/4/21.
 */

public class MyInComeAdapter extends BaseAdapter {

    private List<MyInComeBean.InComeBean> lists;
    private LayoutInflater inflater;

    public MyInComeAdapter(Context mContext, List<MyInComeBean.InComeBean> lists){
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
        if (convertView == null){
            convertView = inflater.inflate(R.layout.layout_my_income_item,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.my_income_time_tv.setText(lists.get(position).getAdd_time());
        holder.my_income_money_tv.setText(lists.get(position).getMoney());
        holder.my_income_ordeird_tv.setText(lists.get(position).getOrderid());
        String my_income_status = lists.get(position).getStatus();

        if ("1".equals(my_income_status)){
            holder.my_income_status_tv.setText("提交失败");
        }
        else if ("2".equals(my_income_status))
        {
            holder.my_income_status_tv.setText("已提交");
        }else {
            holder.my_income_status_tv.setText("审核中");
        }

        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.my_income_time_tv)
        TextView my_income_time_tv;
        @Bind(R.id.my_income_money_tv)
        TextView my_income_money_tv;
        @Bind(R.id.my_income_ordeird_tv)
        TextView my_income_ordeird_tv;
        @Bind(R.id.my_income_status_tv)
        TextView my_income_status_tv;


        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}
