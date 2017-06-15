package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shanfu.tianxia.bean.SmsCheckBean;
import com.shanfu.tianxia.bean.SmsSendBean;
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

public class RealNameFirstActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.phone_ed)
    EditText phone_ed;
    @Bind(R.id.code_ed)
    EditText code_ed;
    @Bind(R.id.next_btn)
    Button next_btn;
    @Bind(R.id.code_send)
    RelativeLayout code_send;
    @Bind(R.id.code_send_tv)
    TextView code_send_tv;
    private String phone;
    private String verify_code;
    private String tokens;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_first);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("实名认证");
        content_head_back.setOnClickListener(this);

        next_btn.setOnClickListener(this);
        code_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.next_btn:
                verify_code = code_ed.getText().toString().trim();
                if (TextUtils.isEmpty(phone)|| phone.length() != 11){
                    TUtils.showShort(RealNameFirstActivity.this,"请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(verify_code)|| verify_code.length() != 6){
                    TUtils.showShort(RealNameFirstActivity.this,"请输入正确的验证码");
                    return;
                }
                requestCheck(phone,verify_code);

                break;
            case R.id.code_send:
                phone = phone_ed.getText().toString().trim();
                if (TextUtils.isEmpty(phone)|| phone.length() != 11){
                    TUtils.showShort(RealNameFirstActivity.this,"请输入正确的手机号");
                    return;
                }
                requestCode();
                break;
        }
    }

    private void requestCode() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("phone", phone);

            OkGo.post(Urls.smssend)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SmsSendBean>(this) {
                        @Override
                        public void onSuccess(SmsSendBean smsSendBean, Call call, Response response) {
                            decodeSmssend(smsSendBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(RealNameFirstActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeSmssend(SmsSendBean smsSendBean) {
        String code = smsSendBean.getErr_code();
        String msg = smsSendBean.getErr_msg();
        String ret_msg = smsSendBean.getData().getData().getRet_msg();
        String ret_code = smsSendBean.getData().getData().getRet_code();
        if("0000".equals(ret_code)){
            tokens = smsSendBean.getData().getData().getToken();
            TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,code_send_tv,code_send);
            timeCountUtil.start();
            TUtils.showShort(RealNameFirstActivity.this,"短信已下发，请注意查收");
        }else {
            TUtils.showShort(RealNameFirstActivity.this,ret_msg);
        }
    }

    private void requestCheck(String phone,String code){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", phone);
            params.put("verify_code",verify_code);
            params.put("tokens",tokens);

            OkGo.post(Urls.smscheck)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SmsCheckBean>(this) {
                        @Override
                        public void onSuccess(SmsCheckBean smsCheckBean, Call call, Response response) {
                            decodeSmsCheck(smsCheckBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(RealNameFirstActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void decodeSmsCheck(SmsCheckBean smsCheckBean) {
        String code = smsCheckBean.getErr_code();
        String msg = smsCheckBean.getErr_msg();
        String ret_code = smsCheckBean.getData().getData().getRet_code();
        String ret_msg = smsCheckBean.getData().getData().getRet_msg();
        if ("200".equals(code)&&"0000".equals(ret_code)){
            String tokens = smsCheckBean.getData().getData().getToken();
            intent = new Intent(RealNameFirstActivity.this,RealNameSecondActivity.class);
            SPUtils.getInstance().putString("use_id",phone);
            intent.putExtra("phone",phone);
            intent.putExtra("verify_code",verify_code);
            intent.putExtra("tokens",tokens);
            startActivity(intent);
        }else {
            TUtils.showShort(RealNameFirstActivity.this,ret_msg);
        }
    }
}
