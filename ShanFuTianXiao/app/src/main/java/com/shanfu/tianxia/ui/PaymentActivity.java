package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BalancePwdPayBean;
import com.shanfu.tianxia.bean.BalancePwdPayTwoBean;
import com.shanfu.tianxia.bean.BankCardPaymentOneBean;
import com.shanfu.tianxia.bean.BankCardPaymentTwoBean;
import com.shanfu.tianxia.bean.BankCardPrepayBean;
import com.shanfu.tianxia.bean.BankCardPrepayTwoBean;
import com.shanfu.tianxia.bean.BankLianLianBean;
import com.shanfu.tianxia.bean.GetLLInfoBean;
import com.shanfu.tianxia.bean.MyReceivablesBean;
import com.shanfu.tianxia.bean.MyReceivablesTwoBean;
import com.shanfu.tianxia.bean.PayResultBean;
import com.shanfu.tianxia.bean.SaoMaCodeBean;
import com.shanfu.tianxia.bean.ShopRecommender;
import com.shanfu.tianxia.bean.SingleUserQueryBean;
import com.shanfu.tianxia.bean.VerifyPassWordBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.listener.StringDialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.PwdEditText;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.TimeCountUtil;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 付款
 */
public class PaymentActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "LOG";
    private RelativeLayout pay_ment_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.input_payment_money)
    EditText input_payment_money;
    @Bind(R.id.account_id)
    TextView account_id;
    @Bind(R.id.account_name)
    TextView account_name;
    @Bind(R.id.payment_button)
    Button payment_button;

    private String shopid, shopname;
    private TextView chang_pay_tv;
    private String money;

    private List<BankLianLianBean.DataBeanX.DataBean> list;
    private String balance;
    private ImageView change_pay_iv;
    private String bankdeltail;
    private TextView bank_detail;
    private Intent intent;
    private String dataname;
    private String no_agree;
    private String card_no;
    private String pay_type;
    private String bind_mob;
    private String vali_date;
    private String cvv2;
    private String bankname1;
    private String card_no1;
    private String no_agree1;
    private String pay_type1;
    private String bind_mob1;
    private String vali_date1;
    private String cvv21;
    private TextView money_tv;
    private String pwd1;
    private String pwd2;
    private String pwd_pay;
    private String tokens;
    private String code_pay;
    private String no_order;
    private String tokens_balance;
    private String no_order_balance;
    private String name_user;
    private String url1;
    private String person_id;
    private String person_name;
    private String person_money;
    private String person_user_id;
    private String person_token;
    private String person_no_order;
    private String shopname1;
    private String token_bank_person;
    private String no_order_bank_person;
    private String pwd;
    private String tui_auto_id;
    private String shoptid;
    private String shoptmoney;
    private String user_tid;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        shopid = getIntent().getStringExtra("shopid");
        shopname = getIntent().getStringExtra("shopname");
        name_user = getIntent().getStringExtra("name_user");
        url1 = getIntent().getStringExtra("url");



        requestInfo();
        //支付
        if (url1 != null){
            requestData();
        }
        //会员之间转账
        if (TextUtils.isEmpty(url1)){
            person_id = getIntent().getStringExtra("person_id");
            person_name = getIntent().getStringExtra("person_name");
            person_money = getIntent().getStringExtra("person_money");
            person_user_id = getIntent().getStringExtra("person_user_id");
            account_name.setText("账户名称: " + person_name);
            account_id.setText("帐户ID: " + person_id);
            if ("0.00".equals(person_money)){
                input_payment_money.setText("");
            }else {
                input_payment_money.setText(person_money);
            }
            if (!"0.00".equals(person_money)){
                input_payment_money.setFocusable(false);
            }
        }

        init();
        //余额
        requestBalance();
        //银行卡信息
        requestBank();
    }

    private void requestInfo() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);

            OkGo.post(Urls.getllinfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GetLLInfoBean>(this) {
                        @Override
                        public void onSuccess(GetLLInfoBean getLLInfoBean, Call call, Response response) {
                            decodeInfo(getLLInfoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeInfo(GetLLInfoBean getLLInfoBean) {
        String code = getLLInfoBean.getErr_code();
        String msg = getLLInfoBean.getErr_msg();

        if(!"200".equals(code)){
            TUtils.showShort(this,msg);

        }else {
            String user_id = getLLInfoBean.getData().getData().getUser_id();
            String name_user = getLLInfoBean.getData().getData().getName_user();
            String idcard = getLLInfoBean.getData().getData().getIdcard();
            SPUtils.getInstance().putString("user_id",user_id);
        }
    }

    private void requestData() {
        try {
            OkGo.get(url1)
                    .tag(this)
                    .execute(new DialogCallback<SaoMaCodeBean>(this) {
                        @Override
                        public void onSuccess(SaoMaCodeBean saoMaCodeBean, Call call, Response response) {
                            decodeCode(saoMaCodeBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeCode(SaoMaCodeBean saoMaCodeBean) {
        String name_user1 = saoMaCodeBean.getName_user();
        shopname1 = saoMaCodeBean.getShopname();
        if (TextUtils.isEmpty(name_user1)) {
            account_name.setText("账户名称: 联享客合作商户");
        } else {
            account_name.setText("账户名称: " + shopname1);
//            account_name.setText("账户名称: " + name_user1);
//            account_name.setText(name_user1);
        }
        account_id.setText("帐户ID: " + shopid);
    }

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
                            TUtils.showShort(PaymentActivity.this,"网络获取失败");
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
            bankname1 = list.get(list.size() - 1).getBankname();
            card_no1 = list.get(list.size() - 1).getCard_no();
            no_agree1 = list.get(list.size() - 1).getNo_agree();
            pay_type1 = list.get(list.size() - 1).getPay_type();
            bind_mob1 = list.get(list.size() - 1).getBind_mob();
            vali_date1 = list.get(list.size() - 1).getVali_date();
            cvv21 = list.get(list.size() - 1).getCvv2();

            String substring = card_no1.substring(card_no1.length() - 4, card_no1.length());
            bankdeltail = bankname1 + "(" + substring + ")";

        } else {
            TUtils.showShort(this, err_msg);
        }
    }

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
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

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

    private void init() {
        pay_ment_top = (RelativeLayout) findViewById(R.id.pay_ment_top);
        content_head_back = (RelativeLayout) pay_ment_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) pay_ment_top.findViewById(R.id.content_head_title);
        content_head_title.setText("付款");
        content_head_back.setOnClickListener(this);
        input_payment_money.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                //如果输入金额里包含小数点
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        input_payment_money.setText(s);
                        input_payment_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    input_payment_money.setText(s);
                    input_payment_money.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        input_payment_money.setText(s.subSequence(0, 1));
                        input_payment_money.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        payment_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_head_back:
                finish();
                break;
            case R.id.payment_button:
                money = input_payment_money.getText().toString().trim();
                float v1 = Float.parseFloat(money);
                if (TextUtils.isEmpty(money)) {
                    TUtils.showShort(PaymentActivity.this, "请输入付款金额");
                    return;
                }
                if (v1 <= 0.02){
                    TUtils.showShort(PaymentActivity.this,"扫码支付金额过小");
                    return;
                }
//                if (TextUtils.isEmpty(shopid)) {
//                    TUtils.showShort(PaymentActivity.this, "没有获取到帐户ID,请重新扫描");
//                    return;
//                }
//                requestData(money,shopid);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input_payment_money.getWindowToken(), 0);

                openPopup();

                break;
        }
    }
    //支付方式弹框
    private void openPopup() {
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_sao_pay_popup, null);
        Button close_btn = (Button) popupPayView.findViewById(R.id.close_btn);
        money_tv = (TextView) popupPayView.findViewById(R.id.money_tv);
        TextView name_tv = (TextView) popupPayView.findViewById(R.id.name_tv);
        RelativeLayout exchange_pay_rl = (RelativeLayout) popupPayView.findViewById(R.id.exchange_pay_rl);
        Button now_pay_btn = (Button) popupPayView.findViewById(R.id.now_pay_btn);
        change_pay_iv = (ImageView) popupPayView.findViewById(R.id.change_pay_iv);
        chang_pay_tv = (TextView) popupPayView.findViewById(R.id.chang_pay_tv);
        final PopupWindow popupWindow = new PopupWindow(popupPayView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        if (TextUtils.isEmpty(url1)){
            String substring_person_name = person_name.substring(person_name.length() - 1);
            name_tv.setText("向(**"+substring_person_name+")转账");
        }else {
            name_tv.setText("向("+shopname1+")转账");
            Log.i(TAG, "openPopup: "+shopname1);
        }
        money_tv.setText(money);
        chang_pay_tv.setText("余额支付"+"("+balance+")");

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        exchange_pay_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPupNext();
            }
        });
        now_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
                openPupPay();
            }
        });
    }
    //短信验证弹框
    private void openPupThrid() {
        View popupThrid = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_pay_thrid_popup,null);

        Button btn_close = (Button) popupThrid.findViewById(R.id.btn_close);
        TextView tishi_tv = (TextView) popupThrid.findViewById(R.id.tishi_tv);
        LinearLayout phone_ll  = (LinearLayout) popupThrid.findViewById(R.id.phone_ll);
        final TextView phone_ed = (TextView) popupThrid.findViewById(R.id.phone_ed);
        final EditText pay_code = (EditText) popupThrid.findViewById(R.id.pay_code);
