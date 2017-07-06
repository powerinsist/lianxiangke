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
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.GetLLInfoBean;
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

public class SettingRealInfoActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.phone_tv)
    TextView phone_tv;
    @Bind(R.id.code_ed)
    EditText code_ed;
    @Bind(R.id.code_send)
    RelativeLayout code_send;
    @Bind(R.id.code_send_tv)
    TextView code_send_tv;
    @Bind(R.id.next_btn)
    Button next_btn;

    private String code;
    private String user_id;
    private String tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_real_info);
        ButterKnife.bind(this);
        requestInfo();

        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("修改实名信息");
        content_head_back.setOnClickListener(this);

        code_send.setOnClickListener(this);
        next_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.code_send:
                if (phone_tv.getText().toString().trim() == null){
                    TUtils.showShort(SettingRealInfoActivity.this,"您还没有实名认证");
                    return;
                }
                requestCode();
                break;
            case R.id.next_btn:
                code = code_ed.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    TUtils.showShort(SettingRealInfoActivity.this,"请填写验证码");
                    return;
                }
                requestCheck();
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
            params.put("phone",user_id);
            params.put("flag_send","3");

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
                            TUtils.showShort(SettingRealInfoActivity.this,"数据获取失败，请检查网络后重试");
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
        if(!"200".equals(code)){
            TUtils.showShort(this,msg);
        }else {
            tokens = smsSendBean.getData().getData().getToken();
            TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,code_send_tv,code_send);
            timeCountUtil.start();
        }
    }

    private void requestCheck() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", user_id);
            params.put("verify_code",code);
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
                            TUtils.showShort(SettingRealInfoActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeSmsCheck(SmsCheckBean smsCheckBean) {
        String code = smsCheckBean.getErr_code();
        String msg = smsCheckBean.getErr_msg();
        if ("200".equals(code)){
            String tokens = smsCheckBean.getData().getData().getToken();
            Intent intent = new Intent(SettingRealInfoActivity.this,SettingRealInfoNextActivity.class);
            intent.putExtra("tokens",tokens);
            startActivity(intent);
        }else {
            TUtils.showShort(this,msg);
        }
    }

    private void requestInfo() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);

            OkGo.post(Urls.getllinfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GetLLInfoBean>(this) {
                        @Override
                        public void onSuccess(GetLLInfoBean getLLInfoBean, Call call, Response response) {
                            decodeInfo(getLLInfoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SettingRealInfoActivity.this,"数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeInfo(GetLLInfoBean getLLInfoBean) {
        String code = getLLInfoBean.getErr_code();
        String msg = getLLInfoBean.getErr_msg();

        if(!"200".equals(code)){
            TUtils.showShort(this,"您还没有实名认证！");
            Intent intent = new Intent(SettingRealInfoActivity.this, MainActivity.class);
            intent.putExtra("comefrom","mine");
            startActivity(intent);
        }else {
            user_id = getLLInfoBean.getData().getData().getUser_id();
            String name_user = getLLInfoBean.getData().getData().getName_user();
            String idcard = getLLInfoBean.getData().getData().getIdcard();
            phone_tv.setText(user_id);
        }
    }
}
