package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.SelectCityBean;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.CommodityDetailsActivity;
import com.shanfu.tianxia.view.MyRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/4/1.
 */
public class SelectCityDataAdapter extends RecyclerView.Adapter<SelectCityDataAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private String grade;
    private Intent intent;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view ,int position);
    }
    private List<SelectCityBean.SelectCityData> dataList = new ArrayList<>();


    public void addAllData(List<SelectCityBean.SelectCityData> dataList) {
       // clearData();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.dataList.clear();
    }

    public SelectCityDataAdapter(Context context) {
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.select_shop_iv)
        NetworkImageView select_shop_iv;
        @Bind(R.id.select_shop_name)
        TextView select_shop_name;
        @Bind(R.id.select_shop_distance)
        TextView select_shop_distance;
//        @Bind(R.id.select_shop_star)
//        MyRatingBar select_shop_star;
        @Bind(R.id.select_shop_address)
        TextView select_shop_address;
        @Bind(R.id.select_shop_phone)
        ImageView select_shop_phone;
        @Bind(R.id.item_home_gridlayout)
        LinearLayout item_home_gridlayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_shop_item, parent, false);
        //将创建的View注册点击事件
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.select_shop_name.setText(dataList.get(position).getShopname());
        holder.select_shop_distance.setText(dataList.get(position).getJuli() + "km");
        holder.select_shop_address.setText(dataList.get(position).getAddress());
       // Glide.with(mContext).load(dataList.get(position).getImg()).into(holder.select_shop_iv);
        NetworkManager.getInstance().setImageUrl(holder.select_shop_iv, dataList.get(position).getImg());
        grade = dataList.get(position).getGrade();
//        holder.select_shop_star.setClickable(false);
//        if(TextUtils.isEmpty(grade)){
//            holder.select_shop_star.setStar(0);
//        }else{
//            holder.select_shop_star.setStar(Float.valueOf(grade));
//        }

        holder.itemView.setTag(position);
        holder.item_home_gridlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext,CommodityDetailsActivity.class);
                intent.putExtra("shopid",dataList.get(position).getId());
                intent.putExtra("shopname",dataList.get(position).getShopname());
                intent.putExtra("grade",dataList.get(position).getGrade());
                intent.putExtra("img",dataList.get(position).getImg());
                 mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
