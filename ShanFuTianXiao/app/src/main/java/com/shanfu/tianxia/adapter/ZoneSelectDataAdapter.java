package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuchenchen on 2017/5/10.
 */

public class ZoneSelectDataAdapter extends RecyclerView.Adapter<ZoneSelectDataAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private Intent intent;
    private String grade;

    private SelectCityDataAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<ZoneProductBean> list;

    public ZoneSelectDataAdapter(Context context,List<ZoneProductBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zone_grid, parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        NetworkManager.getInstance().setImageUrl(holder.zone_grid_shop_iv,list.get(position).getShopimage());
//        holder.zone_grid_shop_tv.setText(list.get(position).getShopname());
//        holder.zone_grid_lxp_tv.setText(list.get(position).getLxpchange());
//        holder.zone_shop_price_tv.setText(list.get(position).getShopprice());
        Log.i("LOG", "onBindViewHolder: + 1111 ");
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.zone_grid_shop_iv)
        NetworkImageView zone_grid_shop_iv;
        @Bind(R.id.zone_grid_shop_tv)
        TextView zone_grid_shop_tv;
        @Bind(R.id.zone_grid_lxp_tv)
        TextView zone_grid_lxp_tv;
        @Bind(R.id.zone_shop_price_tv)
        TextView zone_shop_price_tv;
        @Bind(R.id.zone_lxp_first_iv)
        ImageView zone_lxp_first_iv;
        @Bind(R.id.zone_lxp_second_iv)
        ImageView zone_lxp_second_iv;
        @Bind(R.id.item_home_gridlayout)
        LinearLayout item_home_gridlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addAllData(List<ZoneProductBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.list.clear();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(SelectCityDataAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view ,int position);
    }

}
