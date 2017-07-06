package com.shanfu.tianxia.recyclerviewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.api.client.json.Json;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.BasicAdapter;
import com.shanfu.tianxia.adapter.BasicViewHolder;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.fragment.ZoneShopFragment;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.NiceZoneActivity;
import com.shanfu.tianxia.ui.ZoneGoodsDetailsActivity;
import com.shanfu.tianxia.utils.TUtils;

import java.util.List;

import butterknife.Bind;

/**
 * Created by xuchenchen on 2017/7/3.
 */

public class Home_Normal extends BasicViewHolder {
    private static final String TAG = "LOG";
    private View itemView;
    private List<ZoneProductBean.DataBean.ListBean> list;
    private ZoneProductBean.DataBean dataBeans;
    private BasicAdapter.OnItemClickListener listener;
    private Context mContext;

    NetworkImageView image;
    TextView name;
    TextView lxp_count;
    ImageView shipping_iv;
    TextView yiduihuan;
    TextView price;
    LinearLayout item_home_gridlayout;

    private String goods_id;
    private String shop_id;
    private String shop_name;

    public Home_Normal(View itemView, List<ZoneProductBean.DataBean.ListBean> list, BasicAdapter.OnItemClickListener listener, final Context context) {
        super(itemView);
        this.itemView = itemView;
        this.list = list;
        this.listener = listener;
        this.mContext = context;

        image = (NetworkImageView) itemView.findViewById(R.id.zone_grid_shop_iv);
        name = (TextView) itemView.findViewById(R.id.zone_grid_shop_tv);
        lxp_count = (TextView) itemView.findViewById(R.id.zone_grid_lxp_tv);
        shipping_iv = (ImageView) itemView.findViewById(R.id.baiyou_iv);
        yiduihuan = (TextView) itemView.findViewById(R.id.exchange_count_tv);
        price = (TextView) itemView.findViewById(R.id.zone_shop_price_tv);
        item_home_gridlayout = (LinearLayout) itemView.findViewById(R.id.item_home_gridlayout);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ZoneGoodsDetailsActivity.class);
                intent.putExtra("goods_id",goods_id);
                intent.putExtra("shop_id",shop_id);
                intent.putExtra("shop_name",shop_name);
                context.startActivity(intent);
            }
        });
    }
    public static int totalnumber;
    @Override
    public void init(int position) {

        ZoneProductBean.DataBean.ListBean listBean = list.get(position);
        totalnumber = listBean.getTotalnumber();
        Log.i(TAG, "init: "+totalnumber);
        goods_id = listBean.getGoods_id();
        shop_id = listBean.getShop_id();
        shop_name = listBean.getName();
        NetworkManager.getInstance().setImageUrl(image, listBean.getImage());
        name.setText(listBean.getName());
        lxp_count.setText(listBean.getRed()+"张联享票");
        yiduihuan.setText(listBean.getSold()+"件");
        price.setText(listBean.getNow_price());
    }
}
