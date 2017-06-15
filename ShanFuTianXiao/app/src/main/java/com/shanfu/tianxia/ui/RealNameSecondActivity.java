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
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.OpenUserBean;
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

public class RealNameSecondActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    
    @Bind(R.id.name_ed)
    EditText name_ed;
    @Bind(R.id.shenfen_ed)
    EditText shenfen_ed;
    @Bind(R.id.paypwd_ed)
    EditText paypwd_ed;
    @Bind(R.id.bind_btn)
    Button bind_btn;
    private String phone;
    private String verify_code;
    private String tokens;
    private String name_user;
    private String pwd_pay;
    private String no_idcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_second);
        ButterKnife.bind(this);

        phone = getIntent().getStringExtra("phone");
        verify_code = getIntent().getStringExtra("verify_code");
        tokens = getIntent().getStringExtra("tokens");
        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("实名认证");
        content_head_back.setOnClickListener(this);
        
        bind_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.bind_btn:
                name_user = name_ed.getText().toString().trim();
                no_idcard = shenfen_ed.getText().toString().trim();
                pwd_pay = paypwd_ed.getText().toString().trim();
                if (TextUtils.isEmpty(name_user)|| name_user.length() < 2|| name_user.length() > 6){
                    TUtils.showShort(RealNameSecondActivity.this,"请输入真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(no_idcard)|| no_idcard.length() != 18){
                    TUtils.showShort(RealNameSecondActivity.this,"请输入正确身份证号");
                    return;
                }
                if (TextUtils.isEmpty(pwd_pay)|| pwd_pay.length() != 6){
                    TUtils.showShort(RealNameSecondActivity.this,"请设置支付密码");
                    return;
                }
                requestOpenUser();
                break;
        }
    }

    private void requestOpenUser() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", phone);
            params.put("tokens", tokens);
            params.put("name_user", name_user);
            params.put("no_idcard", no_idcard);
            params.put("pwd_pay",pwd_pay);

            OkGo.post(Urls.openuser)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<OpenUserBean>(this) {
                        @Override
                        public void onSuccess(OpenUserBean openUserBean, Call call, Response response) {
                            decodeOpenuser(openUserBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(RealNameSecondActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeOpenuser(OpenUserBean openUserBean) {
        String code = openUserBean.getErr_code();
        String msg = openUserBean.getErr_msg();
        String ret_code = openUserBean.getData().getData().getRet_code();
        String ret_msg = openUserBean.getData().getData().getRet_msg();
        if ("0000".equals(ret_code)&&"200".equals(code)){
            SPUtils.getInstance().putString("pwd_pay",pwd_pay);
            SPUtils.getInstance().putString("no_idcard",no_idcard);
            SPUtils.getInstance().putString("name_user",name_user);
            SPUtils.getInstance().putString("user_id",phone);

            Intent intent = new Intent(RealNameSecondActivity.this, MainActivity.class);
            intent.putExtra("name_user",name_user);
            intent.putExtra("comefrom","home");
            startActivity(intent);
            finish();
            TUtils.showShort(RealNameSecondActivity.this,"实名认证成功！");
        }else {
            TUtils.showShort(RealNameSecondActivity.this,ret_msg);
            TUtils.showShort(RealNameSecondActivity.this,code);
        }
    }

}
