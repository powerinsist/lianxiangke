package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.shanfu.tianxia.bean.PayPwdModifyBean;
import com.shanfu.tianxia.bean.RegeditBean;
import com.shanfu.tianxia.bean.RusltPhoneBean;
import com.shanfu.tianxia.listener.DialogCallback;
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
 * 修改交易密码
 */
public class ChangePwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout change_pwd_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.input_old_pay_pwd)
    EditText input_old_pay_pwd;
    @Bind(R.id.input_new_pay_pwd)
    EditText input_new_pay_pwd;
    @Bind(R.id.input_queren_new_pay_pwd)
    EditText input_queren_new_pay_pwd;
    @Bind(R.id.set_longin_pwd_button)
    Button set_longin_pwd_button;

    private Intent intent;
    private String old_pwd;
    private String new_pwd;
    private String queren_new_pwd;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
        initView();




    }

    private void initView() {
        change_pwd_top = (RelativeLayout) findViewById(R.id.change_pwd_top);
        content_head_back = (RelativeLayout) change_pwd_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) change_pwd_top.findViewById(R.id.content_head_title);
        content_head_title.setText("修改交易密码");
        content_head_back.setOnClickListener(this);

        set_longin_pwd_button.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.set_longin_pwd_button:
                old_pwd = input_old_pay_pwd.getText().toString().trim();
                new_pwd = input_new_pay_pwd.getText().toString().trim();
                queren_new_pwd = input_queren_new_pay_pwd.getText().toString().trim();

                if(TextUtils.isEmpty(old_pwd)|| old_pwd.length()!=6){
                    TUtils.showShort(ChangePwdActivity.this,"请输入6位数的原交易密码");
                    return ;
                }

                if(TextUtils.isEmpty(new_pwd)|| new_pwd.length()!=6){
                    TUtils.showShort(ChangePwdActivity.this,"请输入6位数的新交易密码");
                    return ;
                }
                if(TextUtils.isEmpty(queren_new_pwd)|| queren_new_pwd.length()!=6){
                    TUtils.showShort(ChangePwdActivity.this,"请输入6位数的新确认交易密码");
                    return ;
                }
                if(!new_pwd.equals(queren_new_pwd)){
                    TUtils.showShort(ChangePwdActivity.this,"两次输入的交易密码不一致，请重新输入");
                    return ;
                }

                requestData();
                break;

        }
    }

    private void requestData(){
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

            params.put("pwd_pay",old_pwd);
            params.put("pwd_pay_new",new_pwd);

            OkGo.post(Urls.paypwdmodify)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<PayPwdModifyBean>(this) {

                        @Override
                        public void onSuccess(PayPwdModifyBean payPwdModifyBean, Call call, Response response) {
                            decodeData(payPwdModifyBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ChangePwdActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeData(PayPwdModifyBean payPwdModifyBean){
        String err_code = payPwdModifyBean.getErr_code();
        String err_msg = payPwdModifyBean.getErr_msg();
        String ret_code = payPwdModifyBean.getData().getData().getRet_code();
        String ret_msg = payPwdModifyBean.getData().getData().getRet_msg();

        if("200".equals(err_code)&&"0000".equals(ret_code)){
            TUtils.showShort(ChangePwdActivity.this, "修改密码成功");
            SPUtils.getInstance().putString("pwd_pay",new_pwd);
            intent = new Intent(ChangePwdActivity.this,MyWalletActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            TUtils.showShort(ChangePwdActivity.this,ret_msg);
        }
    }

}
