package com.shanfu.tianxia.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }

    public ZoneGridAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);
        View view = LayoutInflater.from(context).inflate(R.layout.item_zone_grid,null,false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZoneGridAdapter.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

            holder.lxp_count.setText(listBeans.get(position).getRed()+"张联享票+100元");
            holder.name.setText(listBeans.get(position).getName());
            Log.e("LOG","aaaa");
            holder.price.setText(listBeans.get(position).getNow_price());
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? listBeans.size() : listBeans.size() + 1;
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
            if(itemView == mHeaderView) return;
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
