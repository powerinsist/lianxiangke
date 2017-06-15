package com.shanfu.tianxia.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.model.ProductData;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.view.MyRatingBar;

import java.util.List;


/**
 * 首页ListView中GridView的适配器
 */
public class HomeGridAdapter extends UniversalAdapter<ProductData> {

	private int screenWidth;
	private DisplayMetrics dm;
//	private MyRatingBar star;
	private NetworkImageView iv;

	public HomeGridAdapter(Context context, List<ProductData> datas, int itemLayoutId) {
		super(context, datas, itemLayoutId);

		dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		screenWidth = dm.widthPixels;
	}

	@Override
	public void updateItem(ViewHolder holder, ProductData data) {
		// 动态修改GridView布局的宽高，解决不显示的问题
		LinearLayout layout = (LinearLayout) holder
				.getView(R.id.item_home_gridlayout);
		AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) layout.getLayoutParams();
		layoutParams.width = screenWidth  - (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, dm);
		layoutParams.height = (int)(screenWidth * 1.5 /5);
		layout.setLayoutParams(layoutParams);
		iv = (NetworkImageView) holder.getView(R.id.item_home_iv);
//		star = holder.getView(R.id.star);
//		star.setClickable(false);
		//star.setRating(Float.valueOf(data.getGrade()));
		//star.setNumStars(Float.valueOf(data.getGrade()));
//		if(!TextUtils.isEmpty(data.getGrade())){
//			star.setStar(Float.valueOf(data.getGrade()));
//		}else{
//			star.setStar(0);
//		}
		TextView score = holder.getView(R.id.score);
		LinearLayout type_ll = holder.getView(R.id.shop_type_ll);
		String shoptype = data.getShoptype();
		switch (shoptype){
			case "A":
				type_ll.setVisibility(View.GONE);
				holder.setTextViewText(R.id.jifen_tv,"消费1元送0.05张联享票");
				break;
			case "B":
				type_ll.setVisibility(View.GONE);
				break;
			case "C":
				score.setText("5%");
				break;
			case "D":
				score.setText("10%");
				break;
			case "E":
				score.setText("20%");
				break;
			case "F":
				score.setText("30%");
				break;
			case "G":
				score.setText("40%");
				break;
			case "H":
				score.setText("50%");
				break;
			case "I":
				score.setText("60%");
				break;
			case "J":
				score.setText("70%");
				break;
			case "K":
				score.setText("80%");
				break;
			case "L":
				score.setText("90%");
				break;
		}
		//star.setNumStars(DateTransformation.getint(data.getGrade()));
		//NetworkManager.getInstance().setImageUrl(iv, data.getImgUrl());
		NetworkManager.getInstance().setImageUrl(iv, data.getImg());
		//Glide.with(context).load(data.getImg()).into(iv);
		holder.setTextViewText(R.id.store_name, data.getShopname());
//		holder.setTextViewText(R.id.score, data.getDiscount() + "%");
//		holder.setTextViewText(R.id.fraction, data.getGrade() + "分");
		holder.setTextViewText(R.id.foot_odga,data.getLabel());
		holder.setTextViewText(R.id.adress_shop,data.getAddress());
		holder.setTextViewText(R.id.distance,data.getJuli()+"km");
		//holder.setTextViewText(R.id.item_home_tv_info, data.getInfo());
		//holder.setTextViewText(R.id.item_home_tv_price, "￥" + data.getPrice());
	}

}
