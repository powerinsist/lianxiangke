package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
 * 修改登录密码
 */
public class SettingLoginPwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout change_login_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.input_ogin_pay_pwd)
    EditText input_ogin_pay_pwd;
    @Bind(R.id.input_new_login_pwd)
    EditText input_new_login_pwd;
    @Bind(R.id.input_queren_new_login_pwd)
    EditText input_queren_new_login_pwd;
    @Bind(R.id.change_button)
    Button change_button;
    @Bind(R.id.chang_longin_yanzhengma)
    EditText chang_longin_yanzhengma;
    @Bind(R.id.chang_longin_send)
    ImageButton chang_longin_send;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_login_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        change_login_top = (RelativeLayout) findViewById(R.id.change_login_top);
        content_head_back = (RelativeLayout) change_login_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) change_login_top.findViewById(R.id.content_head_title);
        content_head_title.setText("修改登录密码");
        content_head_back.setOnClickListener(this);
        change_button.setOnClickListener(this);
        chang_longin_send.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.change_button:
                String old_pwd = input_ogin_pay_pwd.getText().toString().trim();
                String new_pwd = input_new_login_pwd.getText().toString().trim();
                String queren_new_pwd = input_queren_new_login_pwd.getText().toString().trim();
                String yanzhengma = chang_longin_yanzhengma.getText().toString().trim();
                if(TextUtils.isEmpty(old_pwd)){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入登录密码");
                    return;
                }
                if(TextUtils.isEmpty(new_pwd)){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入新密码");
                    return;
                }
                if(TextUtils.isEmpty(queren_new_pwd)){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入确认的新密码");
                    return;
                }
                if(TextUtils.isEmpty(yanzhengma)){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入确认的验证码");
                    return;
                }
                if(old_pwd.length()<8||old_pwd.length()>20){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入8~20位原登录密码");
                    return;
                }
                if(new_pwd.length()<8||new_pwd.length()>20){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入8~20位新登录密码");
                    return;
                }
                if(queren_new_pwd.length()<8||queren_new_pwd.length()>20){
                    TUtils.showShort(SettingLoginPwdActivity.this,"请输入8~20位确认登录密码");
                    return;
                }

                if(!new_pwd.equals(queren_new_pwd)){
                    TUtils.showShort(SettingLoginPwdActivity.this,"输入的新密码与确认密码不一致，请重新输入");
                    return;
                }

                requestData(MD5Utils.MD5(old_pwd),MD5Utils.MD5(new_pwd),MD5Utils.MD5(queren_new_pwd),yanzhengma);
                break;
            case R.id.chang_longin_send:
                //// TODO: 2017/4/4  发送验证码
                requestCode();
                break;
            
        }



    }
    private void requestCode(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(SettingLoginPwdActivity.this);
            String phone = SPUtils.getInstance().getString("phoneNum","");
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid","");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("phone", phone);

            OkGo.post(Urls.modifyLoginPasswordSmsCode)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeResultCode(rsultBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SettingLoginPwdActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void decodeResultCode(RsultBean rsultBean){
        String code = rsultBean.getErr_code();
       String msg =  rsultBean.getErr_msg();
        if(!"200".equals(code)){
            TUtils.showShort(SettingLoginPwdActivity.this, msg);
        }

    }
    private void requestData(String oldPwd,String newPwd,String queren,String code){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(SettingLoginPwdActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid","");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);
            params.put("oldpassword", oldPwd);
            params.put("newpassword", newPwd);
            params.put("confirmPassword", queren);
            params.put("code", code);
            OkGo.post(Urls.modifypassword)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SettingLoginPwdActivity.this, "数据获取失败，请检查网络后重试");
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
            TUtils.showShort(SettingLoginPwdActivity.this, "修改密码成功");
            finish();
        }else if("103".equals(err_code)){
            Intent intent = new Intent(SettingLoginPwdActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else {
            TUtils.showShort(SettingLoginPwdActivity.this, msg);
        }


    }
}
