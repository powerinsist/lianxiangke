package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.shanfu.tianxia.bean.BankLianLianBean;
import com.shanfu.tianxia.bean.CashOutCombineApplyBean;
import com.shanfu.tianxia.bean.SingleUserQueryBean;
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

public class WalletWithDrawActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.bank_celsct_rl)
    RelativeLayout bank_celsct_rl;
    @Bind(R.id.amount_entered)
    EditText amount_entered;
    @Bind(R.id.wallet_pay_next)
    Button wallet_pay_next;
    @Bind(R.id.bank_select_tv)
    TextView bank_select_tv;
    @Bind(R.id.tixian_tv)
    TextView tixian_tv;
    private Intent intent;
    private String money;
    private String dataname;
    private String no_agree;
    private String bankName;
    private String card_no;
    private String pay_type;
    private String bind_mob;
    private String vali_date;
    private String cvv2;
    private String pwd;
    private String balance;
    private List<BankLianLianBean.DataBeanX.DataBean> list;
    private String pay_type1;
    private String card_no1;
    private String bankdeltail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_with_draw);
        ButterKnife.bind(this);
        initView();
        requestBalance();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("提现");
        content_head_back.setOnClickListener(this);

        bank_celsct_rl.setOnClickListener(this);
        wallet_pay_next.setOnClickListener(this);

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
//                            TUtils.showShort(WalletWithDrawActivity.this,"网络获取失败");
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
            card_no1 = list.get(list.size() - 1).getCard_no();
            String no_agree1 = list.get(list.size() - 1).getNo_agree();
            pay_type1 = list.get(list.size() - 1).getPay_type();
            String bind_mob1 = list.get(list.size() - 1).getBind_mob();
            String vali_date1 = list.get(list.size() - 1).getVali_date();
            String cvv21 = list.get(list.size() - 1).getCvv2();

            String substring = card_no1.substring(card_no1.length() - 4, card_no1.length());
            bankdeltail = bankname1 + "(" + substring + ")";
            if ("3".equals(pay_type1)){
                bank_select_tv.setText("点击选择借记卡提现");
            }else {
                bank_select_tv.setText(bankdeltail);
            }
        } else {
            TUtils.showShort(this, err_msg);
        }
    }

    private void requestBalance() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.singleuserquery)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SingleUserQueryBean>(this) {
                        @Override
                        public void onSuccess(SingleUserQueryBean singleUserQueryBean, Call call, Response response) {
                            decodeData(singleUserQueryBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(WalletWithDrawActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(SingleUserQueryBean singleUserQueryBean) {
        String err_code = singleUserQueryBean.getErr_code();
        String err_msg = singleUserQueryBean.getErr_msg();
        String ret_code = singleUserQueryBean.getData().getData().getRet_code();
        String ret_msg = singleUserQueryBean.getData().getData().getRet_msg();

        if ("200".equals(err_code)&&"0000".equals(ret_code)){
            balance = singleUserQueryBean.getData().getData().getBalance();
            tixian_tv.setText("提现到银行卡可提现"+balance);
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击更换银行卡
            case R.id.bank_celsct_rl:
                if (bank_select_tv.getText().toString().equals("请添加银行卡")){
                    intent = new Intent(WalletWithDrawActivity.this,MyBankCardPwdActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(WalletWithDrawActivity.this, TopSelectBankActivity.class);
                intent.putExtra("code", 1);
                startActivityForResult(intent, 101);
                break;
            case R.id.wallet_pay_next:
                money = amount_entered.getText().toString().trim();

                String bank_select = bank_select_tv.getText().toString().trim();
                if (TextUtils.isEmpty(money)){
                    TUtils.showShort(this,"请输入充值金额");
                    return;
                }
                if (dataname == null){
                    if ("3".equals(pay_type1)){
                        TUtils.showShort(WalletWithDrawActivity.this,"信用卡暂不能提现，请选择借记卡");
                        return;
                    }
                }
                if (dataname != null){
                    if ("3".equals(pay_type)){
                        TUtils.showShort(WalletWithDrawActivity.this,"信用卡暂不能提现，请选择借记卡");
                        return;
                    }
                }

                if (bank_select.equals("点击选择借记卡提现")){
                    TUtils.showShort(this,"请选择银行卡");
                    return;
                }
                float money_float = Float.parseFloat(money);
                float balance_float = Float.parseFloat(balance);
                //TODO 判断输入金额需要大于2，提现金额小于100的2.0手续费，大于100按2%扣
                if (money_float < 10){
                    TUtils.showShort(this,"提现金额必须大于10元");
                    return;
                }
                if (money_float > balance_float){
                    TUtils.showShort(this,"可提现金额不足，请重新输入");
                    return;
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
        dataname = data.getStringExtra("name");
        bank_select_tv.setText(dataname);
        no_agree = data.getStringExtra("no_agree");
        bankName = data.getStringExtra("bankName");
        card_no = data.getStringExtra("card_no");
        pay_type = data.getStringExtra("pay_type");
        bind_mob = data.getStringExtra("bind_mob");
        vali_date = data.getStringExtra("vali_date");
        cvv2 = data.getStringExtra("cvv2");
    }

    private void openPopup() {
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_wallet_pay_popup, null);
        TextView select_tv = (TextView) popupPayView.findViewById(R.id.select_tv);
        select_tv.setText("提现");
        Button btn_close = (Button) popupPayView.findViewById(R.id.btn_close);
        TextView pay_money_tv = (TextView) popupPayView.findViewById(R.id.pay_money_tv);
        final PwdEditText pwd_edittext = (PwdEditText) popupPayView.findViewById(R.id.pwd_edittext);
        final PopupWindow popupWindow = new PopupWindow(popupPayView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, -100);
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
            @Override
            public void onInputFinish(String password) {
//                pwd_pay = SPUtils.getInstance().getString("pwd_pay", "");
                pwd = pwd_edittext.getText().toString().trim();
                requestPassWord();
//                if (!pwd.equals(pwd_pay)){
//                    TUtils.showShort(WalletWithDrawActivity.this,"请输入正确支付密码");
//                    return;
//                }
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
                            .execute(new DialogCallback<VerifyPassWordBean>(WalletWithDrawActivity.this) {
                                @Override
                                public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
                                    decodePassWord(verifyPassWordBean);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    TUtils.showShort(WalletWithDrawActivity.this,"数据获取失败，请检查网络后重试");
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
                    if (dataname == null){
                        requestData1();
                    }
                    if (dataname != null){
                        requestData();
                    }
                }else {
                    TUtils.showShort(WalletWithDrawActivity.this, err_msg);
                }
            }
        });
    }
    //默认银行卡提现
    private void requestData1() {
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

            params.put("money_order",money);
            params.put("card_no",card_no1);
            params.put("pwd_pay",pwd);

            OkGo.post(Urls.cashoutcombineapply)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<CashOutCombineApplyBean>(this) {
                        @Override
                        public void onSuccess(CashOutCombineApplyBean cashOutCombineApplyBean, Call call, Response response) {
                            decodeData(cashOutCombineApplyBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(WalletWithDrawActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestData() {
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

            params.put("money_order",money);
            params.put("card_no",card_no);
            params.put("pwd_pay",pwd);

            OkGo.post(Urls.cashoutcombineapply)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<CashOutCombineApplyBean>(this) {
                        @Override
                        public void onSuccess(CashOutCombineApplyBean cashOutCombineApplyBean, Call call, Response response) {
                            decodeData(cashOutCombineApplyBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(WalletWithDrawActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(CashOutCombineApplyBean cashOutCombineApplyBean) {
        String err_code = cashOutCombineApplyBean.getErr_code();
        String err_msg = cashOutCombineApplyBean.getErr_msg();
        String ret_code = cashOutCombineApplyBean.getData().getData().getRet_code();
        String ret_msg = cashOutCombineApplyBean.getData().getData().getRet_msg();

        if ("200".equals(err_code)&&"0000".equals(ret_code)){
            String counterfee = cashOutCombineApplyBean.getData().getData().getCounterfee();
            intent = new Intent(WalletWithDrawActivity.this, WithDrawSucessActivity.class);
            if (dataname == null){
                intent.putExtra("bankName",bankdeltail);
                intent.putExtra("card_no",card_no1);
                intent.putExtra("money",money);
                intent.putExtra("counterfee",counterfee);
            }
            if (dataname != null){
                intent.putExtra("bankName",dataname);
                intent.putExtra("card_no",card_no);
                intent.putExtra("money",money);
                intent.putExtra("counterfee",counterfee);
            }
            startActivity(intent);
            finish();

        }else {
            TUtils.showShort(this,ret_msg);
        }
    }
}
