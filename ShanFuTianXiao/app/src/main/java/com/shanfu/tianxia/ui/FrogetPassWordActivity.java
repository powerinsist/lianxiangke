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
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.TimeCountUtil;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记密码
 */
public class FrogetPassWordActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout forget_pwd_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.forget_phone_num)
    EditText forget_phone_num;
    @Bind(R.id.froget_yanzhengma)
    EditText froget_yanzhengma;
    @Bind(R.id.forget_send)
    RelativeLayout forget_send;
    @Bind(R.id.froget_pwd_next)
    Button froget_pwd_next;
    @Bind(R.id.forget_send_tv)
    TextView forget_send_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_froget_pass_word);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        forget_pwd_top = (RelativeLayout) findViewById(R.id.forget_pwd_top);
        content_head_back = (RelativeLayout) forget_pwd_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) forget_pwd_top.findViewById(R.id.content_head_title);
        content_head_title.setText("忘记密码");
        content_head_back.setOnClickListener(this);
        forget_send.setOnClickListener(this);
        froget_pwd_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.forget_send:
                String num = forget_phone_num.getText().toString().trim();
                if(TextUtils.isEmpty(num)){
                    TUtils.showShort(FrogetPassWordActivity.this, "手机号码不能为空");
                    return;
                }
                if(num.length()!=11){
                    TUtils.showShort(FrogetPassWordActivity.this, "请输入正确的手机号码");
                    return;
                }
                registerCode(num);
                TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,forget_send_tv,forget_send);
                timeCountUtil.start();
                break;
            case R.id.froget_pwd_next:
                String phone_num = forget_phone_num.getText().toString().trim();
                String send_code = froget_yanzhengma.getText().toString().trim();
                if(TextUtils.isEmpty(phone_num)){
                    TUtils.showShort(FrogetPassWordActivity.this,"手机号不能为空");
                    return;
                }
                if(TextUtils.isEmpty(send_code)){
                    TUtils.showShort(FrogetPassWordActivity.this,"验证码不能为空");
                    return;
                }

                Intent intent = new Intent(FrogetPassWordActivity.this,ForgetPwdNextActivity.class);
                intent.putExtra("send_code",send_code);
                intent.putExtra("phone",phone_num);
                startActivity(intent);
                break;
        }
    }

    private void registerCode(String phone){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);


            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("phone", phone);

            OkGo.post(Urls.sendcodeByfoundpassword)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                           decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(FrogetPassWordActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(RsultBean rsultBean){
       String code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        if(!"200".equals(code)){
           TUtils.showShort(FrogetPassWordActivity.this,msg);
        }
    }
}
