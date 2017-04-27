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
    @Bind(R.id.forget_pay_password)
    TextView forget_pay_password;
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
        requestphone(phone);
        initView();




    }

    private void initView() {
        change_pwd_top = (RelativeLayout) findViewById(R.id.change_pwd_top);
        content_head_back = (RelativeLayout) change_pwd_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) change_pwd_top.findViewById(R.id.content_head_title);
        content_head_title.setText("修改提现密码");
        content_head_back.setOnClickListener(this);
        set_longin_pwd_button.setOnClickListener(this);
        forget_pay_password.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;

            case R.id.forget_pay_password:
                intent = new Intent(ChangePwdActivity.this,ForgetPayPwdActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
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

                requestData(MD5Utils.MD5(old_pwd),MD5Utils.MD5(new_pwd),MD5Utils.MD5(queren_new_pwd));
                break;

        }
    }

    private void requestData(String oldPwd,String newPwd,String queren){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(ChangePwdActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid","");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("oldpassword", oldPwd);
            params.put("uid", uid);
            params.put("newpassword", newPwd);
            params.put("confirmpaypassword", queren);

            OkGo.post(Urls.updatePaypassword)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
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
    private void decodeResult(RegeditBean rsultBean){
        String err_code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        if("200".equals(err_code)){
            SPUtils.getInstance().putString("ptoken", rsultBean.getData().getPtoken());
            TUtils.showShort(ChangePwdActivity.this, "修改密码成功");
            finish();
        }else if("103".equals(err_code)){
            Intent intent = new Intent(ChangePwdActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else if ("100".equals(err_code)){
            TUtils.showShort(ChangePwdActivity.this,msg);
        }
        else{
            TUtils.showShort(ChangePwdActivity.this, msg);
        }
    }

    private void requestphone(String phone){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid","");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("ptoken", ptoken);
            params.put("uid", uid);

            OkGo.post(Urls.forget_phone)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RusltPhoneBean>(this) {
                        @Override
                        public void onSuccess(RusltPhoneBean rusltPhoneBean, Call call, Response response) {
//                            phoneResult(rusltPhoneBean);
                            phoneResult(rusltPhoneBean);
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
    private void phoneResult(RusltPhoneBean rusltPhoneBean){
        phone = rusltPhoneBean.getData().getPhone();

    }
}
