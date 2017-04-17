package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.MyPassword;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 设置交易密码
 */
public class SetUpPwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setup_pwd_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.set_pqy_pwd_next)
    Button set_pqy_pwd_next;

    @Bind(R.id.set_pay_pwd)
    MyPassword set_pay_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setup_pwd_top = (RelativeLayout) findViewById(R.id.setup_pwd_top);
        content_head_back = (RelativeLayout) setup_pwd_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setup_pwd_top.findViewById(R.id.content_head_title);
        content_head_title.setText("设置交易密码");
        content_head_back.setOnClickListener(this);
        set_pqy_pwd_next.setOnClickListener(this);

        set_pay_pwd.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        popKeyboard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.set_pqy_pwd_next:
                String pwd = set_pay_pwd.getText().toString().trim();
                if(TextUtils.isEmpty(pwd)){
                    TUtils.showShort(SetUpPwdActivity.this,"请输入6位数字密码");
                    return;
                }else{
                    String reg = "[0-9]{6}";
                    if (!pwd.matches(reg)) {
                        TUtils.showShort(SetUpPwdActivity.this, "请填写6位数字密码");
                        return;
                    }
                }
                //requestData(MD5Utils.MD5(pwd));
                Intent intent = new Intent(SetUpPwdActivity.this,SetUpPwdNextActivity.class);
                intent.putExtra("pwd",pwd);
                startActivity(intent);
                break;

        }
    }

    private void requestData(String pwdagin){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("paypassword", pwdagin);

            OkGo.post(Urls.setPaypassword)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SetUpPwdActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeResult(RsultBean rsultBean){
        String err_code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        if("200".equals(err_code)){
            Intent intent = new Intent(this,BindingBankCardActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(SetUpPwdActivity.this,msg);
        }


    }

    private void popKeyboard(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask()

                       {

                           public void run()

                           {
                               InputMethodManager m = (InputMethodManager) set_pay_pwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                           }

                       },

                300);
    }
}
