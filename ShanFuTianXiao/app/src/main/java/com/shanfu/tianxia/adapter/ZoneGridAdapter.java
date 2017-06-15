package com.shanfu.tianxia.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.ZoneGoodsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuchenchen on 2017/5/9.
 */

public class ZoneGridAdapter extends RecyclerView.Adapter<ZoneGridAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<ZoneProductBean.DataBean.ListBean> listBeans = new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Intent intent;

    public ZoneGridAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("LOG","000");
        View view = LayoutInflater.from(context).inflate(R.layout.item_zone_grid,null,false);
        Log.e("LOG","111");
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZoneGridAdapter.ViewHolder holder, int position) {
        holder.lxp_count.setText(listBeans.get(position).getRed()+"张联享票+100元");
        holder.name.setText(listBeans.get(position).getName());
        Log.e("LOG","aaaa");
        holder.price.setText(listBeans.get(position).getPrice());
        NetworkManager.getInstance().setImageUrl(holder.image,listBeans.get(position).getImage());
        holder.itemView.setTag(position);
        holder.item_home_gridlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ZoneGoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.zone_grid_shop_iv)
        NetworkImageView image;
        @Bind(R.id.zone_grid_shop_tv)
        TextView name;
        @Bind(R.id.zone_grid_lxp_tv)
        TextView lxp_count;
        @Bind(R.id.baiyou_iv)
        ImageView shipping_iv;
        @Bind(R.id.exchange_count_tv)
        TextView yiduihuan;
        @Bind(R.id.zone_shop_price_tv)
        TextView price;
        @Bind(R.id.item_home_gridlayout)
        LinearLayout item_home_gridlayout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view ,int position);
    }

    public void addAll(List<ZoneProductBean.DataBean.ListBean> listBeans){
        this.listBeans.addAll(listBeans);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.listBeans.clear();
    }
}
