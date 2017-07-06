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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BalanceRechargeBean;
import com.shanfu.tianxia.bean.BalanceRechargeTwoBean;
import com.shanfu.tianxia.bean.BankLianLianBean;
import com.shanfu.tianxia.bean.VerifyPassWordBean;
import com.shanfu.tianxia.listener.DialogCallback;
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

public class TopUpRechangeActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "TAG";
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.bank_celsct_rl)
    RelativeLayout bank_celsct_rl;
//    @Bind(R.id.bank_click_iv)
//    RadioButton bank_click_iv;
//    @Bind(R.id.wechart_name_tv)
//    TextView wechart_name_tv;
//    @Bind(R.id.wechart_click_iv)
//    RadioButton wechart_click_iv;
    @Bind(R.id.amount_entered)
    EditText amount_entered;
    @Bind(R.id.wallet_pay_next)
    Button wallet_pay_next;
    @Bind(R.id.bank_select_tv)
    TextView bank_select_tv;


    private Intent intent;
    private String money;
    private String no_agree;
    private String card_no;
    private String pay_type;
    private String bind_mob;
    private String vali_date;
    private String cvv2;
    private String pwd_pay;
    private String tokens;
    private String code_pay;
    private String phone_num;
    private String no_order;

    private String pwd;

    private List<BankLianLianBean.DataBeanX.DataBean> list;
    private String dataname;
    private String bankname1;
    private String card_no1;
    private String no_agree1;
    private String pay_type1;
    private String bind_mob1;
    private String vali_date1;
    private String cvv21;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_rechange);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("充值");
        content_head_back.setOnClickListener(this);

        bank_celsct_rl.setOnClickListener(this);
//        bank_click_iv.setOnClickListener(this);
//        wechart_click_iv.setOnClickListener(this);
        requestBank();

        amount_entered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果输入金额里包含小数点
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        amount_entered.setText(s);
                        amount_entered.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    amount_entered.setText(s);
                    amount_entered.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        amount_entered.setText(s.subSequence(0, 1));
                        amount_entered.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        wallet_pay_next.setOnClickListener(this);
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
                            bank_select_tv.setText("请添加银行卡");
