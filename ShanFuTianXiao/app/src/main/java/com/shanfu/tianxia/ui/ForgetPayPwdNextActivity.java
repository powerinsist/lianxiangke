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
import com.shanfu.tianxia.bean.RegeditBean;
import com.shanfu.tianxia.bean.RsultBean;
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
 * Created by xuchenchen on 2017/4/19.
 * 修改提现密码第二步
 */

public class ForgetPayPwdNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.forget_pay_pwd)
    EditText forget_pay_pwd;
    @Bind(R.id.froget_pay_pwd_next)
    EditText forget_pay_pwd_next;
    @Bind(R.id.froget_pay_pwd_complete)
    Button forget_pay_pwd_complete;
    private RelativeLayout forget_pay_pwd_next_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    private String send_code,phone;
    private String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pay_next);
        ButterKnife.bind(this);
        initView();

        send_code = getIntent().getStringExtra("send_code");
        phone = getIntent().getStringExtra("phone");
        uid = SPUtils.getInstance().getString("uid","");


    }

    private void initView() {
        forget_pay_pwd_next_top = (RelativeLayout) findViewById(R.id.forget_pay_pwd_next_top);
        content_head_back = (RelativeLayout) forget_pay_pwd_next_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) forget_pay_pwd_next_top.findViewById(R.id.content_head_title);
        content_head_title.setText("重置提现密码");
        content_head_back.setOnClickListener(this);
        forget_pay_pwd_complete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;

            case R.id.froget_pay_pwd_complete:
                String foget_pwd =  forget_pay_pwd.getText().toString().trim();
                String foget_pwd_again =  forget_pay_pwd_next.getText().toString().trim();

                if(TextUtils.isEmpty(foget_pwd)||foget_pwd.length() != 6){
                    TUtils.showShort(ForgetPayPwdNextActivity.this,"提现密码为6位，请重新输入");
                    return;
                }
                if(!foget_pwd.equals(foget_pwd_again)){
                    TUtils.showShort(ForgetPayPwdNextActivity.this,"两次输入的密码不一致，请重新输入");
                    return;
                }
//                if(TextUtils.isEmpty(send_code)){
//                    TUtils.showShort(ForgetPayPwdNextActivity.this,"验证码不能为空");
//                    return;
//                }

                requestCode(phone,  MD5Utils.MD5(foget_pwd),MD5Utils.MD5(foget_pwd_again),uid);
                break;
        }
    }

    private void requestCode(String phone,String tradepwd,String twopwd,String uid){

        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
//            params.put("code",code);
            params.put("phone", phone);
            params.put("tradepwd", tradepwd);
            params.put("twopwd",twopwd);
            params.put("uid",uid);
            params.put("send_code",send_code);



            OkGo.post(Urls.tixian_requst)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPayPwdNextActivity.this, "数据获取失败，请检查网络后重试");
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
            TUtils.showShort(ForgetPayPwdNextActivity.this,msg);
        }else{
            TUtils.showShort(ForgetPayPwdNextActivity.this,"密码修改成功");
            Intent intent = new Intent(ForgetPayPwdNextActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
