package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.LoginEvent;
import com.shanfu.tianxia.bean.RegeditBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener {
    EditText ed_login_user_name;
    EditText ed_login_password;
    Button btn_login;
    TextView tv_login_forget_password;
    TextView tv_login_quick_regedit;
//    ImageView dl_baomi;
    ImageView login_close;
    private String phoneNum;
    private String sp_phoneNum;

    @Bind(R.id.forget_password)
    TextView forget_password;
    private Intent intent;
    private String comefrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_lxk);
        ButterKnife.bind(this);
        sp_phoneNum = SPUtils.getInstance().getString("phoneNum","");
        comefrom = getIntent().getStringExtra("comefrom");
        init();
    }



    private void init() {
        ed_login_user_name = (EditText) findViewById(R.id.ed_login_user_name);
        ed_login_password = (EditText) findViewById(R.id.ed_login_password);
        if(!TextUtils.isEmpty(sp_phoneNum)){
            ed_login_user_name.setText(sp_phoneNum);
            ed_login_user_name.setSelection(sp_phoneNum.length());
        }
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_login_quick_regedit = (TextView) findViewById(R.id.tv_login_quick_regedit);
        tv_login_forget_password = (TextView) findViewById(R.id.tv_login_forget_password);
        login_close = (ImageView) findViewById(R.id.login_close);
        String getPhoneNum = SPUtils.getInstance().getString("phoneNum", "");
        if(!TextUtils.isEmpty(getPhoneNum)){
            ed_login_user_name.setText(getPhoneNum);
            ed_login_user_name.setSelection(ed_login_user_name.getText().length());
        }
        btn_login.setOnClickListener(this);
        tv_login_quick_regedit.setOnClickListener(this);
        tv_login_forget_password.setOnClickListener(this);
        login_close.setOnClickListener(this);
        forget_password.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_close:
                if(!"CommodityDetailsActivity".equals(comefrom)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("comefrom", "home");
                    startActivity(intent);
                }
                SPUtils.getInstance().putBoolean("request", false);
                finish();
                break;
            case R.id.btn_login:
                phoneNum = ed_login_user_name.getText().toString().trim();
                String pwd = ed_login_password.getText().toString().trim();
                if(TextUtils.isEmpty(phoneNum)||TextUtils.isEmpty(pwd)){
                    TUtils.showShort(LoginActivity.this, "用户名或密码不能为空");
                    return;
                }
                if(pwd.length()<6||pwd.length()>20){
                    TUtils.showShort(LoginActivity.this, "密码只能为8~20位");
                }

                requestData(phoneNum, MD5Utils.MD5(pwd));

                break;

            case R.id.tv_login_quick_regedit:
                intent = new Intent(this,RegeditActivity.class);
               // intent.putExtra("comefrom", comefrom);
                startActivity(intent);
                break;
            case R.id.forget_password:
                intent = new Intent(this,FrogetPassWordActivity.class);
                // intent.putExtra("comefrom", comefrom);
                startActivity(intent);
                break;
        }
    }

    private void requestData(String phone,String pwd){
        try {
            String version = AppUtils.getVerName(LoginActivity.this);
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("phone", phone);
            params.put("password", pwd);
            params.put("version", version);


            OkGo.post(Urls.login)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean regeditBean, Call call, Response response) {
                            decodeResult(regeditBean,call);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(LoginActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(RegeditBean regeditBean,Call call){

       String errCode = regeditBean.getErr_code();
        String msg =  regeditBean.getErr_msg();
        if("200".equals(errCode)){
            SPUtils.getInstance().putString("phoneNum",phoneNum);
            SPUtils.getInstance().putString("uid",regeditBean.getData().getUid());
            SPUtils.getInstance().putString("ptoken", regeditBean.getData().getPtoken());
            SPUtils.getInstance().putString("b_status", regeditBean.getData().getB_status());
            SPUtils.getInstance().putString("p_status", regeditBean.getData().getP_status());
            SPUtils.getInstance().putString("t_status", regeditBean.getData().getT_status());
            SPUtils.getInstance().putBoolean("request", true);
            SPUtils.getInstance().putString("logintoken",regeditBean.getData().getLogintoken());
            //TUtils.showShort(LoginActivity.this,msg);
            Log.e("LOG",regeditBean.getData().getLogintoken());
            //TODO  发送广播A
            EventBus.getDefault().post(new LoginEvent());

            intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("comefrom","home");
            startActivity(intent);
            this.finish();
        }else{
            TUtils.showShort(LoginActivity.this, msg);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(!"CommodityDetailsActivity".equals(comefrom)){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("comefrom", "home");
                startActivity(intent);
            }
            SPUtils.getInstance().putBoolean("request", false);
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }
}
