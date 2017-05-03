package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 实名认证
 */
public class AuthenticationActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout authentication_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.regedit_next)
    Button regedit_next;
    @Bind(R.id.real_name)
    EditText real_name;
    @Bind(R.id.ed_regedit_aut_idcard)
    EditText ed_regedit_aut_idcard;
    private String t_status,p_status,b_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");
        initView();
    }

    private void initView() {
        authentication_top = (RelativeLayout) findViewById(R.id.authentication_top);
        content_head_back = (RelativeLayout) authentication_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) authentication_top.findViewById(R.id.content_head_title);
        content_head_title.setText("实名认证");
        content_head_back.setOnClickListener(this);
        regedit_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.regedit_next:

                String id_num = ed_regedit_aut_idcard.getText().toString().trim();
                String name = real_name.getText().toString().trim();
                if(TextUtils.isEmpty(id_num)){
                    TUtils.showShort(AuthenticationActivity.this,"姓名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    TUtils.showShort(AuthenticationActivity.this,"身份证号码不能为空");
                    return;
                }
               // Intent intent = new Intent(this,SetUpPwdActivity.class);
                //startActivity(intent);
                requestData(name,id_num);

                break;
        }

    }

    private void requestData(String name,String id){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(AuthenticationActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("truename", name);
            params.put("idcard", id);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);
            Log.e("LOG",uid);
            OkGo.post(Urls.setTruename)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(AuthenticationActivity.this, "数据获取失败，请检查网络后重试");
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
            SPUtils.getInstance().putString("t_status", "1");
            //设置过交易密码
            if("1".equals(p_status)){
               //绑定过银行卡
                if("1".equals(b_status)){
                    finish();
                }else{
                    //没有绑定银行卡
                    //没有设置过交易密码
                    Intent intent = new Intent(this,BindingBankCardActivity.class);
                    startActivity(intent);
                }

            }else{
                //没有设置过交易密码
                Intent intent = new Intent(this,SetUpPwdActivity.class);
                startActivity(intent);
            }

        }else if("103".equals(err_code)){
            Intent intent = new Intent(AuthenticationActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(AuthenticationActivity.this,msg);
        }


    }
}
