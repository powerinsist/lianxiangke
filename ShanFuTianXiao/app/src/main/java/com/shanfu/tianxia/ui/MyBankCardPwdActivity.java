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

public class MyBankCardPwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.pwd_ed)
    PwdEditText pwd_ed;
    private Intent intent;
    private String pwd_put;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_card_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("绑定银行卡");
        content_head_back.setOnClickListener(this);

        pwd_ed.setOnInputFinishListener(new PwdEditText.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
//                String pwd_pay = SPUtils.getInstance().getString("pwd_pay", "");
                pwd_put = pwd_ed.getText().toString().trim();
                requestPassWord();
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
                    Log.e("LOG",pwd_put);
                    Log.e("LOG",user_id);

                    OkGo.post(Urls.verify_password)
                            .tag(this)
                            .params(params)
                            .execute(new DialogCallback<VerifyPassWordBean>(MyBankCardPwdActivity.this) {
                                @Override
                                public void onSuccess(VerifyPassWordBean verifyPassWordBean, Call call, Response response) {
                                    decodePassWord(verifyPassWordBean);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    TUtils.showShort(MyBankCardPwdActivity.this,"数据获取失败，请检查网络后重试");
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
                    intent = new Intent(MyBankCardPwdActivity.this,BindingBankCardActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    TUtils.showShort(MyBankCardPwdActivity.this,err_msg);
                }
            }
        });
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
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
