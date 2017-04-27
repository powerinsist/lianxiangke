package com.shanfu.tianxia.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.AbsListView;
import android.widget.LinearLayout;

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

	public HomeGridAdapter(Context context, List<ProductData> datas,
			int itemLayoutId) {
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
		iv = (NetworkImageView) holder
				.getView(R.id.item_home_iv);
//		star = holder.getView(R.id.star);
//		star.setClickable(false);
		//star.setRating(Float.valueOf(data.getGrade()));
		//star.setNumStars(Float.valueOf(data.getGrade()));
//		if(!TextUtils.isEmpty(data.getGrade())){
//			star.setStar(Float.valueOf(data.getGrade()));
//		}else{
//			star.setStar(0);
//		}

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
