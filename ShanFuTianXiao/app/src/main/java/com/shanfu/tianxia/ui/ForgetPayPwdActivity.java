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
 * 修改交易密码第一步
 */

public class ForgetPayPwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.forget_pay_name)
    EditText forget_pay_name;
    @Bind(R.id.forget_pay_idcard)
    EditText forget_pay_idcard;
    @Bind(R.id.forget_pay_bankNum)
    EditText forget_pay_bankNum;
    @Bind(R.id.forget_pay_phone)
    EditText forget_pay_phone;
    @Bind(R.id.froget_pay_pwd_next)
    Button froget_pay_pwd_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pay_word);
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
        content_head_title.setText("忘记交易密码");
        content_head_back.setOnClickListener(this);

        froget_pay_pwd_next.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.froget_pay_pwd_next:
                String name = forget_pay_name.getText().toString().trim();
                String idcard = forget_pay_idcard.getText().toString().trim();
                String bankNum = forget_pay_bankNum.getText().toString().trim();
                String phone = forget_pay_phone.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    TUtils.showShort(ForgetPayPwdActivity.this,"请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(idcard)||idcard.length() != 18){
                    TUtils.showShort(ForgetPayPwdActivity.this,"请输入正确身份证号码");
                    return;
                }
                if (TextUtils.isEmpty(bankNum)){
                    TUtils.showShort(ForgetPayPwdActivity.this,"请输入银行卡号");
                    return;
                }
                if (TextUtils.isEmpty(phone) || phone.length() != 11){
                    TUtils.showShort(ForgetPayPwdActivity.this,"请输入银行预留手机号");
                    return;
                }
                Intent intent = new Intent(ForgetPayPwdActivity.this,ForgetPayPwdNextActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("idcard",idcard);
                intent.putExtra("bankNum",bankNum);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
                break;
        }
    }

}
