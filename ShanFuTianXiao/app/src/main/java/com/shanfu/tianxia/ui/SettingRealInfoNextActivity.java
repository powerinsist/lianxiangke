package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shanfu.tianxia.bean.ModifyUserInfoBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SettingRealInfoNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.name_ed)
    EditText name_ed;
    @Bind(R.id.idcard_ed)
    EditText idcard_ed;
    @Bind(R.id.next_btn)
    Button next_btn;
    private String name_user;
    private String no_idcard;
    private String tokens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_real_info_next);
        ButterKnife.bind(this);
        tokens = getIntent().getStringExtra("tokens");
        initView();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("修改实名认证信息");
        content_head_back.setOnClickListener(this);

        next_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.next_btn:
                name_user = name_ed.getText().toString().trim();
                no_idcard = idcard_ed.getText().toString().trim();
                if (TextUtils.isEmpty(name_user)|| name_user.length() < 2|| name_user.length() > 6){
                    TUtils.showShort(this,"请输入真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(no_idcard)|| no_idcard.length() != 18){
                    TUtils.showShort(this,"请输入正确身份证号");
                    return;
                }
                requestData();
                break;
        }
    }

    private void requestData() {
        String time = DateUtils.getLinuxTime();
        String token = MD5Utils.MD5(Constants.appKey + time);
        String uid = SPUtils.getInstance().getString("uid","");

        HttpParams params = new HttpParams();
        params.put("time", time);
        params.put("token", token);
        params.put("uid",uid);
        params.put("tokens",tokens);
        params.put("name_user",name_user);
        params.put("no_idcard",no_idcard);

        OkGo.post(Urls.modifyuserinfo)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<ModifyUserInfoBean>(this) {
                    @Override
                    public void onSuccess(ModifyUserInfoBean modifyUserInfoBean, Call call, Response response) {
                        decodeModifyUserInfo(modifyUserInfoBean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        TUtils.showShort(SettingRealInfoNextActivity.this,"数据获取失败，请检查网络后重试");
                    }
                });
    }

    private void decodeModifyUserInfo(ModifyUserInfoBean modifyUserInfoBean) {
        String code = modifyUserInfoBean.getErr_code();
        String msg = modifyUserInfoBean.getErr_msg();
        String ret_msg = modifyUserInfoBean.getData().getData().getRet_msg();
        String ret_code = modifyUserInfoBean.getData().getData().getRet_code();
        if(!"200".equals(code)){
            TUtils.showShort(this,ret_msg);
        }else {
            Intent intent = new Intent(SettingRealInfoNextActivity.this,SettingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
