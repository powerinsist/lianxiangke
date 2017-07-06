package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.utils.PwdEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZoneFrimOrderActivity extends BaseFragmentActivity implements View.OnClickListener {
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.shouhuoren_tv)
    TextView shouhuoren_tv;
    @Bind(R.id.phone_tv)
    TextView phone_tv;
    @Bind(R.id.shop_adress_ll)
    LinearLayout shop_adress_ll;
    @Bind(R.id.shopaddress_tv)
    TextView shopaddress_tv;
    @Bind(R.id.shop_pic_im)
    NetworkImageView shop_pic_im;
    @Bind(R.id.shop_name_tv)
    TextView shop_name_tv;
    @Bind(R.id.lxp_tv)
    TextView lxp_tv;
    @Bind(R.id.shop_count_tv)
    TextView shop_count_tv;
    @Bind(R.id.kuaidi_fangshi_tv)
    TextView kuaidi_fangshi_tv;
    @Bind(R.id.gongji_count_tv)
    TextView gongji_count_tv;
    @Bind(R.id.xufu_lxp_tv)
    TextView xufu_lxp_tv;
    @Bind(R.id.query_duihuan_tv)
    TextView query_duihuan_tv;
    private ImageView change_pay_iv;
    private TextView chang_pay_tv;
    private Intent intent;
    private String name;
    private String red;
    private String now_price;
    private String shipping;
    private String shipping_price;
    private String count;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_frim_order);
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");
        red = getIntent().getStringExtra("red");
        now_price = getIntent().getStringExtra("now_price");
        shipping = getIntent().getStringExtra("shipping");
        shipping_price = getIntent().getStringExtra("shipping_price");
        count = getIntent().getStringExtra("count");
        image = getIntent().getStringExtra("image");
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("确认订单");

        NetworkManager.getInstance().setImageUrl(shop_pic_im,image);
        shop_name_tv.setText(name);
        lxp_tv.setText(red+"张联享票+"+now_price+"元");
        shop_count_tv.setText("X"+count);
        if (shipping.equals("1")){
            kuaidi_fangshi_tv.setText("快递：免费");
        }else {
            kuaidi_fangshi_tv.setText("运费："+shipping_price+"元");
        }
        gongji_count_tv.setText("共"+count+"件商品");
        xufu_lxp_tv.setText(red+"张联享票+"+now_price+"元");

        content_head_back.setOnClickListener(this);
        query_duihuan_tv.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.query_duihuan_tv:
                openPopup();
                break;
        }
    }

    private void openPopup(){
        final View popView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_zone_pay_popup,null);
        Button close_btn = (Button) popView.findViewById(R.id.close_btn);
        TextView money_tv = (TextView) popView.findViewById(R.id.money_tv);
        RelativeLayout exchange_pay_rl = (RelativeLayout) popView.findViewById(R.id.exchange_pay_rl);
        change_pay_iv = (ImageView) popView.findViewById(R.id.change_pay_iv);
        chang_pay_tv = (TextView) popView.findViewById(R.id.chang_pay_tv);
        TextView pay_name_tv = (TextView) popView.findViewById(R.id.pay_name_tv);
        Button now_pay_btn = (Button) popView.findViewById(R.id.now_pay_btn);

        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置点击外层关闭popupwindow
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        popupWindow.update();
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        exchange_pay_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopupNext();
            }
        });
        now_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopupPay();
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    private void openPopupNext(){
        final View popupView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_zone_popupwindow,null);
        RelativeLayout wallet_pay_rl = (RelativeLayout) popupView.findViewById(R.id.wallet_pay_rl);
        RelativeLayout wechart_pay_rl = (RelativeLayout) popupView.findViewById(R.id.wechart_pay_rl);
        RelativeLayout select_bank_rl = (RelativeLayout) popupView.findViewById(R.id.select_bank_rl);
        RadioButton rbt_wallet = (RadioButton) popupView.findViewById(R.id.rbt_wallet);
        RadioButton rbt_wechart = (RadioButton) popupView.findViewById(R.id.rbt_wechart);
        Button close_btn = (Button) popupView.findViewById(R.id.close_btn);

        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        popupWindow.update();
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopup();
            }
        });
        rbt_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopup();
                change_pay_iv.setImageResource(R.mipmap.myticket_exchange_payway_moncon);
                chang_pay_tv.setText("余额支付");
            }
        });
        rbt_wechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopup();
                change_pay_iv.setImageResource(R.mipmap.myticket_exchange_payway_wechat);
                chang_pay_tv.setText("微信支付");
            }
        });
        select_bank_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ZoneFrimOrderActivity.this,BindingBankCardActivity.class);
                startActivity(intent);
            }
        });
    }
    private void openPopupPay(){
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_pay_popup,null);
        Button btn_close = (Button) popupPayView.findViewById(R.id.btn_close);
        TextView pay_money_tv = (TextView) popupPayView.findViewById(R.id.pay_money_tv);
        PwdEditText pwd_edittext = (PwdEditText) popupPayView.findViewById(R.id.pwd_edittext);
        final PopupWindow popupWindow = new PopupWindow(popupPayView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent,Gravity.CENTER,0,-100);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopupNext();
            }
        });
        pwd_edittext.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                popupWindow.dismiss();
                intent = new Intent(ZoneFrimOrderActivity.this,ZonePaymentSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
}
