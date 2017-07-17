package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BankLianLianBean;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.bean.SingleUserQueryBean;
import com.shanfu.tianxia.bean.TicketUserBean;
import com.shanfu.tianxia.bean.VerifyPassWordBean;
import com.shanfu.tianxia.bean.ZoneSelectBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.PwdEditText;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class ZoneFrimOrderActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "LOG";
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
    private String number;
    private String balance;
    private List<BankLianLianBean.DataBeanX.DataBean> list;
    private String bankdeltail;
    private TextView bank_detail;
    private String dataname;
    private String no_agree;
    private String card_no;
    private String pay_type;
    private String bind_mob;
    private String vali_date;
    private String cvv2;
    private String pwd;

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
        requestBalance();
        requestBank();
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
                requestLxp();
                break;
        }
    }

    private void requestLxp() {
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);

            OkGo.post(Urls.ticketusers)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<TicketUserBean>(this) {
                        @Override
                        public void onSuccess(TicketUserBean ticketUserBean, Call call, Response response) {
                            decodeLxp(ticketUserBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ZoneFrimOrderActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeLxp(TicketUserBean ticketUserBean) {
        String code = ticketUserBean.getErr_code();
        String msg = ticketUserBean.getErr_msg();
        if (code.equals("200")){
            number = ticketUserBean.getData().getNumber();
            if (number.equals("0")){
                //TODO 测试调用popup 上线应删除
                openPopup();
                TUtils.showShort(this,"您还没有联巷票！");
            }else{
                openPopup();
            }

        }else {
            TUtils.showShort(this,msg);
        }
    }

    private void openPopup(){
        final View popView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_zone_pay_popup,null);
        Button close_btn = (Button) popView.findViewById(R.id.close_btn);
        TextView money_tv = (TextView) popView.findViewById(R.id.money_tv);
        RelativeLayout exchange_pay_rl = (RelativeLayout) popView.findViewById(R.id.exchange_pay_rl);
        change_pay_iv = (ImageView) popView.findViewById(R.id.change_pay_iv);
        chang_pay_tv = (TextView) popView.findViewById(R.id.chang_pay_tv);
//        TextView pay_name_tv = (TextView) popView.findViewById(R.id.pay_name_tv);
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

        money_tv.setText(red+"张联享票+"+now_price+"元");
        chang_pay_tv.setText("余额支付"+"("+balance+")");
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

        TextView wallet_tv = (TextView) popupView.findViewById(R.id.wallet_tv);
        bank_detail = (TextView) popupView.findViewById(R.id.bank_detail);
        RadioButton rbt_wallet = (RadioButton) popupView.findViewById(R.id.rbt_wallet);
        RadioButton rbt_bank = (RadioButton) popupView.findViewById(R.id.rbt_bank);
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
        wallet_tv.setText("余额支付"+"("+balance+")");
        Log.i(TAG, "openPopupNext: "+bankdeltail);
        bank_detail.setText(bankdeltail);

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
                chang_pay_tv.setText("余额支付"+"("+balance+")");
            }
        });
        wechart_pay_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ZoneFrimOrderActivity.this,TopSelectBankActivity.class);
                intent.putExtra("code", 17);
                startActivityForResult(intent, 117);
                if ("3".equals(pay_type)){
                    TUtils.showShort(ZoneFrimOrderActivity.this,"信用卡不能转账或提现");
                    return;
                }
                bank_detail.setText(dataname);
            }
        });
        rbt_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopup();
                change_pay_iv.setVisibility(View.GONE);
