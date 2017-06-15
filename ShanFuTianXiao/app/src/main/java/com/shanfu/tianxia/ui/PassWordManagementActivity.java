package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PassWordManagementActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.forget_pwd_rl)
    RelativeLayout forget_pwd_rl;
    @Bind(R.id.modify_pwd_rl)
    RelativeLayout modify_pwd_rl;
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word_management);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("密码管理");
        content_head_back.setOnClickListener(this);

        forget_pwd_rl.setOnClickListener(this);
        modify_pwd_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_pwd_rl:
                intent = new Intent(PassWordManagementActivity.this,ForgetPayPwdActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.modify_pwd_rl:
                intent = new Intent(PassWordManagementActivity.this,ChangePwdActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.content_head_back:
                finish();
                break;
        }
    }
}
