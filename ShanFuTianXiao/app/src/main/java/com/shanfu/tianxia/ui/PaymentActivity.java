package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.PayResultBean;
import com.shanfu.tianxia.listener.StringDialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 付款
 */
public class PaymentActivity extends BaseFragmentActivity implements View.OnClickListener {

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

    private String shopid,shopname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        shopid = getIntent().getStringExtra("shopid");
        shopname = getIntent().getStringExtra("shopname");
        init();
    }
    private void init(){
        pay_ment_top = (RelativeLayout) findViewById(R.id.pay_ment_top);
        content_head_back = (RelativeLayout) pay_ment_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) pay_ment_top.findViewById(R.id.content_head_title);
        content_head_title.setText("付款");
        content_head_back.setOnClickListener(this);
        input_payment_money.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
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
        if(TextUtils.isEmpty(shopname)){
            account_name.setText("账户名称: 联享客合作商户");
        }else{
            account_name.setText("账户名称: "+shopname);
        }
        account_id.setText("帐户ID: " + shopid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.payment_button:
                String money = input_payment_money.getText().toString().trim();
                if(TextUtils.isEmpty(money)){
                    TUtils.showShort(PaymentActivity.this,"请输入付款金额");
                    return;
                }

                if (TextUtils.isEmpty(shopid)) {
                    TUtils.showShort(PaymentActivity.this,"没有获取到帐户ID,请重新扫描");
                    return;
                }
                requestData(money,shopid);

                break;
        }
    }
    private void requestData(String money,String shopid){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(PaymentActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid","");
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
                            if(s.startsWith("\uFEFF\uFEFF")){
                                String result = s.split("\uFEFF\uFEFF")[1];
                                Gson gson = new Gson();
                                payResultBean = gson.fromJson(result,PayResultBean.class);
                            }else{
                                Gson gson = new Gson();
                                payResultBean = gson.fromJson(s,PayResultBean.class);
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
    private void decodeResult(PayResultBean payResultBean){
        String code = payResultBean.getErr_code();
        String msg = payResultBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", payResultBean.getData().getPtoken());
            url = payResultBean.getData().getUrl();
            Intent intent = new Intent(PaymentActivity.this,PayWebViewActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }else{
            TUtils.showShort(PaymentActivity.this,msg);
        }
    }
}
