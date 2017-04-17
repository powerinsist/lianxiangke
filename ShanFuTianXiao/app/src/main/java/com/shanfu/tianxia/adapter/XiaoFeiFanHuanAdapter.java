package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.XiaoFeiFanHuanBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/29.
 */
public class XiaoFeiFanHuanAdapter extends BaseAdapter {

    private List<XiaoFeiFanHuanBean.CiaoFeiBean> lists;
    private LayoutInflater inflater;
    private String accumulated_income, income_source, income_money, income_time;

    public XiaoFeiFanHuanAdapter(Context mContext, List<XiaoFeiFanHuanBean.CiaoFeiBean> lists) {
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
            convertView = inflater.inflate(R.layout.layout_xiaofei_fanhuan_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.remission_money_tv.setText(lists.get(position).getRemission_money());
        holder.remission_time_tv.setText(lists.get(position).getRemission_time());

        return convertView;

    }

    class ViewHolder {

        @Bind(R.id.remission_money_tv)
        TextView remission_money_tv;
        @Bind(R.id.remission_time_tv)
        TextView remission_time_tv;



        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}
