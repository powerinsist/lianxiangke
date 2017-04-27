package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.utils.SPUtils;

/**
 * 安全设置
 */
public class SecurityActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_login_pwd;
    private RelativeLayout setting_pay_pwd;
    private Intent intent;
    private RelativeLayout safe_setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private String p_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        p_status = SPUtils.getInstance().getString("p_status","");
        init();
    }

    private void init() {
        safe_setting_top = (RelativeLayout) findViewById(R.id.safe_setting_top);
        content_head_back = (RelativeLayout) safe_setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) safe_setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("安全设置");
        content_head_back.setOnClickListener(this);
        setting_login_pwd = (RelativeLayout) findViewById(R.id.setting_login_pwd);
        setting_pay_pwd = (RelativeLayout) findViewById(R.id.setting_pay_pwd);
        setting_login_pwd.setOnClickListener(this);
        setting_pay_pwd.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.setting_login_pwd:
                intent = new Intent(this,SettingLoginPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_pay_pwd:
                if("1".equals(p_status)){
                    intent = new Intent(this,ChangePwdActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(this,SetUpPwdActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }
}
