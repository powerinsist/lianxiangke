package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.mtxc.universallistview.UniversalAdapter;
import com.mtxc.universallistview.ViewHolder;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.model.HomeFloorData;
import com.shanfu.tianxia.model.ProductData;
import com.shanfu.tianxia.noscrollview.NoScrollGridView;
import com.shanfu.tianxia.ui.CommodityDetailsActivity;

import java.util.List;



/**
 * 首页ListView的适配器
 */
public class HomeListAdapter extends UniversalAdapter<HomeFloorData> {

	private List<HomeFloorData> datas;
	public HomeListAdapter(Context context, List<HomeFloorData> datas,
			int itemLayoutId) {
		super(context, datas, itemLayoutId);
		this.datas = datas;
	}




	public void updateState(List<HomeFloorData> datas){
		this.datas = datas;
		notifyDataSetChanged();
	}
	
	@Override
	public void updateItem(ViewHolder holder, HomeFloorData data) {
		//((TextView) holder.getView(R.id.item_home_tv_floor)).setText(data.getFloor());
		if (data.getProducts() != null) {
			NoScrollGridView gridView = (NoScrollGridView) holder
					.getView(R.id.item_home_grid);
			HomeGridAdapter adapter = new HomeGridAdapter(context,
					data.getProducts(), R.layout.item_home_grid);
			gridView.setAdapter(adapter);
			// 设置商品的点击事件
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					ProductData product = (ProductData) parent.getItemAtPosition(position);
					//LogUtil.log(LogType.DEBUG, getClass(), product.getId() + "");
				    Intent intent = new Intent(context,CommodityDetailsActivity.class);
					intent.putExtra("shopid",product.getShops_id());
					intent.putExtra("shopname",product.getShopname());
					intent.putExtra("grade",product.getGrade());
					intent.putExtra("img",product.getImg());
					context.startActivity(intent);

				}
			});
		}
	}

}