//                chang_pay_tv.setText(bankdeltail);
                if (dataname == null){
                    chang_pay_tv.setText(bankdeltail);
                    Log.e("LOG","111");
                }else {
                    chang_pay_tv.setText(dataname);
                    Log.e("LOG","222");
                }
            }
        });
    }
    private void openPopupPay(){
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_pay_popup,null);
        Button btn_close = (Button) popupPayView.findViewById(R.id.btn_close);
        TextView pay_money_tv = (TextView) popupPayView.findViewById(R.id.pay_money_tv);
        final PwdEditText pwd_edittext = (PwdEditText) popupPayView.findViewById(R.id.pwd_edittext);
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
        pay_money_tv.setText(red+"张联享票+"+now_price+"元");
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        pwd_edittext.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                pwd = pwd_edittext.getText().toString().trim();
                requestPassWord();

                popupWindow.dismiss();
                intent = new Intent(ZoneFrimOrderActivity.this,ZonePaymentSuccessActivity.class);
                startActivity(intent);
            }
        });
    }
    //账户余额
    private void requestBalance() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.singleuserquery)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SingleUserQueryBean>(this) {
                        @Override
                        public void onSuccess(SingleUserQueryBean singleUserQueryBean, Call call, Response response) {
                            decodeBalance(singleUserQueryBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ZoneFrimOrderActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBalance(SingleUserQueryBean singleUserQueryBean) {
        String err_code = singleUserQueryBean.getErr_code();
        String err_msg = singleUserQueryBean.getErr_msg();
        String ret_code = singleUserQueryBean.getData().getData().getRet_code();
        String ret_msg = singleUserQueryBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) || "0000".equals(ret_code)) {
            balance = singleUserQueryBean.getData().getData().getBalance();
        } else {
            TUtils.showShort(this, ret_msg);
        }
    }
    //银行卡列表
    private void requestBank() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.banklianlian)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankLianLianBean>(this) {
                        @Override
                        public void onSuccess(BankLianLianBean bankLianLianBean, Call call, Response response) {
                            decodeBank(bankLianLianBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ZoneFrimOrderActivity.this,"网络获取失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBank(BankLianLianBean bankLianLianBean) {
        String err_code = bankLianLianBean.getErr_code();
        String err_msg = bankLianLianBean.getErr_msg();
        list = new ArrayList<>();
        if ("200".equals(err_code)) {
            list = bankLianLianBean.getData().getData();
            String bankname1 = list.get(list.size() - 1).getBankname();
            String card_no1 = list.get(list.size() - 1).getCard_no();
            String no_agree1 = list.get(list.size() - 1).getNo_agree();
            String pay_type1 = list.get(list.size() - 1).getPay_type();
            String bind_mob1 = list.get(list.size() - 1).getBind_mob();
            String vali_date1 = list.get(list.size() - 1).getVali_date();
            String cvv21 = list.get(list.size() - 1).getCvv2();

            String substring = card_no1.substring(card_no1.length() - 4, card_no1.length());
            bankdeltail = bankname1 + "(" + substring + ")";

        } else {
            TUtils.showShort(this, err_msg);
        }
    }
    //支付密码
    private void requestPassWord() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            params.put("userpwd",pwd);

            OkGo.post(Urls.verify_password)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<VerifyPassWordBean>(ZoneFrimOrderActivity.this) {
                        @Override
                        public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
                            decodePassWord(verifyPassWordBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ZoneFrimOrderActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodePassWord(VerifyPassWordBean verifyPassWordBean) {
        String err_code = verifyPassWordBean.getErr_code();
        String err_msg = verifyPassWordBean.getErr_msg();

        if ("200".equals(err_code)) {
            String yu_e = chang_pay_tv.getText().toString().trim();
            //TODO 银行卡支付
            if (!yu_e.equals("余额支付"+"("+balance+")")){
                if (dataname == null){ //请求服务器 银行卡支付第一步
                    // 默认银行卡
                    //TODO 注释未打开
//                    requestBankPay1();
                }else if (dataname != null){
                    //点击进入银行卡列表后的银行卡支付
                    //TODO  注释未打开
//                    requestBankPay();
                }
            }else {
                //TODO 余额支付
                //TODO  注释未打开
//                requsetBalancePay();
            }
        }else {
            TUtils.showShort(ZoneFrimOrderActivity.this, err_msg);
        }
    }
    //默认银行卡第一步
    /*private void requestBankPay1() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);
            params.put("address_id",);
            params.put("goods_id",);
            params.put("red",);
            params.put("now_price",);
            params.put("quantity",);
            params.put("shipping",);
            params.put("shipping_price",);
            params.put("rednums",);
            params.put("shop_id",);
            params.put("total",);
            params.put("card_no",);
            params.put("acct_name",);
            params.put("pay_type",);
            params.put("bind_mob",);
            params.put("no_agree",);
            params.put("pwd_pay",);
            params.put("vali_date",);
            params.put("cvv2",);




            OkGo.post(Urls.bankpayone)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(ZoneFrimOrderActivity.this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeBankPay1(rsultBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ZoneFrimOrderActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBankPay1(RsultBean rsultBean) {
        String err_code = rsultBean.getErr_code();
        String err_msg = rsultBean.getErr_msg();
        if (err_code.equals("200")){
            TUtils.showShort(this,err_msg);
        }else {
            TUtils.showShort(this,err_msg);
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dataname = data.getStringExtra("name");
        no_agree = data.getStringExtra("no_agree");
        String bankName = data.getStringExtra("bankName");
        card_no = data.getStringExtra("card_no");
        pay_type = data.getStringExtra("pay_type");
        bind_mob = data.getStringExtra("bind_mob");
        vali_date = data.getStringExtra("vali_date");
        cvv2 = data.getStringExtra("cvv2");

        if (dataname.equals("请添加银行卡")) {
            //没有请求道网络数据
            Toast.makeText(this, "没有请求到网络：" + dataname, Toast.LENGTH_SHORT);
        } else {
            bank_detail.setText(dataname);
        }
    }
}
