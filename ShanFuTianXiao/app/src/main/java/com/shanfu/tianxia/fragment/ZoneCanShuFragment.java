package com.shanfu.tianxia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragment;
import com.shanfu.tianxia.bean.GoodsDetailsBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.ZoneFrimOrderActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xuchenchen on 2017/5/11.
 */

public class ZoneCanShuFragment extends Fragment implements View.OnClickListener{

    private String goods_id;
    private String shop_id;
    private View view;
    private WebView canshu_web;
    private TextView lijiduihuan_tv;
    private String red;
    private String now_price;
    private String quantity;
    private String image;
    private int amount = 1;
    private TextView count_tv;
    private String name;
    private String price;
    private String shipping;
    private String shipping_price;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_can_shu_zone,null,false);
        goods_id = getArguments().getString("goods_id");
        shop_id = getArguments().getString("shop_id");
        initView();
        requestDetails();
        return view;

    }

    private void initView() {
        canshu_web = (WebView) view.findViewById(R.id.canshu_web);
        lijiduihuan_tv = (TextView) view.findViewById(R.id.lijiduihuan_tv);
        lijiduihuan_tv.setOnClickListener(this);

        String weburl = "http://api.lianxiangke.cn/index.php?m=shop&c=shopdetail&a=getparameter&goods_id=";
        String url = weburl + goods_id;
        WebSettings settings = canshu_web.getSettings();
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 启用内置缩放装置
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setDefaultTextEncodingName("utf-8");
        canshu_web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        canshu_web.loadUrl(url);

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
            shipping = goodsDetailsBean.getData().getGood_detail().getJiben().getShipping();
            shipping_price = goodsDetailsBean.getData().getGood_detail().getJiben().getShipping_price();
            image = goodsDetailsBean.getData().getGood_detail().getJiben().getImage();

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
        NetworkImageView shop_pic_iv = (NetworkImageView) popupView.findViewById(R.id.shop_pic_iv);
        TextView lxp_count_tv = (TextView) popupView.findViewById(R.id.lxp_count_tv);
        Button minus_btn = (Button) popupView.findViewById(R.id.minus_btn);
        Button add_btn = (Button) popupView.findViewById(R.id.add_btn);
        count_tv = (TextView) popupView.findViewById(R.id.count_tv);
        TextView query_tv = (TextView) popupView.findViewById(R.id.query_tv);
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
                popupWindow.dismiss();
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
