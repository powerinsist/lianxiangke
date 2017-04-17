package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.OnLineBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/30.
 */
public class OnLineAdapter  extends BaseAdapter {

    private List<OnLineBean.OnLineListBean> lists;
    private LayoutInflater inflater;
    private String member_name, member_id, reg_time;

    public OnLineAdapter(Context mContext, List<OnLineBean.OnLineListBean> lists) {
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
            convertView = inflater.inflate(R.layout.layout_online_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        member_id = lists.get(position).getMember_id();
        member_name = lists.get(position).getMember_name();
        reg_time = lists.get(position).getReg_time();

        holder.member_id_tv.setText(member_id);

        holder.member_name_tv.setText(member_name);

        holder.reg_time_tv.setText(reg_time);

        return convertView;

    }

    class ViewHolder {

        @Bind(R.id.member_name_tv)
        TextView member_name_tv;
        @Bind(R.id.member_id_tv)
        TextView member_id_tv;
        @Bind(R.id.reg_time_tv)
        TextView reg_time_tv;



        public ViewHolder(View convertView) {

            ButterKnife.bind(this, convertView);
        }
    }
}
