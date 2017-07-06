package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.shanfu.tianxia.bean.PayPwdSetBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class ForgetPayPwdThirdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.new_pwd_ed)
    EditText new_pwd_ed;
    @Bind(R.id.new_pay_pwd_next_ed)
    EditText new_pay_pwd_next_ed;
    @Bind(R.id.set_new_pay_pwd)
    Button set_new_pay_pwd;

    private String verify_code;
    private String tokens;
    private String new_pwd;
    private String new_pwd_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pay_pwd_third);
        ButterKnife.bind(this);
        tokens = getIntent().getStringExtra("tokens");

        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("新交易密码");
        content_head_back.setOnClickListener(this);

        set_new_pay_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.set_new_pay_pwd:
                new_pwd = new_pwd_ed.getText().toString().trim();
                new_pwd_next = new_pay_pwd_next_ed.getText().toString().trim();
                if (TextUtils.isEmpty(new_pwd)){
                    TUtils.showShort(ForgetPayPwdThirdActivity.this,"请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(new_pwd_next)){
                    TUtils.showShort(ForgetPayPwdThirdActivity.this,"请输入新密码");
                    return;
                }
                if (!new_pwd.equals(new_pwd_next)){
                    TUtils.showShort(ForgetPayPwdThirdActivity.this,"两次密码输入不一致");
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
            String uid = SPUtils.getInstance().getString("uid","");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", user_id);

            params.put("tokens",tokens);
            params.put("pwd_pay_new",new_pwd);
            Log.e("LOG",new_pwd);

            OkGo.post(Urls.paypwdset)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<PayPwdSetBean>(this) {
                        @Override
                        public void onSuccess(PayPwdSetBean payPwdSetBean, Call call, Response response) {
                            decodeData(payPwdSetBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPayPwdThirdActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(PayPwdSetBean payPwdSetBean) {
        String err_code = payPwdSetBean.getErr_code();
        String err_msg = payPwdSetBean.getErr_msg();
        String ret_code = payPwdSetBean.getData().getData().getRet_code();
        String ret_msg = payPwdSetBean.getData().getData().getRet_msg();

        if("200".equals(err_code)&&"0000".equals(ret_code)){
//            SPUtils.getInstance().putString("pwd_pay",new_pwd);
            Intent intent = new Intent(ForgetPayPwdThirdActivity.this,MyWalletActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(ForgetPayPwdThirdActivity.this,ret_msg);
        }
    }
}
