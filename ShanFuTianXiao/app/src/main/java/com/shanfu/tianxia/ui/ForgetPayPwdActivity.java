package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.bean.RsultCodeBean;
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
 * 修改提现密码第一步
 */

public class ForgetPayPwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout forget_pay_pwd_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.forget_pay_phone_num)
    TextView forget_pay_phone_num;
    @Bind(R.id.froget_pay_yanzhengma)
    EditText froget_pay_yanzhengma;
    @Bind(R.id.forget_pay_send)
    RelativeLayout forget_pay_send;
    @Bind(R.id.froget_pay_pwd_next)
    Button froget_pay_pwd_next;
    @Bind(R.id.forget_pay_send_tv)
    TextView forget_pay_send_tv;
//    private String phone_num;
    private String num;
    private String send_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pay_word);
        ButterKnife.bind(this);
        num = getIntent().getStringExtra("phone");
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerCode(num);

    }

    private void initView(){
        forget_pay_pwd_top = (RelativeLayout) findViewById(R.id.forget_pay_pwd_top);
        content_head_back = (RelativeLayout) forget_pay_pwd_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) forget_pay_pwd_top.findViewById(R.id.content_head_title);
        content_head_title.setText("忘记密码");
        forget_pay_phone_num.setText(num);
        content_head_back.setOnClickListener(this);
        forget_pay_send.setOnClickListener(this);
        froget_pay_pwd_next.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.forget_pay_send:

                TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,forget_pay_send_tv,forget_pay_send);
                timeCountUtil.start();
                break;
            case R.id.froget_pay_pwd_next:
                    send_code = froget_pay_yanzhengma.getText().toString().trim();
                    registerCodeYanZheng(num,send_code);

                break;
        }
    }

    private void registerCode(String phone){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("ptoken", ptoken);
            params.put("phone", phone);

            OkGo.post(Urls.tixian_code)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPayPwdActivity.this, "数据获取失败，请检查网络后重试");
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
            TUtils.showShort(ForgetPayPwdActivity.this,msg);
        }
    }



    private void registerCodeYanZheng(String phone,String send_code){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");


            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("phone", phone);
            params.put("send_code",send_code);

            OkGo.post(Urls.tixian_yanzheng)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultCodeBean>(this) {
                        @Override
                        public void onSuccess(RsultCodeBean rsultCodeBean, Call call, Response response) {
                            decodeResultYanZheng(rsultCodeBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ForgetPayPwdActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResultYanZheng(RsultCodeBean rsultCodeBean){
        String code = rsultCodeBean.getErr_code();
        String msg = rsultCodeBean.getErr_msg();
        if(!"200".equals(code)){
            TUtils.showShort(ForgetPayPwdActivity.this,msg);
        }else {
            Intent intent = new Intent(ForgetPayPwdActivity.this,ForgetPayPwdNextActivity.class);
            intent.putExtra("send_code", send_code);
            intent.putExtra("phone", num);
            startActivity(intent);
        }
    }
}
