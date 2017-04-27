package com.shanfu.tianxia.ui;

import android.content.Intent;
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
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记密码最二步
 */
public class ForgetPwdNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout forget_pwd_next_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.forget_login_pwd)
    EditText forget_login_pwd;
    @Bind(R.id.froget_login_pwd_next)
    EditText froget_login_pwd_next;
    @Bind(R.id.froget_pwd_complete)
    Button froget_pwd_complete;

    private String send_code,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_next);
        ButterKnife.bind(this);
        initView();
        send_code = getIntent().getStringExtra("send_code");
        phone = getIntent().getStringExtra("phone");

    }
    private void initView(){
        forget_pwd_next_top = (RelativeLayout) findViewById(R.id.forget_pwd_next_top);
        content_head_back = (RelativeLayout) forget_pwd_next_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) forget_pwd_next_top.findViewById(R.id.content_head_title);
        content_head_title.setText("重置登录密码");
        content_head_back.setOnClickListener(this);
        froget_pwd_complete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.froget_pwd_complete:

               String login_pwd =  forget_login_pwd.getText().toString().trim();
                String login_pwd_agein =  froget_login_pwd_next.getText().toString().trim();
                if(TextUtils.isEmpty(login_pwd)||login_pwd.length()<6||login_pwd.length()>20){
                    TUtils.showShort(ForgetPwdNextActivity.this,"登录密码为6~20位，请重新输入");
                    return;
                }
                if(!login_pwd.equals(login_pwd_agein)){
                    TUtils.showShort(ForgetPwdNextActivity.this,"两次输入的密码不一致，请重新输入");
                    return;
                }

                if(TextUtils.isEmpty(send_code)){
                    TUtils.showShort(ForgetPwdNextActivity.this,"验证码不能为空");
                    return;
                }

                requestCode(phone, send_code, MD5Utils.MD5(login_pwd));
                break;


        }
    }

    private void requestCode(String phone,String code,String pwd){

        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("phone", phone);
            params.put("code", code);
            params.put("password", pwd);

            OkGo.post(Urls.setpasswordByreset)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPwdNextActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeResult(RsultBean rsultBean){
        String code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        if(!"200".equals(code)){
            TUtils.showShort(ForgetPwdNextActivity.this,msg);
        }else{
            Intent intent = new Intent(ForgetPwdNextActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }

}