//                            TUtils.showShort(TopUpRechangeActivity.this,"网络获取失败");
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
            String bankdeltail = bankname1 + "(" + substring + ")";
            bank_select_tv.setText(bankdeltail);
            Log.e("LOG","-------card_no-----------"+card_no1);

        } else {
            TUtils.showShort(this, err_msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击更换银行卡
            case R.id.bank_celsct_rl:
                if (bank_select_tv.getText().toString().equals("请添加银行卡")){
                    intent = new Intent(TopUpRechangeActivity.this,MyBankCardPwdActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(TopUpRechangeActivity.this, TopSelectBankActivity.class);
                intent.putExtra("code",0);
                startActivityForResult(intent,100);
                break;
            case R.id.wallet_pay_next:
                money = amount_entered.getText().toString().trim();
                String bank_select = bank_select_tv.getText().toString().trim();
                if (TextUtils.isEmpty(money)){
                    TUtils.showShort(this,"请输入充值金额");
                    return;
                }
                if (bank_select.equals("点击选择银行卡")){
                    TUtils.showShort(this,"请选择银行卡");
                    return;
                }
                if (dataname == null){
                    if ("3".equals(pay_type1)){
                        TUtils.showShort(TopUpRechangeActivity.this,"信用卡不能充值");
                        return;
                    }
                }
                if (dataname != null){
                    if ("3".equals(pay_type)){
                        TUtils.showShort(TopUpRechangeActivity.this,"信用卡不能充值");
                        return;
                    }
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(amount_entered.getWindowToken(), 0);

                openPopup();
                break;
            case R.id.content_head_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            dataname = data.getStringExtra("name");
            String name = data.getStringExtra("name");
            bank_select_tv.setText(name);
            no_agree = data.getStringExtra("no_agree");
            String bankName = data.getStringExtra("bankName");
            card_no = data.getStringExtra("card_no");
            pay_type = data.getStringExtra("pay_type");
            bind_mob = data.getStringExtra("bind_mob");
            vali_date = data.getStringExtra("vali_date");
            cvv2 = data.getStringExtra("cvv2");
    }

    private void openPopup(){
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_wallet_pay_popup,null);
        TextView select_tv = (TextView) popupPayView.findViewById(R.id.select_tv);
        select_tv.setText("充值");
        Button btn_close = (Button) popupPayView.findViewById(R.id.btn_close);
        TextView pay_money_tv = (TextView) popupPayView.findViewById(R.id.pay_money_tv);
        final PwdEditText pwd_edittext = (PwdEditText) popupPayView.findViewById(R.id.pwd_edittext);
        final PopupWindow popupWindow = new PopupWindow(popupPayView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.CENTER,0,-100);
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
        pay_money_tv.setText(money);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        pwd_edittext.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {

            private float moneyint;

            @Override
            public void onInputFinish(String password) {
//                pwd_pay = SPUtils.getInstance().getString("pwd_pay", "");
                moneyint = Float.parseFloat(money);
                pwd = pwd_edittext.getText().toString().trim();

                requestPassWord();

//                if (!pwd.equals(pwd_pay)){
//                    TUtils.showShort(TopUpRechangeActivity.this,"请输入正确支付密码");
//                    return;
//                }
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
                            .execute(new DialogCallback<VerifyPassWordBean>(TopUpRechangeActivity.this) {
                                @Override
                                public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
                                    decodePassWord(verifyPassWordBean);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    TUtils.showShort(TopUpRechangeActivity.this,"数据获取失败，请检查网络后重试");
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
//                    if (moneyint < 1000){
                        popupWindow.dismiss();
                        if (dataname == null){
                            Log.i(TAG, "decodePassWord: 默认"+dataname+"");
                            requestData1();
                        }
                        if (dataname != null){
                            Log.i(TAG, "decodePassWord: 选择"+dataname+"");
                            requestData();
                        }

//                    }
                    /*else {
                        //跳转到验证界面
                        popupWindow.dismiss();
                        openPupThrid();
                    }*/
                }else {
                    TUtils.showShort(TopUpRechangeActivity.this, err_msg);
                }
            }
        });
    }

    private void openPupThrid() {
        View popupThrid = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_pay_thrid_popup, null);

        Button btn_close = (Button) popupThrid.findViewById(R.id.btn_close);
        TextView tishi_tv = (TextView) popupThrid.findViewById(R.id.tishi_tv);
        final TextView phone_ed = (TextView) popupThrid.findViewById(R.id.phone_ed);
        final EditText pay_code = (EditText) popupThrid.findViewById(R.id.pay_code);
//        final RelativeLayout code_send = (RelativeLayout) popupThrid.findViewById(R.id.code_send);
//        final TextView code_send_tv = (TextView) popupThrid.findViewById(R.id.code_send_tv);
        Button pay_btn = (Button) popupThrid.findViewById(R.id.pay_btn);
        final PopupWindow popupWindow = new PopupWindow(popupThrid, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.TOP, 0, 0);
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

        if (dataname == null){
            phone_ed.setText(bind_mob1);
        }
        if (dataname != null){
            phone_ed.setText(bind_mob);
        }

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
            }
        });
        /*code_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_num = phone_ed.getText().toString().trim();
                if (TextUtils.isEmpty(phone_num)||phone_num.length() != 11){
                    TUtils.showShort(TopUpRechangeActivity.this,"请填写手机号码");
                    return;
                }
                if (dataname == null){
                    Log.i(TAG, "decodePassWord: 默认"+dataname+"");
                    requestData1();
                }
                if (dataname != null){
                    Log.i(TAG, "decodePassWord: 选择"+dataname+"");
                    requestData();
                }
                TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,code_send_tv,code_send);
                timeCountUtil.start();
            }
        });*/
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone_num = phone_ed.getText().toString().trim();
                code_pay = pay_code.getText().toString().trim();
                if (TextUtils.isEmpty(phone_num)|| phone_num.length() != 11){
                    TUtils.showShort(TopUpRechangeActivity.this,"请填写手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code_pay)|| code_pay.length() != 6){
                    TUtils.showShort(TopUpRechangeActivity.this,"请输入正确验证码");
                    return;
                }
                popupWindow.dismiss();
                requestDataTwo();

            }
        });
    }
    //默认银行卡充值
    private void requestData1() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");
            Log.i(TAG, "requestData1: "+user_id);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            params.put("no_agree",no_agree1);
            params.put("pwd_pay",pwd);
            params.put("money_order",money);
            params.put("card_no",card_no1);
            params.put("pay_type",pay_type1);
            params.put("bind_mob",bind_mob1);
            params.put("vali_date",vali_date1);
            params.put("cvv2",cvv21);

            OkGo.post(Urls.balance_recharge)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BalanceRechargeBean>(this) {
                        @Override
                        public void onSuccess(BalanceRechargeBean balanceRechargeBean, Call call, Response response) {
                            decodeData(balanceRechargeBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(TopUpRechangeActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");
            Log.i(TAG, "requestData: "+user_id);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            params.put("no_agree",no_agree);
            params.put("pwd_pay",pwd);
            params.put("money_order",money);
            params.put("card_no",card_no);
            params.put("pay_type",pay_type);
            params.put("bind_mob",bind_mob);
            params.put("vali_date",vali_date);
            params.put("cvv2",cvv2);

            OkGo.post(Urls.balance_recharge)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BalanceRechargeBean>(this) {
                        @Override
                        public void onSuccess(BalanceRechargeBean balanceRechargeBean, Call call, Response response) {
                            decodeData(balanceRechargeBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(TopUpRechangeActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void decodeData(BalanceRechargeBean balanceRechargeBean) {
        String err_code = balanceRechargeBean.getErr_code();
        String err_msg = balanceRechargeBean.getErr_msg();
        String ret_code = balanceRechargeBean.getData().getData().getRet_code();
        String ret_msg = balanceRechargeBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){

            intent = new Intent(TopUpRechangeActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            startActivity(intent);
            finish();
        }else if ("200".equals(err_code) && "8888".equals(ret_code)){
            TUtils.showShort(this,"短信已下发，请注意查收");

            tokens = balanceRechargeBean.getData().getData().getToken();
            no_order = balanceRechargeBean.getData().getData().getNo_order();

            openPupThrid();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    private void requestDataTwo(){
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

            OkGo.post(Urls.balabce_recharge_two)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BalanceRechargeTwoBean>(this) {
                        @Override
                        public void onSuccess(BalanceRechargeTwoBean balanceRechargeTwoBean, Call call, Response response) {
                            decodeDataTwo(balanceRechargeTwoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(TopUpRechangeActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeDataTwo(BalanceRechargeTwoBean balanceRechargeTwoBean) {
        String err_code = balanceRechargeTwoBean.getErr_code();
        String err_msg = balanceRechargeTwoBean.getErr_msg();
        String ret_code = balanceRechargeTwoBean.getData().getData().getRet_code();
        String ret_msg = balanceRechargeTwoBean.getData().getData().getRet_msg();

        if ("200".equals(err_code) && "0000".equals(ret_code)){

            intent = new Intent(TopUpRechangeActivity.this,WalletBalancePayActivity.class);
            intent.putExtra("money",money);
            startActivity(intent);
            finish();

        }else {
            TUtils.showShort(this,ret_msg);
        }
    }
}
