package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RegeditBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.MyPassword;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SetUpPwdNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setup_pwd_next_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.set_pqy_pwd_next_two)
    Button set_pqy_pwd_next_two;

    @Bind(R.id.set_pay_pwd_next)
    MyPassword set_pay_pwd_next;
    private String pwd;
    private String t_status,p_status,b_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_pwd_next);
        ButterKnife.bind(this);
        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");
        initView();
        pwd = getIntent().getStringExtra("pwd");
    }
    private void initView() {
        setup_pwd_next_top = (RelativeLayout) findViewById(R.id.setup_pwd_next_top);
        content_head_back = (RelativeLayout) setup_pwd_next_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setup_pwd_next_top.findViewById(R.id.content_head_title);
        content_head_title.setText("设置交易密码");
        content_head_back.setOnClickListener(this);
        set_pqy_pwd_next_two.setOnClickListener(this);

        set_pay_pwd_next.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        popKeyboard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.set_pqy_pwd_next_two:
                String pwd_again = set_pay_pwd_next.getText().toString().trim();
                if(TextUtils.isEmpty(pwd_again)){
                    TUtils.showShort(SetUpPwdNextActivity.this, "请输入6位数字密码");
                    return;
                }else{
                    String reg = "[0-9]{6}";
                    if (!pwd_again.matches(reg)) {
                        TUtils.showShort(SetUpPwdNextActivity.this, "请填写6位数字密码");
                        return;
                    }
                }
                if(!pwd.equals(pwd_again)){
                    TUtils.showShort(SetUpPwdNextActivity.this, "两次输入的密码不一致,请重新输入");
                    return;
                }

                requestData(MD5Utils.MD5(pwd_again));
                break;
        }
    }
    private void popKeyboard(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask()

                       {
                           public void run()

                           {
                               InputMethodManager m = (InputMethodManager) set_pay_pwd_next.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                           }

                       },

                300);
    }
    private void requestData(String pwdagin){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(SetUpPwdNextActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("paypassword", pwdagin);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);

            OkGo.post(Urls.setPaypassword)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SetUpPwdNextActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeResult(RegeditBean rsultBean){
        String err_code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        Log.e("LOG",err_code);
        if("200".equals(err_code)){
            SPUtils.getInstance().putString("ptoken", rsultBean.getData().getPtoken());
            SPUtils.getInstance().putString("p_status", "1");
            if("1".equals(b_status)){
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("comefrom","mine");
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this,BindingBankCardActivity.class);
                startActivity(intent);
            }

        }else if("103".equals(err_code)){
            Intent intent = new Intent(SetUpPwdNextActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(SetUpPwdNextActivity.this,msg);
        }


    }
}
