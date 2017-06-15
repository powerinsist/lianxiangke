package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BankCardDuBindBean;
import com.shanfu.tianxia.bean.VerifyPassWordBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.PwdEditText;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class BankCardUnBindActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.pwd_ed)
    PwdEditText pwd_ed;
    private Intent intent;
    private String pwd_pay;
    private String pwd_put;
    private String no_agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_un_bind);
        ButterKnife.bind(this);
        no_agree = getIntent().getStringExtra("no_agree");

        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("解除绑定");
        content_head_back.setOnClickListener(this);

        pwd_ed.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                pwd_put = pwd_ed.getText().toString().trim();
                requestPassWord();
            }
        });
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

            params.put("userpwd",pwd_put);

            OkGo.post(Urls.verify_password)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<VerifyPassWordBean>(this) {
                        @Override
                        public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
                            decodePassWord(verifyPassWordBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(BankCardUnBindActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodePassWord(VerifyPassWordBean verifyPassWordBean) {
        String err_code = verifyPassWordBean.getErr_code();
        String err_msg = verifyPassWordBean.getErr_msg();

        if ("200".equals(err_code)){
            requestData();
        }else {
            TUtils.showShort(BankCardUnBindActivity.this,err_msg);
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
            params.put("no_agree",no_agree);
            params.put("pwd_pay",pwd_put);

            OkGo.post(Urls.bankcarddubind)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankCardDuBindBean>(this) {
                        @Override
                        public void onSuccess(BankCardDuBindBean bankCardDuBindBean, Call call, Response response) {
                            decodeData(bankCardDuBindBean);
                            OkGo.getInstance().debug("OkHttpUtils");

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

    private void decodeData(BankCardDuBindBean bankCardDuBindBean) {
        String err_code = bankCardDuBindBean.getErr_code();
        String err_msg = bankCardDuBindBean.getErr_msg();
        String ret_code = bankCardDuBindBean.getData().getData().getRet_code();
        String ret_msg = bankCardDuBindBean.getData().getData().getRet_msg();

        if ("200".equals(err_code)&&"0000".equals(ret_code)){
            intent = new Intent(BankCardUnBindActivity.this,MyWalletActivity.class);
            startActivity(intent);
            finish();
        }else {
            TUtils.showShort(BankCardUnBindActivity.this,ret_msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            intent = new Intent(BankCardUnBindActivity.this,MyWalletActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
