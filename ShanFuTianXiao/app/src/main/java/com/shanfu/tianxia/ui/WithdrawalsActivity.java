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
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.bean.WithdrawalsBean;
import com.shanfu.tianxia.listener.DialogCallback;
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
 * 提现
 */
public class WithdrawalsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout withdrawals_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.withdrawals_card)
    TextView withdrawals_card;
    @Bind(R.id.withdrawals_money)
    EditText withdrawals_money;
    @Bind(R.id.withdrawals_pwd)
    EditText withdrawals_pwd;
    @Bind(R.id.phone_verification_code)
    EditText phone_verification_code;
    @Bind(R.id.send_withdrawal_code)
    RelativeLayout send_withdrawal_code;
    @Bind(R.id.withdrawal_button)
    Button withdrawal_button;
    private String bankcard;
    private WithdrawalsBean withdrawalsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);
        ButterKnife.bind(this);
        bankcard = SPUtils.getInstance().getString("bankcard","");
        init();
        //requestData("0.01");
    }

    private void init() {
        withdrawals_top = (RelativeLayout) findViewById(R.id.withdrawals_top);
        content_head_back = (RelativeLayout) withdrawals_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) withdrawals_top.findViewById(R.id.content_head_title);
        content_head_title.setText("提现");
        content_head_back.setOnClickListener(this);

        withdrawals_money.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        withdrawals_money.setText(s);
                        withdrawals_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    withdrawals_money.setText(s);
                    withdrawals_money.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        withdrawals_money.setText(s.subSequence(0, 1));
                        withdrawals_money.setSelection(1);
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
        withdrawals_card.setText(bankcard);
        send_withdrawal_code.setOnClickListener(this);
        withdrawal_button.setOnClickListener(this);
    }

    private String money,pwd,verification_cod,phoneNum;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                 finish();
                break;
            case R.id.withdrawal_button:
                money =  withdrawals_money.getText().toString().trim();
                pwd = withdrawals_pwd.getText().toString().trim();
                verification_cod = phone_verification_code.getText().toString().trim();
                
                if(TextUtils.isEmpty(money)){
                    TUtils.showShort(this,"请输入提现金额");
                    return;
                }
                if(TextUtils.isEmpty(pwd)||pwd.length()!=6){
                    TUtils.showShort(this,"请输入6位交易密码");
                    return;
                }
                if(TextUtils.isEmpty(verification_cod)){
                    TUtils.showShort(this,"验证码不能为空");
                    return;
                }

                double input_money = Double.valueOf(money);
                if(input_money<110.00){
                    TUtils.showShort(this,"提现金额最小额为110,请重新输入");
                    return;
                }
                //// TODO: 2017/3/31
                requestData(money, MD5Utils.MD5(pwd), verification_cod);
                break;
            case R.id.send_withdrawal_code:
                phoneNum =  SPUtils.getInstance().getString("phoneNum","");
                if(TextUtils.isEmpty(phoneNum)){
                    TUtils.showShort(this,"请重新登录");
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    //// TODO: 2017/3/31
                    requestCode(phoneNum);
                }
                
                break;
        }
    }

    private void requestCode(String num){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("phone", num);

            OkGo.post(Urls.withDrawCode)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBane, Call call, Response response) {
                            decodeCode(rsultBane);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeCode(RsultBean rsultBane){
        String code = rsultBane.getErr_code();
        String msg = rsultBane.getErr_msg();
        if("200".equals(code)){

        }else{
            TUtils.showShort(WithdrawalsActivity.this,msg);
        }
    }


    private void requestData(String money,String pwd,String code){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(WithdrawalsActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("money", money);
            params.put("tradepwd", pwd);
            params.put("code", code);
            params.put("uid", uid);
            OkGo.post(Urls.withDraw)
                    .tag(this)
                    .params(params)
                    .execute(new StringDialogCallback(this) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            if(s.startsWith("\uFEFF\uFEFF")){
                                String result = s.split("\uFEFF\uFEFF")[1];
                                Gson gson = new Gson();
                                withdrawalsBean = gson.fromJson(result,WithdrawalsBean.class);
                            }else{
                                Gson gson = new Gson();
                                withdrawalsBean = gson.fromJson(s,WithdrawalsBean.class);
                            }
                            decodeResultDta(withdrawalsBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(WithdrawalsActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String url;

    private void decodeResultDta(WithdrawalsBean withdrawalsBean){
        String code = withdrawalsBean.getErr_code();
        String msg = withdrawalsBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", withdrawalsBean.getData().getPtoken());
            TUtils.showShort(WithdrawalsActivity.this, "提现成功");
            finish();
           /* url = withdrawalsBean.getData().getUrl();
            Intent intent = new Intent(WithdrawalsActivity.this,PayWebViewActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);*/
        }else{
            TUtils.showShort(WithdrawalsActivity.this, msg);
        }
    }

    private void decode(PayResultBean payResultBean){
        String code = payResultBean.getErr_code();
        String msg = payResultBean.getErr_msg();
        if("200".equals(code)){
            Intent intent = new Intent(WithdrawalsActivity.this,PayWebViewActivity.class);
            intent.putExtra("url",payResultBean.getData().getUrl());
            startActivity(intent);
        }else
            TUtils.showShort(WithdrawalsActivity.this,msg);
        }

}
