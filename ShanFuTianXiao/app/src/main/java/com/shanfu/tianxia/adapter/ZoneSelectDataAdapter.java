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
import com.shanfu.tianxia.bean.ZoneSelectBean;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.ZoneGoodsDetailsActivity;

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

    private SelectCityDataAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    private List<ZoneSelectBean.DataBean.ListBean> list;
    public static int totalnumber;

    public ZoneSelectDataAdapter(Context context,List<ZoneSelectBean.DataBean.ListBean> list){
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
        NetworkManager.getInstance().setImageUrl(holder.zone_grid_shop_iv,list.get(position).getImage());
        holder.zone_grid_shop_tv.setText(list.get(position).getName());
        holder.zone_grid_lxp_tv.setText(list.get(position).getRed()+"张联享票");
        holder.zone_shop_price_tv.setText(list.get(position).getNow_price());
        holder.exchange_count_tv.setText(list.get(position).getSold());
        String shipping = list.get(position).getShipping();
        totalnumber = list.get(position).getTotalnumber();
        final String goods_id = list.get(position).getGoods_id();
        final String shop_name = list.get(position).getName();
        final String shop_id = list.get(position).getShop_id();


        holder.item_home_gridlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ZoneGoodsDetailsActivity.class);
                intent.putExtra("goods_id",goods_id);
                Log.e("LOG","---------->"+goods_id);
                intent.putExtra("shop_id",shop_id);
                Log.e("LOG","---------->"+shop_id);
                intent.putExtra("shop_name",shop_name);
                Log.e("LOG","---------->"+shop_name);
                context.startActivity(intent);
            }
        });
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
        @Bind(R.id.item_home_gridlayout)
        LinearLayout item_home_gridlayout;
        @Bind(R.id.exchange_count_tv)
        TextView exchange_count_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void addAllData(List<ZoneSelectBean.DataBean.ListBean> list){
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
