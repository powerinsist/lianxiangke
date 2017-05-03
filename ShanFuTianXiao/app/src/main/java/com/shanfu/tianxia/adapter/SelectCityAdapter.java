package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.SelectCityBean;
import com.shanfu.tianxia.network.NetworkManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/31.
 */
public class SelectCityAdapter extends BaseAdapter {

    private Context context;
    private List<SelectCityBean.SelectCityData> data;
    private LayoutInflater mInflater;
    private SelectCityBean.SelectCityData item;

    public void setData(List<SelectCityBean.SelectCityData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public SelectCityAdapter(Context context, List<SelectCityBean.SelectCityData> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SelectCityBean.SelectCityData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_select_shop_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        item = getItem(position);
        holder.select_shop_name.setText(item.getShopname());
        holder.select_shop_distance.setText(item.getJuli()+"km");
        holder.select_shop_address.setText(item.getAddress());
        NetworkManager.getInstance().setImageUrl(holder.select_shop_iv, item.getImg());
//        holder.select_shop_star.setNumStars(1);
        //holder.select_shop_star.setRating(Float.valueOf(item.getGrade()));
     //   holder.select_shop_star.setNumStars(Integer.valueOf(item.getGrade()));
       /* if(item.getImg()!=null){
            setImage(context,holder.select_shop_iv,item.getImg());
        }*/

       // setImage(context, holder.select_shop_iv, item.getImg() == null ? null : item.getImg());
        return convertView;
    }

    private void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_default_color)//
                .error(R.drawable.ic_default_color)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    class ViewHolder  {
        @Bind(R.id.select_shop_iv)
        NetworkImageView select_shop_iv;
        @Bind(R.id.select_shop_name)
        TextView select_shop_name;
        @Bind(R.id.select_shop_distance)
        TextView select_shop_distance;
//        @Bind(R.id.select_shop_star)
//        RatingBar select_shop_star;
        @Bind(R.id.select_shop_address)
        TextView select_shop_address;
        @Bind(R.id.select_shop_phone)
        ImageView select_shop_phone;


        private View rootView;

        public ViewHolder(View convertView) {
            rootView = convertView;
            ButterKnife.bind(this, convertView);
        }

    }
}