//        final RelativeLayout code_send = (RelativeLayout) popupThrid.findViewById(R.id.code_send);
//        final TextView code_send_tv = (TextView) popupThrid.findViewById(R.id.code_send_tv);
        Button pay_btn = (Button) popupThrid.findViewById(R.id.pay_btn);
        final PopupWindow popupWindow = new PopupWindow(popupThrid,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent,Gravity.TOP,0,0);
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
        //TODO 判断手机号
        String yu_e = chang_pay_tv.getText().toString().trim();
        if (!yu_e.equals("余额支付"+"("+balance+")")){
            //会员之间转账
            if (TextUtils.isEmpty(url1)){
                if (dataname == null){
                    //默认银行卡
                    Log.i(TAG, "openPupThrid: "+ bind_mob);
                    phone_ll.setVisibility(View.GONE);
                    phone_ed.setText(bind_mob);
                }
                if (dataname != null){
                    //列表选择的银行卡信息
                    Log.i(TAG, "openPupThrid: "+bind_mob1);
                    phone_ll.setVisibility(View.GONE);
                    phone_ed.setText(bind_mob1);
                }
            }else if (dataname == null){
                //默认银行卡
                phone_ll.setVisibility(View.GONE);
                phone_ed.setText(bind_mob1);
            }else if (dataname != null){
                //列表银行卡
                phone_ll.setVisibility(View.GONE);
                phone_ed.setText(bind_mob);
            }

        }else {
            //TODO 余额支付
            if (TextUtils.isEmpty(url1)){
                //会员之间转账（余额支付，大于1000）
                String user_id = SPUtils.getInstance().getString("user_id", "");
                phone_ed.setText(user_id);
            }else {
                String user_id = SPUtils.getInstance().getString("user_id", "");
                phone_ed.setText(user_id);
            }
        }


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopup();
            }
        });
        /*code_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_num = phone_ed.getText().toString().trim();
                if (TextUtils.isEmpty(phone_num)||phone_num.length() != 11){
                    TUtils.showShort(PaymentActivity.this,"请填写手机号码");
                    return;
                }
                TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,code_send_tv,code_send);
                timeCountUtil.start();
                String yu_e = chang_pay_tv.getText().toString().trim();
                //TODO  银行卡支付第一步
                if (!yu_e.equals("余额支付"+"("+balance+")")){
                    if (dataname == null){
                        //默认银行卡
                        requestBankPay1();
                    }if (dataname != null){
                        //列表选择的银行卡信息
                        requestBankPay();
                    }
                    //会员之间转账
                    if (TextUtils.isEmpty(url1)){
                        if (dataname == null){
                            //默认银行卡
                            requestBankPaymentOne1();
                        }
                        if (dataname != null){
                            //列表选择的银行卡信息
                            requestBankPaymentOne();
                        }
                    }
                }else {
                    //TODO 余额支付（大于1000）第一步
                    if (TextUtils.isEmpty(url1)){
                        //会员之间转账（余额支付，大于1000）
                        requsetReceivables();
                    }else {
                        requsetBalancePay();
                    }
                }
            }
        });*/
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_num = phone_ed.getText().toString().trim();
                code_pay = pay_code.getText().toString().trim();
                if (TextUtils.isEmpty(phone_num)||phone_num.length() != 11){
                    TUtils.showShort(PaymentActivity.this,"请填写手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code_pay)|| code_pay.length() != 6){
                    TUtils.showShort(PaymentActivity.this,"请输入正确验证码");
                    return;
                }
                String yu_e = chang_pay_tv.getText().toString().trim();
                //TODO  银行卡支付（大于1000）第二步
                if (!yu_e.equals("余额支付"+"("+balance+")")){
                    //请求服务器
                    if (TextUtils.isEmpty(url1)){
                        //会员之间转账
                        requestBankPaymentTwo();
                    }else {
                        requestPayTwo();
                    }


                }else {
                    //TODO 余额支付（大于1000）第二步
                    //请求服务器
                    if (TextUtils.isEmpty(url1)){
                        //会员之间转账（余额支付，大于1000,第二步）
                        requsetReceivablesTwo();

                    }else {
                        requestBalancePayTwo();
                    }
                }
                popupWindow.dismiss();
            }
        });
    }
    //银行卡支付第二步
    private void requestBankPaymentTwo() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", person_id);
            params.put("user_id", person_user_id);
            params.put("fuid",uid);

            params.put("tokens",token_bank_person);
            params.put("money_order",money);
            params.put("no_order",no_order_bank_person);
            params.put("fqid",user_id);
            params.put("verify_code",code_pay);

            OkGo.post(Urls.bankcardpaymenttwo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardPaymentTwoBean>(this) {
                        @Override
                        public void onSuccess(BankCardPaymentTwoBean bankCardPaymentTwoBean, Call call, Response response) {
                            decodeBankCardPaymentTwo(bankCardPaymentTwoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void decodeBankCardPaymentTwo(BankCardPaymentTwoBean bankCardPaymentTwoBean) {
        String err_code = bankCardPaymentTwoBean.getErr_code();
        String err_msg = bankCardPaymentTwoBean.getErr_msg();
        String ret_code = bankCardPaymentTwoBean.getData().getData().getRet_code();
        String ret_msg = bankCardPaymentTwoBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){
            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            startActivity(intent);
            finish();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    private void requsetReceivablesTwo() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", person_id);
            params.put("user_id", person_user_id);
            params.put("fuid",uid);

            params.put("no_order",person_no_order);
            params.put("verify_code",code_pay);
            params.put("tokens",person_token);
            params.put("fqid",user_id);
            if ("0.00".equals(person_money)){
                params.put("money_order",money);
            }else
                params.put("money_order",person_money);

            OkGo.post(Urls.myreceivables_two)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MyReceivablesTwoBean>(this) {
                        @Override
                        public void onSuccess(MyReceivablesTwoBean myReceivablesTwoBean, Call call, Response response) {
                            decodeReceivablesTwo(myReceivablesTwoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeReceivablesTwo(MyReceivablesTwoBean myReceivablesTwoBean) {
        String err_code = myReceivablesTwoBean.getErr_code();
        String err_msg = myReceivablesTwoBean.getErr_msg();
        String ret_code = myReceivablesTwoBean.getData().getData().getRet_code();
        String ret_msg = myReceivablesTwoBean.getData().getData().getRet_msg();

        if ("200".equals(err_code)||"0000".equals(ret_code)){
            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("money",money);
            startActivity(intent);
            finish();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    private void requestBalancePayTwo() {
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

            params.put("shopid",shopid);
            params.put("no_order",no_order_balance);
            params.put("money_order",money);
            params.put("verify_code",code_pay);
            params.put("tokens",tokens_balance);

            OkGo.post(Urls.balancepwdpay_two)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BalancePwdPayTwoBean>(this) {
                        @Override
                        public void onSuccess(BalancePwdPayTwoBean balancePwdPayTwoBean, Call call, Response response) {
                            decodeBalancePayTwo(balancePwdPayTwoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBalancePayTwo(BalancePwdPayTwoBean balancePwdPayTwoBean) {
        String err_code = balancePwdPayTwoBean.getErr_code();
        String err_msg = balancePwdPayTwoBean.getErr_msg();
        String ret_code = balancePwdPayTwoBean.getData().getData().getRet_code();
        String ret_msg = balancePwdPayTwoBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){

            String shopspeople_redmoney = balancePwdPayTwoBean.getData().getShopspeople_redmoney();
            String redid = balancePwdPayTwoBean.getData().getRedid();
            String ticketnum = balancePwdPayTwoBean.getData().getTicketnum();
            String money_order = balancePwdPayTwoBean.getData().getData().getMoney_order();

            String shopstpeople = balancePwdPayTwoBean.getData().getShopstpeople();
            tui_auto_id = balancePwdPayTwoBean.getData().getTui_auto_id();
            shoptid = balancePwdPayTwoBean.getData().getShoptid();
            shoptmoney = balancePwdPayTwoBean.getData().getShoptmoney();
            user_tid = balancePwdPayTwoBean.getData().getUser_tid();

            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            intent.putExtra("money_order",money_order);
            intent.putExtra("url","领取红包");
            intent.putExtra("shopspeople_redmoney",shopspeople_redmoney);
            intent.putExtra("redid",redid);
            intent.putExtra("ticketnum",ticketnum);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            if ("yes".equals(shopstpeople)){
                requestShopRecommender();
            }

        }else {
            TUtils.showShort(this,ret_msg);
        }

    }

    private void requestPayTwo() {
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

            params.put("tokens",tokens);
            params.put("money_order",money);
            params.put("verify_code",code_pay);
            params.put("no_order",no_order);
            params.put("shopid",shopid);

            OkGo.post(Urls.bankcardprepay_two)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardPrepayTwoBean>(this) {
                        @Override
                        public void onSuccess(BankCardPrepayTwoBean bankCardPrepayTwoBean, Call call, Response response) {
                            decodeBankCardpayTwo(bankCardPrepayTwoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBankCardpayTwo(BankCardPrepayTwoBean bankCardPrepayTwoBean) {
        String err_code = bankCardPrepayTwoBean.getErr_code();
        String err_msg = bankCardPrepayTwoBean.getErr_msg();
        String ret_code = bankCardPrepayTwoBean.getData().getData().getRet_code();
        String ret_msg = bankCardPrepayTwoBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){

            String shopspeople_redmoney = bankCardPrepayTwoBean.getData().getShopspeople_redmoney();
            String redid = bankCardPrepayTwoBean.getData().getRedid();
            String ticketnum = bankCardPrepayTwoBean.getData().getTicketnum();
            String money_order = bankCardPrepayTwoBean.getData().getData().getMoney_order();

            String shopstpeople = bankCardPrepayTwoBean.getData().getShopstpeople();
            tui_auto_id = bankCardPrepayTwoBean.getData().getTui_auto_id();
            shoptid = bankCardPrepayTwoBean.getData().getShoptid();
            shoptmoney = bankCardPrepayTwoBean.getData().getShoptmoney();
            user_tid = bankCardPrepayTwoBean.getData().getUser_tid();

            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            intent.putExtra("money_order",money_order);
            intent.putExtra("url","领取红包");
            intent.putExtra("shopspeople_redmoney",shopspeople_redmoney);
            intent.putExtra("redid",redid);
            intent.putExtra("ticketnum",ticketnum);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            if ("yes".equals(shopstpeople)){
                requestShopRecommender();
            }

        }else {
            TUtils.showShort(this,ret_msg);
        }
    }
    //默认银行卡第一步
    private void requestBankPay1(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params1 = new HttpParams();
            params1.put("time", time);
            params1.put("token", token);
            params1.put("uid", uid);
            params1.put("user_id", user_id);

            params1.put("shopid",shopid);
            params1.put("no_agree",no_agree1);
            params1.put("pwd_pay",pwd);
            params1.put("money_order",money);
            params1.put("card_no",card_no1);
            params1.put("pay_type",pay_type1);
            params1.put("bind_mob",bind_mob1);
            params1.put("vali_mob",vali_date1);
            params1.put("cvv2",cvv21);

            OkGo.post(Urls.bankcardprepay)
                    .tag(this)
                    .params(params1)
                    .execute(new DialogCallback<BankCardPrepayBean>(this) {
                        @Override
                        public void onSuccess(BankCardPrepayBean bankCardPrepayBean, Call call, Response response) {
                            decodeBankpay(bankCardPrepayBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //银行卡支付第一步
    private void requestBankPay() {
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

            params.put("shopid",shopid);
            params.put("no_agree",no_agree);
            params.put("pwd_pay",pwd);
            params.put("money_order",money);
            params.put("card_no",card_no);
            params.put("pay_type",pay_type);
            params.put("bind_mob",bind_mob);
            params.put("vali_mob",vali_date);
            params.put("cvv2",cvv2);

            OkGo.post(Urls.bankcardprepay)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardPrepayBean>(this) {
                        @Override
                        public void onSuccess(BankCardPrepayBean bankCardPrepayBean, Call call, Response response) {
                            decodeBankpay(bankCardPrepayBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void decodeBankpay(BankCardPrepayBean bankCardPrepayBean) {
        String err_code = bankCardPrepayBean.getErr_code();
        String err_msg = bankCardPrepayBean.getErr_msg();
        String ret_code = bankCardPrepayBean.getData().getData().getRet_code();
        String ret_msg = bankCardPrepayBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){
            String shopspeople_redmoney = bankCardPrepayBean.getData().getShopspeople_redmoney();
            String redid = bankCardPrepayBean.getData().getRedid();
            String ticketnum = bankCardPrepayBean.getData().getTicketnum();
            String money_order = bankCardPrepayBean.getData().getData().getMoney_order();

            String shopstpeople = bankCardPrepayBean.getData().getShopstpeople();
            tui_auto_id = bankCardPrepayBean.getData().getTui_auto_id();
            shoptid = bankCardPrepayBean.getData().getShoptid();
            shoptmoney = bankCardPrepayBean.getData().getShoptmoney();
            user_tid = bankCardPrepayBean.getData().getUser_tid();

            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            intent.putExtra("money_order",money_order);
            intent.putExtra("url","领取红包");
            intent.putExtra("shopspeople_redmoney",shopspeople_redmoney);
            intent.putExtra("redid",redid);
            intent.putExtra("ticketnum",ticketnum);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            if ("yes".equals(shopstpeople)){
                requestShopRecommender();
            }

        }else if ("200".equals(err_code) && "8888".equals(ret_code)){
            TUtils.showShort(this,"短信已下发，请注意查收");

            tokens = (String) bankCardPrepayBean.getData().getToken();
            no_order = bankCardPrepayBean.getData().getData().getNo_order();

            openPupThrid();
        }else {
            TUtils.showShort(PaymentActivity.this,ret_msg);
        }
    }
    //商铺推荐人领取
    private void requestShopRecommender() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",shoptid);
            params.put("user_tid", user_tid);
            params.put("tui_auto_id",tui_auto_id);
            params.put("money_order",shoptmoney);

            OkGo.post(Urls.shop_recommender)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ShopRecommender>(this) {
                        @Override
                        public void onSuccess(ShopRecommender shopRecommender, Call call, Response response) {
                            decodeShopRecommender(shopRecommender);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeShopRecommender(ShopRecommender shopRecommender) {
        String err_code = shopRecommender.getErr_code();
        String err_msg = shopRecommender.getErr_msg();
        String ret_code = shopRecommender.getData().getData().getRet_code();
        String ret_msg = shopRecommender.getData().getData().getRet_msg();

        if ("200".equals(err_code)){

        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    //支付密码弹框
    private void openPupPay() {
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
        pay_money_tv.setText(money+"元");
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                openPopup();
            }
        });
        pwd_edittext.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {

            @Override
            public void onInputFinish(String password) {
                pwd = pwd_edittext.getText().toString().trim();

                requestPassWord();
                popupWindow.dismiss();
            }

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
                            .execute(new DialogCallback<VerifyPassWordBean>(PaymentActivity.this) {
                                @Override
                                public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
                                    decodePassWord(verifyPassWordBean);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");
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
                        //会员之间转账（银行卡支付，小于1000）input_payment_money.isFocusable() == false
                        if (TextUtils.isEmpty(url1)){
                            if (dataname == null){
                                //默认银行卡
                                requestBankPaymentOne1();
                            }
                            if (dataname != null){
                                //列表选择的银行卡信息
                                requestBankPaymentOne();
                            }
                        }else if (dataname == null){ //请求服务器 银行卡支付第一步
                            //默认银行卡
                            requestBankPay1();
                        }else if (dataname != null){
                            //点击进入银行卡列表后的银行卡支付
                            requestBankPay();
                        }

                    }else {
                        //TODO 余额支付
                        //请求服务器 余额支付第一步 跳转成功与否页面
                        if (TextUtils.isEmpty(url1)) {
                            //会员之间转账
                            requsetReceivables();

                        } else {
                            float money_fl = Float.parseFloat(money);
                            float balance_fl = Float.parseFloat(balance);
                            if (money_fl > balance_fl) {
                                TUtils.showShort(PaymentActivity.this, "您的余额不足，请更换支付方式");
                                return;
                            }
                            requsetBalancePay();
                        }
                    }
                    /*float money1 = Float.parseFloat(PaymentActivity.this.money);
                    if (money1 >= 1000){
                        //TODO 金额大于1000的情况下，跳转到短信验证界面
                        popupWindow.dismiss();
                        openPupThrid();
                    }else {
                        popupWindow.dismiss();
                        //TODO 金额小于1000的情况下，输入完支付密码，判断是余额支付还是银行卡支付，请求各自接口，返回支付成功与否页面
                        String yu_e = chang_pay_tv.getText().toString().trim();
                        //TODO 银行卡支付（小于1000）
                        if (!yu_e.equals("余额支付"+"("+balance+")")){
                            //请求服务器 银行卡支付第一步 跳转成功与否页面
                            if (dataname == null){
                                //默认银行卡
                                requestBankPay1();
                            }
                            if (dataname != null){
                                //点击进入银行卡列表后的银行卡支付
                                requestBankPay();
                            }
                            //会员之间转账（银行卡支付，小于1000）input_payment_money.isFocusable() == false
                            if (TextUtils.isEmpty(url1)){
                                if (dataname == null){
                                    //默认银行卡
                                    requestBankPaymentOne1();
                                }
                                if (dataname != null){
                                    //列表选择的银行卡信息
                                    requestBankPaymentOne();
                                }
                            }
                        }else {
                            //TODO 余额支付（小于1000）
                            //请求服务器 余额支付第一步 跳转成功与否页面
                            if (TextUtils.isEmpty(url1)){
                                //会员之间转账（余额支付，小于1000）
                                requsetReceivables();

                            }else {
                                float money_fl = Float.parseFloat(money);
                                float balance_fl = Float.parseFloat(balance);
                                if (money_fl > balance_fl){
                                    TUtils.showShort(PaymentActivity.this,"您的余额不足，请更换支付方式");
                                    return;
                                }
                                requsetBalancePay();
                            }

                        }
                    };*/
                }else {
                    TUtils.showShort(PaymentActivity.this, err_msg);
                }
            }
        });
    }

//    private void requestPassWord() {
//        String time = DateUtils.getLinuxTime();
//        String token = MD5Utils.MD5(Constants.appKey + time);
//        String uid = SPUtils.getInstance().getString("uid", "");
//        String user_id = SPUtils.getInstance().getString("user_id", "");
//
//        HttpParams params = new HttpParams();
//        params.put("time", time);
//        params.put("token", token);
//        params.put("uid", uid);
//        params.put("user_id", user_id);
//
//        params.put("userpwd",pwd);
//
//        OkGo.post(Urls.verify_password)
//                .tag(this)
//                .params(params)
//                .execute(new DialogCallback<VerifyPassWordBean>(this) {
//                    @Override
//                    public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
//                        decodePassWord(verifyPassWordBean);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });
//    }
//
//    private void decodePassWord(VerifyPassWordBean verifyPassWordBean) {
//        String err_code = verifyPassWordBean.getErr_code();
//        String err_msg = verifyPassWordBean.getErr_msg();
//        if ("200".equals(err_code)) {
//            TUtils.showShort(this, err_code);
//            TUtils.showShort(this, err_msg);
//
//
//        }else {
//            TUtils.showShort(this, err_code);
//            TUtils.showShort(this, err_msg);
//        }
//    }

    private void requestBankPaymentOne() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", person_id);
            params.put("user_id", person_user_id);
            params.put("fuid",uid);

            params.put("fqid",user_id);
            params.put("no_agree",no_agree);
            params.put("pwd_pay",pwd);
            params.put("money_order",money);
            params.put("card_no",card_no);
            params.put("pay_type",pay_type);
            params.put("bind_mob",bind_mob);
            params.put("vali_date",vali_date);
            params.put("cvv2",cvv2);

            OkGo.post(Urls.bankcardpaymentone)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardPaymentOneBean>(this) {
                        @Override
                        public void onSuccess(BankCardPaymentOneBean bankCardPaymentOneBean, Call call, Response response) {
                            decodeBankCardPaymentOne(bankCardPaymentOneBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestBankPaymentOne1() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", person_id);
            params.put("user_id", person_user_id);

            params.put("fuid",uid);
            params.put("fqid",user_id);
            params.put("no_agree",no_agree1);
            params.put("pwd_pay",pwd);
            params.put("money_order",money);
            params.put("card_no",card_no1);
            params.put("pay_type",pay_type1);
            params.put("bind_mob",bind_mob1);
            params.put("vali_date",vali_date1);
            params.put("cvv2",cvv21);

            OkGo.post(Urls.bankcardpaymentone)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardPaymentOneBean>(this) {
                        @Override
                        public void onSuccess(BankCardPaymentOneBean bankCardPaymentOneBean, Call call, Response response) {
                            decodeBankCardPaymentOne(bankCardPaymentOneBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBankCardPaymentOne(BankCardPaymentOneBean bankCardPaymentOneBean) {
        String err_code = bankCardPaymentOneBean.getErr_code();
        String err_msg = bankCardPaymentOneBean.getErr_msg();
        String ret_code = bankCardPaymentOneBean.getData().getData().getRet_code();
        String ret_msg = bankCardPaymentOneBean.getData().getData().getRet_msg();
        if ("200".equals(err_code) && "0000".equals(ret_code)){
            //TODO 跳转页面
            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if ("200".equals(err_code) && "8888".equals(ret_code)){
            TUtils.showShort(this,"短信已下发，请注意查收");

            token_bank_person = bankCardPaymentOneBean.getData().getData().getToken();
            no_order_bank_person = bankCardPaymentOneBean.getData().getData().getNo_order();

            openPupThrid();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }


    private void requsetReceivables() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", person_id);
            params.put("user_id", person_user_id);
            params.put("fuid",uid);

            params.put("fqid",user_id);
            params.put("busi_partner","0");
            params.put("pwd_pay",pwd);
            if ("0.00".equals(person_money)){
                params.put("money_order",money);
            }else
            params.put("money_order",person_money);

            OkGo.post(Urls.myreceivables)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MyReceivablesBean>(this) {
                        @Override
                        public void onSuccess(MyReceivablesBean myReceivablesBean, Call call, Response response) {
                            decodeReceivables(myReceivablesBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeReceivables(MyReceivablesBean myReceivablesBean) {
        String err_code = myReceivablesBean.getErr_code();
        String err_msg = myReceivablesBean.getErr_msg();
        String ret_code = myReceivablesBean.getData().getData().getRet_code();
        String ret_msg = myReceivablesBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){
            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if ("200".equals(err_code) && "8888".equals(ret_code)){
            TUtils.showShort(this,"短信已下发，请注意查收");

            //TODO  捕获tokens---未完成
            person_token = myReceivablesBean.getData().getData().getToken();
            person_no_order = myReceivablesBean.getData().getData().getNo_order();

            openPupThrid();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    //余额支付 小于1000
    private void requsetBalancePay() {
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

            params.put("shopid",shopid);
            params.put("busi_partner","0");
            params.put("pwd_pay",pwd);
            params.put("money_order",money);

            OkGo.post(Urls.balancepwdpay)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BalancePwdPayBean>(this) {
                        @Override
                        public void onSuccess(BalancePwdPayBean balancePwdPayBean, Call call, Response response) {
                            decodeBalancePwdpay(balancePwdPayBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBalancePwdpay(BalancePwdPayBean balancePwdPayBean) {
        String err_code = balancePwdPayBean.getErr_code();
        String err_msg = balancePwdPayBean.getErr_msg();
        String ret_code = balancePwdPayBean.getData().getData().getRet_code();
        String ret_msg = balancePwdPayBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){

            String shopspeople_redmoney = balancePwdPayBean.getData().getShopspeople_redmoney();
            String redid = balancePwdPayBean.getData().getRedid();
            String ticketnum = balancePwdPayBean.getData().getTicketnum();
            String money_order = balancePwdPayBean.getData().getData().getMoney_order();

            String shopstpeople = balancePwdPayBean.getData().getShopstpeople();
            tui_auto_id = balancePwdPayBean.getData().getTui_auto_id();
            shoptid = balancePwdPayBean.getData().getShoptid();
            shoptmoney = balancePwdPayBean.getData().getShoptmoney();
            user_tid = balancePwdPayBean.getData().getUser_tid();

            intent = new Intent(PaymentActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            intent.putExtra("money_order",money_order);
            intent.putExtra("url","领取红包");
            intent.putExtra("shopspeople_redmoney",shopspeople_redmoney);
            intent.putExtra("redid",redid);
            intent.putExtra("ticketnum",ticketnum);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            if ("yes".equals(shopstpeople)){
                requestShopRecommender();
            }

        }else if ("200".equals(err_code) && "8888".equals(ret_code)){
            TUtils.showShort(this,"短信已下发，请注意查收");
            tokens_balance = balancePwdPayBean.getData().getData().getToken();
            no_order_balance = balancePwdPayBean.getData().getData().getNo_order();

            openPupThrid();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    //选择支付方式弹框
    private void openPupNext() {
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_sao_pay_next_popup, null);
        Button close_btn = (Button) popupPayView.findViewById(R.id.close_btn);
        final RadioButton rbt_wallet = (RadioButton) popupPayView.findViewById(R.id.rbt_wallet);
        final RadioButton rbt_bank = (RadioButton) popupPayView.findViewById(R.id.rbt_bank);
        bank_detail = (TextView) popupPayView.findViewById(R.id.bank_detail);
        TextView wallet_tv = (TextView) popupPayView.findViewById(R.id.wallet_tv);
        final RelativeLayout wechart_pay_rl = (RelativeLayout) popupPayView.findViewById(R.id.wechart_pay_rl);

        final PopupWindow popupWindow = new PopupWindow(popupPayView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                chang_pay_tv.setText("余额支付"+"("+balance+")");

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
                        money_tv.setText(money);
                        Log.e("LOG","111");
                    }else {
                        chang_pay_tv.setText(dataname);
                        Log.e("LOG","222");
                    }
            }
        });
        wechart_pay_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(PaymentActivity.this,TopSelectBankActivity.class);
                intent.putExtra("code", 6);
                startActivityForResult(intent, 106);
                if ("3".equals(pay_type)){
                    TUtils.showShort(PaymentActivity.this,"信用卡不能转账或提现");
                    return;
                }
                bank_detail.setText(dataname);
            }
        });

    }

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

    private void requestData(String money, String shopid) {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(PaymentActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);
            params.put("money", money);
            params.put("tid", shopid);


            OkGo.post(Urls.Pay)
                    .tag(this)
                    .params(params)
                    .execute(new StringDialogCallback(this) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            PayResultBean payResultBean;
                            if (s.startsWith("\uFEFF\uFEFF")) {
                                String result = s.split("\uFEFF\uFEFF")[1];
//                                Log.e("LOG",result);
                                Gson gson = new Gson();
                                payResultBean = gson.fromJson(result, PayResultBean.class);
                            } else {
                                Gson gson = new Gson();
                                payResultBean = gson.fromJson(s, PayResultBean.class);
                            }
                            decodeResult(payResultBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(PaymentActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String url;

    private void decodeResult(PayResultBean payResultBean) {
        String code = payResultBean.getErr_code();
        String msg = payResultBean.getErr_msg();
        if ("200".equals(code)) {
            SPUtils.getInstance().putString("ptoken", payResultBean.getData().getPtoken());
            url = payResultBean.getData().getUrl();
            Intent intent = new Intent(PaymentActivity.this, PayWebViewActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        } else {
            TUtils.showShort(PaymentActivity.this, msg);
        }
    }
}
