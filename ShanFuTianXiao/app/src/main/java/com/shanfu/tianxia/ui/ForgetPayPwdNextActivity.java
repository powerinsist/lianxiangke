package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shanfu.tianxia.bean.BankPayPwdSetBean;
import com.shanfu.tianxia.bean.PayPwdVerifyBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.TimeCountUtil;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xuchenchen on 2017/4/19.
 * 修改提现密码第二步
 */

public class ForgetPayPwdNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.forget_pay_code)
    EditText forget_pay_code;
    @Bind(R.id.code_send)
    RelativeLayout code_send;
    @Bind(R.id.code_send_tv)
    TextView code_send_tv;
    @Bind(R.id.froget_pay_pwd_complete)
    Button froget_pay_pwd_complete;
    private String name;
    private String idcard;
    private String bankNum;
    private String phone;
    private String tokens;
    private String code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pay_next);
        ButterKnife.bind(this);

        name = getIntent().getStringExtra("name");
        idcard = getIntent().getStringExtra("idcard");
        bankNum = getIntent().getStringExtra("bankNum");
        phone = getIntent().getStringExtra("phone");

        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("验证码");
        content_head_back.setOnClickListener(this);

        code_send.setOnClickListener(this);
        froget_pay_pwd_complete.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;

            case R.id.code_send:
                requestData();
                break;

            case R.id.froget_pay_pwd_complete:
                code = forget_pay_code.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    TUtils.showShort(ForgetPayPwdNextActivity.this,"请填写验证码");
                    return;
                }
                requestCode();
                break;
        }
    }

    private void requestCode() {
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

            params.put("tokens",tokens);
            params.put("verify_code",code);

            OkGo.post(Urls.paypwdverify)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<PayPwdVerifyBean>(this) {
                        @Override
                        public void onSuccess(PayPwdVerifyBean payPwdVerifyBean, Call call, Response response) {
                            decodeCode(payPwdVerifyBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPayPwdNextActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeCode(PayPwdVerifyBean payPwdVerifyBean) {
        String err_code = payPwdVerifyBean.getErr_code();
        String err_msg = payPwdVerifyBean.getErr_msg();
        String ret_code = payPwdVerifyBean.getData().getData().getRet_code();
        String ret_msg = payPwdVerifyBean.getData().getData().getRet_msg();

        if("200".equals(err_code)&&"0000".equals(ret_code)){
            String tokenss = payPwdVerifyBean.getData().getData().getToken();
            Intent intent = new Intent(ForgetPayPwdNextActivity.this,ForgetPayPwdThirdActivity.class);
            intent.putExtra("tokens",tokenss);
            startActivity(intent);
            finish();

        }else{
            TUtils.showShort(ForgetPayPwdNextActivity.this,ret_msg);
        }
    }

    private void requestData() {
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

            params.put("card_no",bankNum);
            params.put("bind_mob",phone);
            params.put("acct_name",name);
            params.put("id_no",idcard);

            OkGo.post(Urls.bankpaypwdset)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankPayPwdSetBean>(this) {
                        @Override
                        public void onSuccess(BankPayPwdSetBean bankPayPwdSetBean, Call call, Response response) {
                            decodeData(bankPayPwdSetBean);
                            OkGo.getInstance().debug("OkHttpUtils");


                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPayPwdNextActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(BankPayPwdSetBean bankPayPwdSetBean) {
        String err_code = bankPayPwdSetBean.getErr_code();
        String err_msg = bankPayPwdSetBean.getErr_msg();
        String ret_code = bankPayPwdSetBean.getData().getData().getRet_code();
        String ret_msg = bankPayPwdSetBean.getData().getData().getRet_msg();

        if("200".equals(err_code)&&"0000".equals(ret_code)){
            tokens = bankPayPwdSetBean.getData().getData().getToken();
            TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,code_send_tv,code_send);
            timeCountUtil.start();
        }else{
            TUtils.showShort(ForgetPayPwdNextActivity.this,ret_msg);
        }
    }

}
