package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BankCardAuthVerfyBean;
import com.shanfu.tianxia.bean.BankCardOpenAuthBean;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.TimeCountUtil;
import com.shanfu.tianxia.utils.Urls;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BindBankNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout bind_card_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.binding_bank_card_phone)
    EditText binding_bank_card_phone;
    @Bind(R.id.binding_bank_card_code)
    EditText binding_bank_card_code;
    @Bind(R.id.code_send)
    RelativeLayout code_send;
    @Bind(R.id.code_send_tv)
    TextView code_send_tv;
    @Bind(R.id.next_btn)
    Button next_btn;
    private String bank;
    private String number;
    private String date;
    private String cvv2;
    private String tokens;
    private String bank_code;
    private String pay_type;
    private String verify_code;
    private String phone;
    private String card_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_bank_next);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        bind_card_top = (RelativeLayout) findViewById(R.id.bind_card_top);
        content_head_back = (RelativeLayout) bind_card_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) bind_card_top.findViewById(R.id.content_head_title);
        content_head_title.setText("验证");
        content_head_back.setOnClickListener(this);

        code_send.setOnClickListener(this);
        next_btn.setOnClickListener(this);
    }

    private void initData() {
        String name = getIntent().getStringExtra("name");
        number = getIntent().getStringExtra("number");
        bank = getIntent().getStringExtra("bank");
        date = getIntent().getStringExtra("date");
        cvv2 = getIntent().getStringExtra("cvv2");
        if ("借记卡".equals(bank)) {
            pay_type = "2";
        } else {
            pay_type = "3";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_head_back:
                finish();
                break;
            case R.id.code_send:
                phone = binding_bank_card_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    TUtils.showShort(BindBankNextActivity.this, "请输入预留手机号");
                    return;
                }

                requestCode(phone);
                break;
            case R.id.next_btn:
                verify_code = binding_bank_card_code.getText().toString().trim();
                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    TUtils.showShort(BindBankNextActivity.this, "请输入预留手机号");
                    return;
                }
                if (TextUtils.isEmpty(verify_code)) {
                    TUtils.showShort(BindBankNextActivity.this, "请输入验证码");
                    return;
                }

                requestData();
                break;
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

            params.put("tokens", tokens);
            params.put("pay_type", pay_type);
            params.put("verify_code", verify_code);
            params.put("bind_mob", phone);
            params.put("card_no", number);

            OkGo.post(Urls.bankcardauthverfy)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardAuthVerfyBean>(this) {
                        @Override
                        public void onSuccess(BankCardAuthVerfyBean bankCardAuthVerfyBean, Call call, Response response) {
                            decodeData(bankCardAuthVerfyBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(BindBankNextActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(BankCardAuthVerfyBean bankCardAuthVerfyBean) {
        String err_code = bankCardAuthVerfyBean.getErr_code();
        String err_msg = bankCardAuthVerfyBean.getErr_msg();
        String ret_code = bankCardAuthVerfyBean.getData().getData().getRet_code();
        String ret_msg = bankCardAuthVerfyBean.getData().getData().getRet_msg();
        if ("200".equals(err_code) && "0000".equals(ret_code)) {
            //银行卡号
            card_no = bankCardAuthVerfyBean.getData().getData().getCard_no();
            Intent intent = new Intent(BindBankNextActivity.this, MyBankCardActivity.class);
            intent.putExtra("card_no", card_no);
            intent.putExtra("bank_code", bank_code);
            startActivity(intent);
            finish();
        } else {
            TUtils.showShort(BindBankNextActivity.this, ret_msg);
        }
    }

    private void requestCode(String phone) {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            if ("借记卡".equals(bank)) {
                params.put("time", time);
                params.put("token", token);
                params.put("uid", uid);
                params.put("user_id", user_id);
                params.put("bind_mob", phone);
                params.put("pay_type", pay_type);
                params.put("card_no", number);
            } else {
                params.put("time", time);
                params.put("token", token);
                params.put("uid", uid);
                params.put("user_id", user_id);
                params.put("bind_mob", phone);
                params.put("pay_type", pay_type);
                params.put("card_no", number);
                params.put("vali_date", date);
                params.put("cvv2", cvv2);
            }

            OkGo.post(Urls.bankcardopenauth)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardOpenAuthBean>(this) {
                        @Override
                        public void onSuccess(BankCardOpenAuthBean bankCardOpenAuthBean, Call call, Response response) {
                            decodeBankCardOpenAuth(bankCardOpenAuthBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(BindBankNextActivity.this, "数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBankCardOpenAuth(BankCardOpenAuthBean bankCardOpenAuthBean) {

        String ret_code = bankCardOpenAuthBean.getData().getData().getRet_code();
        String ret_msg = bankCardOpenAuthBean.getData().getData().getRet_msg();
        if ("0000".equals(ret_code)) {
            bank_code = bankCardOpenAuthBean.getData().getData().getBank_code();
            tokens = bankCardOpenAuthBean.getData().getData().getToken();
            TimeCountUtil timeCountUtil = new TimeCountUtil(60000, 1000, code_send_tv, code_send);
            timeCountUtil.start();
        } else {
            TUtils.showShort(BindBankNextActivity.this, ret_msg);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
