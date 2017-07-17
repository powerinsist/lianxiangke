package com.shanfu.tianxia.recyclerviewholder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.BasicViewHolder;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.bean.NavListBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.NiceZoneActivity;
import com.shanfu.tianxia.ui.SelectCityActivity;
import com.shanfu.tianxia.ui.ZoneGoodsDetailsActivity;
import com.shanfu.tianxia.ui.ZoneSelectActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xuchenchen on 2017/7/3.
 */

public class NavHolder extends BasicViewHolder {

    private static final String TAG = "LOG";
    private Context mContext;
    private LinearLayout view;

    private NiceZoneActivity niceZoneActivity;
    private List<NavListBean.DataBean.ListBean> list = new ArrayList<>();
    private final NetworkImageView zone_phone_iv;
    private final TextView zone_phone_tv;
    private final NetworkImageView zone_art_iv;
    private final TextView zone_art_tv;
    private final NetworkImageView zone_fitment_iv;
    private final TextView zone_fitment_tv;
    private final NetworkImageView zone_home_iv;
    private final TextView zone_home_tv;
    private final NetworkImageView zone_drink_iv;
    private final TextView zone_drink_tv;
    private final NetworkImageView zone_clothes_iv;
    private final TextView zone_clothes_tv;
    private final NetworkImageView zone_face_iv;
    private final TextView zone_face_tv;
    private final NetworkImageView zone_rest_iv;
    private final TextView zone_rest_tv;
    private ArrayList<NetworkImageView> networkImageViews;
    private ArrayList<TextView> textViews;
    private ArrayList<String> id;
    private ArrayList<String> names;
    private String shop_id;
    private String shop_name;
    private final LinearLayout childAtFirst;    //第一行
    private final LinearLayout childAtTwo;      //第二行
    private final int childFirstCount;

    public NavHolder(View itemView, Context context, NiceZoneActivity niceZoneActivity, List<NavListBean.DataBean.ListBean> list) {
        super(itemView);
        this.mContext = context;
        this.niceZoneActivity = niceZoneActivity;
        this.list = list;
        this.view = (LinearLayout) itemView;
        childAtFirst = (LinearLayout) view.getChildAt(0);
        childAtTwo = (LinearLayout) view.getChildAt(1);
        childFirstCount = childAtFirst.getChildCount();

        networkImageViews = new ArrayList<>();
        textViews = new ArrayList<>();
        id = new ArrayList<>();
        names = new ArrayList<>();


        zone_phone_iv = (NetworkImageView) itemView.findViewById(R.id.zone_phone_iv);
        zone_phone_tv = (TextView) itemView.findViewById(R.id.zone_phone_tv);

        zone_art_iv = (NetworkImageView) itemView.findViewById(R.id.zone_art_iv);
        zone_art_tv = (TextView) itemView.findViewById(R.id.zone_art_tv);

        zone_fitment_iv = (NetworkImageView) itemView.findViewById(R.id.zone_fitment_iv);
        zone_fitment_tv = (TextView) itemView.findViewById(R.id.zone_fitment_tv);

        zone_home_iv = (NetworkImageView) itemView.findViewById(R.id.zone_home_iv);
        zone_home_tv = (TextView) itemView.findViewById(R.id.zone_home_tv);

        zone_drink_iv = (NetworkImageView) itemView.findViewById(R.id.zone_drink_iv);
        zone_drink_tv = (TextView) itemView.findViewById(R.id.zone_drink_tv);

        zone_clothes_iv = (NetworkImageView) itemView.findViewById(R.id.zone_clothes_iv);
        zone_clothes_tv = (TextView) itemView.findViewById(R.id.zone_clothes_tv);

        zone_face_iv = (NetworkImageView) itemView.findViewById(R.id.zone_face_iv);
        zone_face_tv = (TextView) itemView.findViewById(R.id.zone_face_tv);

        zone_rest_iv = (NetworkImageView) itemView.findViewById(R.id.zone_rest_iv);
        zone_rest_tv = (TextView) itemView.findViewById(R.id.zone_rest_tv);

        networkImageViews.add(zone_phone_iv);
        networkImageViews.add(zone_art_iv);
        networkImageViews.add(zone_fitment_iv);
        networkImageViews.add(zone_home_iv);
        networkImageViews.add(zone_drink_iv);
        networkImageViews.add(zone_clothes_iv);
        networkImageViews.add(zone_face_iv);
        networkImageViews.add(zone_rest_iv);

        textViews.add(zone_phone_tv);
        textViews.add(zone_art_tv);
        textViews.add(zone_fitment_tv);
        textViews.add(zone_home_tv);
        textViews.add(zone_drink_tv);
        textViews.add(zone_clothes_tv);
        textViews.add(zone_face_tv);
        textViews.add(zone_rest_tv);

        requestNav();


    }

    /**
     * 首页导航列表
     */
    public void requestNav() {
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);

            OkGo.post(Urls.get_classlist)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<NavListBean>(niceZoneActivity) {
                        @Override
                        public void onSuccess(NavListBean navListBean, Call call, Response response) {
                            decodeNav(navListBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(niceZoneActivity, "数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeNav(NavListBean navListBean) {
        String code = navListBean.getErr_code();
        String msg = navListBean.getErr_msg();
        if (code.equals("200")) {
            list = navListBean.getData().getList();
            for (int i = 0; i < list.size(); i++) {
                //保存数据
                id.add(list.get(i).getId());
                names.add(list.get(i).getName());
                //
                NetworkManager.getInstance().setImageUrl(networkImageViews.get(i), list.get(i).getImages());
                textViews.get(i).setText(names.get(i));

                final int finili = i;
                if (i < childFirstCount)
                    childAtFirst.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ZoneSelectActivity.class);
                        intent.putExtra("shop_id", id.get(finili));
                        intent.putExtra("shop_name", names.get(finili));
                        mContext.startActivity(intent);
                    }
                });
                else
                    childAtTwo.getChildAt(i - childFirstCount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ZoneSelectActivity.class);
                        intent.putExtra("shop_id", id.get(finili));
                        intent.putExtra("shop_name", names.get(finili));
                        mContext.startActivity(intent);
                    }
                });



            }


        }
    }

    @Override
    public void init(int position) {

    }
}
