package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.widget.CircleImageView;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.CommodityDetailsItemBean;
import com.shanfu.tianxia.view.MyRatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/22
 * 描    述：评价页的Adapter
 * 修订历史：
 * ================================================
 */
public class EvaluationAdapter extends BaseAdapter {

    private Context context;
    private List<CommodityDetailsItemBean.ItemBean> data;
    private LayoutInflater mInflater;

    public void setData(List<CommodityDetailsItemBean.ItemBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public EvaluationAdapter(Context context, List<CommodityDetailsItemBean.ItemBean> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CommodityDetailsItemBean.ItemBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_evaluate, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        CommodityDetailsItemBean.ItemBean item = getItem(position);
        holder.content.setText(item.getContent());
        holder.username.setText(item.getNickname());
        holder.createTime.setText(item.getTime());
        holder.grade.setClickable(false);
        if(TextUtils.isEmpty(item.getScore())){
            holder.grade.setStar(0);
        }else{
            holder.grade.setStar(Float.valueOf(item.getScore()));
        }
        //holder.grade.setRating(Float.valueOf(item.getScore()));
        //holder.grade.setNumStars((int)Double.valueOf(item.getScore()));

       // setImage(context, holder.avatar, item.getPics() == null ? null : item.getPics());

        ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        /*List<String> imageDetails = item.getAttachments();
        if (imageDetails != null) {
            for (EvaluationPic imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail.smallImageUrl);
                info.setBigImageUrl(imageDetail.imageUrl);
                imageInfo.add(info);
            }
        }*/
        for(int x=0;x<3;x++){
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl("https://api.lianxiangke.cn//Public/upload/img/banner/ceshi.png");
            info.setBigImageUrl("https://api.lianxiangke.cn//Public/upload/img/banner/ceshi.png");
            imageInfo.add(info);
        }
       holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));


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
        @Bind(R.id.tv_content)
        TextView content;
        @Bind(R.id.nineGrid)
        NineGridView nineGrid;
        @Bind(R.id.tv_username)
        TextView username;
        @Bind(R.id.tv_createTime)
        TextView createTime;
        @Bind(R.id.rb_grade)
        MyRatingBar grade;
        @Bind(R.id.avatar)
        CircleImageView avatar;


        private PopupWindow window;
        private PopupWindow editWindow;
        private View rootView;

        public ViewHolder(View convertView) {
            rootView = convertView;
            ButterKnife.bind(this, convertView);
        }

    }
}