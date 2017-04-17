package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.UploadUserImg;
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
 * 昵称
 */
public class NiChengActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.ni_cheng_commit)
    TextView ni_cheng_commit;
    @Bind(R.id.ni_cheng_back)
    RelativeLayout ni_cheng_back;
    @Bind(R.id.input_ni_cheng)
    EditText input_ni_cheng;
    private String nicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ni_cheng);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        ni_cheng_back.setOnClickListener(this);
        ni_cheng_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ni_cheng_back:
                finish();
                break;
            case R.id.ni_cheng_commit:
               nicheng =  input_ni_cheng.getText().toString().trim();
                if(TextUtils.isEmpty(nicheng)){
                    TUtils.showShort(NiChengActivity.this,"请输入昵称");
                    return;
                }
                if(nicheng.length()<4||nicheng.length()>20){
                    TUtils.showShort(NiChengActivity.this,"请输入4-20位长度的昵称");
                    return;
                }
                requestData(nicheng);
                break;
        }
    }
    private void requestData(String nicheng){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(NiChengActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);
            params.put("nickname", nicheng);

            OkGo.post(Urls.setNickName)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<UploadUserImg>(this) {
                        @Override
                        public void onSuccess(UploadUserImg uploadUserImg, Call call, Response response) {
                            decodeImageData(uploadUserImg);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(NiChengActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decodeImageData(UploadUserImg uploadUserImg){
        String code = uploadUserImg.getErr_code();
        String msg = uploadUserImg.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken",uploadUserImg.getData().getPtoken());
            SPUtils.getInstance().putString("nickname", nicheng);
            TUtils.showShort(NiChengActivity.this,msg);
            finish();
        }else if("103".equals(code)){
            Intent intent = new Intent(NiChengActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(NiChengActivity.this,msg);
        }

    }
}
