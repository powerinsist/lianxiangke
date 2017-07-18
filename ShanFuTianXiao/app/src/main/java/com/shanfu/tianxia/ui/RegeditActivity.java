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
import com.shanfu.tianxia.bean.RegeditBean;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
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
 *i注册
 */

public class RegeditActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout regist_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.input_phone_num)
    EditText input_phone_num;
    @Bind(R.id.input_yanzhengma)
    EditText input_yanzhengma;

    @Bind(R.id.input_login_name)
    EditText input_login_name;
    @Bind(R.id.input_login_idcard)
    EditText input_login_idcard;


    @Bind(R.id.re_send)
    TextView re_send;
    @Bind(R.id.input_login_pwd)
    EditText input_login_pwd;
    @Bind(R.id.input_pay_pwd)
    EditText input_pay_pwd;
    @Bind(R.id.input_tui_jian_ren)
    EditText input_tui_jian_ren;
    @Bind(R.id.regist_button)
    Button regist_button;
    @Bind(R.id.re_send_rl)
    RelativeLayout re_send_rl;
    @Bind(R.id.lxk_xieyi_tv)
    TextView lxk_xieyi_tv;


    private String phoneNum;
    private String phone_num;
    private String[] data_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regedit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        regist_top = (RelativeLayout) findViewById(R.id.regist_top);
        content_head_back = (RelativeLayout) regist_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) regist_top.findViewById(R.id.content_head_title);
        content_head_title.setText("注册");
        content_head_back.setOnClickListener(this);

        regist_button.setOnClickListener(this);
        re_send.setOnClickListener(this);
        lxk_xieyi_tv.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.regist_button:

                phoneNum = input_phone_num.getText().toString().trim();
                String yanzhengma = input_yanzhengma.getText().toString().trim();
                String login_pwd = input_login_pwd.getText().toString().trim();
                String pay_pwd = input_pay_pwd.getText().toString().trim();
                String tui_jian_ren = input_tui_jian_ren.getText().toString().trim();

                if(TextUtils.isEmpty(phoneNum)){
                    TUtils.showShort(RegeditActivity.this,"手机号码不能为空");
                    return;
                }
                if(phoneNum.length()!=11){
                    TUtils.showShort(RegeditActivity.this, "请输入正确的手机号码");
                    return;
                }
                if(TextUtils.isEmpty(yanzhengma)){
                    TUtils.showShort(RegeditActivity.this,"验证码不能为空");
                    return;
                }
                if(yanzhengma.length()!=6){
                    TUtils.showShort(RegeditActivity.this,"验证码格式不对,请重新输入");
                    return;
                }
                if(TextUtils.isEmpty(login_pwd)){
                    TUtils.showShort(RegeditActivity.this,"密码不能为空");
                    return;
                }
                if(TextUtils.isEmpty(pay_pwd)){
                    TUtils.showShort(RegeditActivity.this,"支付密码不能为空");
                    return;
                }
                if(login_pwd.length()<8||login_pwd.length()>20){
                    TUtils.showShort(RegeditActivity.this,"密码的长度为8~20位,请重新输入");
                    return;
                }
                if(pay_pwd.length() != 6){
                    TUtils.showShort(RegeditActivity.this,"密码的长度为8~20位,请重新输入");
                    return;
                }

                if(TextUtils.isEmpty(tui_jian_ren)){

                        TUtils.showShort(RegeditActivity.this, "请填写推荐人的ID");
                        return;

                }
                register(phoneNum,yanzhengma, MD5Utils.MD5(login_pwd),tui_jian_ren);
                break;
            //发送验证码
            case R.id.re_send:
                phone_num = input_phone_num.getText().toString().trim();
                data_phone = new String[]{phone_num};

                if(TextUtils.isEmpty(phone_num)){
                    TUtils.showShort(RegeditActivity.this,"手机号码不能为空");
                    return;
                }
                else if(phone_num.length()!=11){
                    TUtils.showShort(RegeditActivity.this, "请输入正确的手机号码");
                    return;
                }else {
                    requestSendCode();
//                    registerCode(phone_num);
                }
                break;
            case R.id.lxk_xieyi_tv:
                Intent intent = new Intent(RegeditActivity.this,LxkAllowActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void requestSendCode() {
        String time = DateUtils.getLinuxTime();
        String token = MD5Utils.MD5(Constants.appKey + time);
        String uid = SPUtils.getInstance().getString("uid", "");
        String[] data_uid = {uid};
        String[] data_flag = {"0"};

        HttpParams params = new HttpParams();
        params.put("time",time);
        params.put("token",token);
        params.put("data[phone]",data_phone[0]);
        params.put("data[uid]",data_uid[0]);
        params.put("data[flag_send]",data_flag[0]);

        OkGo.post(Urls.smssend_ll)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<RsultBean>(this) {
                    @Override
                    public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                        decodeSendCode(rsultBean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        TUtils.showShort(RegeditActivity.this,"网络数据获取失败，请稍后重试");
                    }
                });

    }

    private void decodeSendCode(RsultBean rsultBean) {
        String err_code = rsultBean.getErr_code();
        String err_msg = rsultBean.getErr_msg();
        if (err_code.equals("200")){
            TUtils.showShort(this,err_msg);
            TimeCountUtil timeCountUtil = new TimeCountUtil(60000,1000,re_send,re_send_rl);
            timeCountUtil.start();
        }else {
            TUtils.showShort(this,err_msg);
        }
    }

    private void register(String phoneNum,String yanzhengma,String pwd,String yaoqingren){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(RegeditActivity.this);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("phone", phoneNum);
            params.put("code", yanzhengma);
            params.put("password", pwd);
            params.put("fuid_phone", yaoqingren);
            params.put("version", version);
            OkGo.post(Urls.register)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean regeditBean, Call call, Response response) {
                            decodeResult(regeditBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(RegeditActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void decodeResult(RegeditBean regeditBean){

        String status = regeditBean.getErr_code();
        String msg =  regeditBean.getErr_msg();
        if("200".equals(status)){
            TUtils.showShort(RegeditActivity.this,msg);
            SPUtils.getInstance().putString("phoneNum", phoneNum);
            SPUtils.getInstance().putString("uid", regeditBean.getData().getUid());
            SPUtils.getInstance().putString("ptoken", regeditBean.getData().getPtoken());
            this.finish();
            Intent intent = new Intent(RegeditActivity.this,MainActivity.class);
            intent.putExtra("comefrom","home");
            startActivity(intent);
        }
        else{
            TUtils.showShort(RegeditActivity.this,msg);
        }

    }

    /*private void registerCode(String phone){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);


            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("phone", phone);

            OkGo.post(Urls.register_code)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this) {
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            String succeed = rsultBean.getErr_code();
                            if(!"200".equals(succeed)){
                                TUtils.showShort(RegeditActivity.this,  rsultBean.getErr_msg());
                            }
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(RegeditActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
