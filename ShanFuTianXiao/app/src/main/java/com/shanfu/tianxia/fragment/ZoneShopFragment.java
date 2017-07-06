package com.shanfu.tianxia.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ZoneGridAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragment;
import com.shanfu.tianxia.bean.GoodsDetailsBean;
import com.shanfu.tianxia.bean.ZoneImageUrlBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.ZoneFrimOrderActivity;
import com.shanfu.tianxia.ui.ZoneGoodsDetailsActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xuchenchen on 2017/5/11.
 */

public class ZoneShopFragment extends Fragment implements View.OnClickListener{

    private View view;
    private AutoScrollViewPager scrollViewPager;
    private CirclePageIndicator indicator;
    private TextView zone_grid_shop_tv;
    private TextView zone_grid_lxp_tv;
    private TextView yuanjia_tv;
    private TextView kucun_tv;
    private TextView yiduihuan_tv;
    private TextView lijiduihuan_tv;

    private Context context;
    private NetworkImageView shop_pic_iv;
    private TextView lxp_count_tv;
    private Button minus_btn;
    private Button add_btn;
    private TextView count_tv;
    private TextView query_tv;

    private int amount = 1;
    private String TAG = "LOG";
    private String goods_id;
    private String shop_id;
    private GoodsDetailsBean.DataBean.GoodDetailBean.ImagesBean images;
    private String name;
    private String price;
    private String now_price;
    private String red;
    private String shipping;
    private String shipping_price;
    private TextView peisong_tv;
    private String quantity;
    private String sold;

    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewContainer;
    private static final int AUTO_SCROLL_INTERVAL = 3000;
    private ArrayList<String> urls;
    private String image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shop_details_zone, null);
        goods_id = getArguments().getString("goods_id");
        shop_id = getArguments().getString("shop_id");

        init();
        requestDetails();
        return view;
    }
    private void init(){
        scrollViewPager = (AutoScrollViewPager) view.findViewById(R.id.zone_viewPager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.zone_indicator);
        zone_grid_shop_tv = (TextView) view.findViewById(R.id.zone_grid_shop_tv);
        zone_grid_lxp_tv = (TextView) view.findViewById(R.id.zone_grid_lxp_tv);
        yuanjia_tv = (TextView) view.findViewById(R.id.yuanjia_tv);
        kucun_tv = (TextView) view.findViewById(R.id.kucun_tv);
        yiduihuan_tv = (TextView) view.findViewById(R.id.yiduihuan_tv);
        lijiduihuan_tv = (TextView) view.findViewById(R.id.lijiduihuan_tv);
        peisong_tv = (TextView) view.findViewById(R.id.peisong_tv);
        lijiduihuan_tv.setOnClickListener(this);
    }

    /**
     * 商品详情
     */
    public void requestDetails() {
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("goods_id",goods_id);
            params.put("shop_id",shop_id);
            params.put("uid",uid);


            OkGo.post(Urls.gooddetail)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GoodsDetailsBean>(getActivity()) {
                        @Override
                        public void onSuccess(GoodsDetailsBean goodsDetailsBean, Call call, Response response) {
                            decodeHotShop(goodsDetailsBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeHotShop(GoodsDetailsBean goodsDetailsBean) {
        String code = goodsDetailsBean.getErr_code();
        String msg = goodsDetailsBean.getErr_msg();
        if ("200".equals(code)) {
            name = goodsDetailsBean.getData().getGood_detail().getJiben().getName();
            price = goodsDetailsBean.getData().getGood_detail().getJiben().getPrice();
            now_price = goodsDetailsBean.getData().getGood_detail().getJiben().getNow_price();
            red = goodsDetailsBean.getData().getGood_detail().getJiben().getRed();
            quantity = goodsDetailsBean.getData().getGood_detail().getJiben().getQuantity();
            sold = goodsDetailsBean.getData().getGood_detail().getJiben().getSold();
            image = goodsDetailsBean.getData().getGood_detail().getJiben().getImage();
            String description = goodsDetailsBean.getData().getGood_detail().getJiben().getDescription();

            kucun_tv.setText("库存:"+quantity+"件");
            yiduihuan_tv.setText("已兑换"+sold+"件");
            /*0配送，1不配送*/
            shipping = goodsDetailsBean.getData().getGood_detail().getJiben().getShipping();
            if (shipping.equals("1")){
                peisong_tv.setText("快递：免运费");
            }else {
                peisong_tv.setText("快递："+shipping_price+"元");
            }
            shipping_price = goodsDetailsBean.getData().getGood_detail().getJiben().getShipping_price();
            zone_grid_shop_tv.setText(name);
            zone_grid_lxp_tv.setText(red+"张联享票"+"+"+now_price+"元");
            //中间加横线
            yuanjia_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            yuanjia_tv.setText("原价："+price+"元");

            GoodsDetailsBean.DataBean.GoodDetailBean.ImagesBean images = goodsDetailsBean.getData().getGood_detail().getImages();
            if (viewContainer == null) {
                viewContainer = new ArrayList<View>();
            }
            if (urls == null) {
                urls = new ArrayList<String>();
            }
            urls.clear();

            String images0 = images.getImage0();
            String images1 = images.getImage1();
            String images2 = images.getImage2();
            String images3 = images.getImage3();
            String images4 = images.getImage4();
            String images5 = images.getImage5();


            urls.add(images0);
            urls.add(images1);
            if (images2 != null){
                urls.add(images2);
            }
            if (images3 != null){
                urls.add(images3);
            }
            if (images4 != null){
                urls.add(images4);
            }
            if (images5 != null){
                urls.add(images5);
            }

            viewContainer.clear();
            for (String url : urls) {
                NetworkImageView iv = new NetworkImageView(getActivity().getApplicationContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                NetworkManager.getInstance().setImageUrl(iv, url);
                viewContainer.add(iv);
            }

            if (pagerAdapter == null) {
                pagerAdapter = new PagerAdapter() {

                    @Override
                    public void destroyItem(ViewGroup container, int position,
                                            Object object) {
                        ((AutoScrollViewPager) container).removeView(viewContainer
                                .get(position));
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        ((AutoScrollViewPager) container).addView(viewContainer
                                .get(position));
                        return viewContainer.get(position);
                    }

                    @Override
                    public boolean isViewFromObject(View arg0, Object arg1) {
                        return arg0 == arg1;
                    }

                    @Override
                    public int getCount() {
                        return viewContainer.size();
                    }
                };
            }

            scrollViewPager.setAdapter(pagerAdapter);

            indicator.setViewPager(scrollViewPager);
            scrollViewPager.setInterval(AUTO_SCROLL_INTERVAL);
            scrollViewPager.startAutoScroll();

        } else {
            TUtils.showShort(getActivity(), msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lijiduihuan_tv:
                openPopup();
                break;
        }
    }

    private void openPopup(){
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_selectcount_popup,null);
        shop_pic_iv = (NetworkImageView) popupView.findViewById(R.id.shop_pic_iv);
        lxp_count_tv = (TextView) popupView.findViewById(R.id.lxp_count_tv);
        minus_btn = (Button) popupView.findViewById(R.id.minus_btn);
        add_btn = (Button) popupView.findViewById(R.id.add_btn);
        count_tv = (TextView) popupView.findViewById(R.id.count_tv);
        query_tv = (TextView) popupView.findViewById(R.id.query_tv);
        final int kucun = Integer.parseInt(quantity);
        count_tv.setText(amount+"");
        NetworkManager.getInstance().setImageUrl(shop_pic_iv, image);
        lxp_count_tv.setText(red+"张联享票+"+now_price+"元");

        final PopupWindow popupWindow = new PopupWindow(popupView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = getActivity().findViewById(R.id.root_view_shop);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getActivity().getWindow().setAttributes(params);
            }
        });

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 1){
                    amount--;
                    count_tv.setText(amount+"");
                }
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (amount < kucun){
                    amount++;
                    count_tv.setText(amount+"");
                }
            }
        });
        query_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = count_tv.getText().toString().trim();
                Intent intent = new Intent(getActivity(), ZoneFrimOrderActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("red",red);
                intent.putExtra("now_price",now_price);
                intent.putExtra("shipping",shipping);
                intent.putExtra("shipping_price",shipping_price);
                intent.putExtra("count",count);
                intent.putExtra("image",image);
                startActivity(intent);
            }
        });
    }
}
