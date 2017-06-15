package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BindBankUserBean;
import com.shanfu.tianxia.bean.GetBindBankUserBean;
import com.shanfu.tianxia.bean.GetLLInfoBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class BindAccountActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.select_bankcard_tv)
    TextView select_bankcard_tv;
    @Bind(R.id.bank_name_tv)
    TextView bank_name_tv;
    @Bind(R.id.bind_btn)
    Button bind_btn;
    private String nameuser;
    private String bankname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_account);
        ButterKnife.bind(this);
        requestData();
        initView();

    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("绑定账号");
        content_head_back.setOnClickListener(this);

        select_bankcard_tv.setOnClickListener(this);
        bind_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);

            OkGo.post(Urls.getbindbankuser)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GetBindBankUserBean>(this) {
                        @Override
                        public void onSuccess(GetBindBankUserBean getBindBankUserBean, Call call, Response response) {
                            decodeData(getBindBankUserBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(BindAccountActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(GetBindBankUserBean getBindBankUserBean) {
        String err_code = getBindBankUserBean.getErr_code();
        String err_msg = getBindBankUserBean.getErr_msg();
        if ("200".equals(err_code)){
            String bankname = getBindBankUserBean.getData().getData().getBankname();
            String name_user = getBindBankUserBean.getData().getData().getName_user();
            if (bankname != null){
                bind_btn.setVisibility(View.GONE);
                bank_name_tv.setText(name_user);
                select_bankcard_tv.setText(bankname);
                select_bankcard_tv.setClickable(false);
            }
        }else {
            TUtils.showShort(this,err_msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.select_bankcard_tv:
                Intent intent = new Intent(BindAccountActivity.this, TopSelectBankActivity.class);
                intent.putExtra("code",12);
                startActivityForResult(intent,112);
                break;
            case R.id.bind_btn:
                nameuser = bank_name_tv.getText().toString().trim();
                bankname = select_bankcard_tv.getText().toString().trim();
                requestData2();

                break;
        }
    }

    private void requestData2() {
        String time = DateUtils.getLinuxTime();
        String token = MD5Utils.MD5(Constants.appKey + time);
        String uid = SPUtils.getInstance().getString("uid","");

        HttpParams params = new HttpParams();
        params.put("time", time);
        params.put("token", token);
        params.put("uid",uid);

        params.put("name_user",nameuser);
        params.put("bankname",bankname);

        OkGo.post(Urls.bindbankuser)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<BindBankUserBean>(this) {
                    @Override
                    public void onSuccess(BindBankUserBean bindBankUserBean, Call call, Response response) {
                        decodeData2(bindBankUserBean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        TUtils.showShort(BindAccountActivity.this,"数据获取失败，请检查网络后重试");
                    }
                });
    }

    private void decodeData2(BindBankUserBean bindBankUserBean) {
        String err_code = bindBankUserBean.getErr_code();
        String err_msg = bindBankUserBean.getErr_msg();
        if ("200".equals(err_code)){
            TUtils.showShort(BindAccountActivity.this,"绑定成功");
            bind_btn.setVisibility(View.GONE);
            bank_name_tv.setText(nameuser);
            select_bankcard_tv.setText(bankname);
            select_bankcard_tv.setClickable(false);
            Intent intent = new Intent(BindAccountActivity.this,MyWalletActivity.class);
            startActivity(intent);
            finish();
        }else {
            TUtils.showShort(BindAccountActivity.this,err_msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = data.getStringExtra("name");
        select_bankcard_tv.setText(name);
        String no_agree = data.getStringExtra("no_agree");
        String bankName = data.getStringExtra("bankName");
        String card_no = data.getStringExtra("card_no");
        String pay_type = data.getStringExtra("pay_type");
        String bind_mob = data.getStringExtra("bind_mob");
        String vali_date = data.getStringExtra("vali_date");
        String cvv2 = data.getStringExtra("cvv2");
        requestInfo();
    }

    private void requestInfo() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);

            OkGo.post(Urls.getllinfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GetLLInfoBean>(this) {
                        @Override
                        public void onSuccess(GetLLInfoBean getLLInfoBean, Call call, Response response) {
                            decodeInfo(getLLInfoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeInfo(GetLLInfoBean getLLInfoBean) {
        String code = getLLInfoBean.getErr_code();
        String msg = getLLInfoBean.getErr_msg();

        if(!"200".equals(code)){
            TUtils.showShort(this,msg);

        }else {
            String idcard = getLLInfoBean.getData().getData().getIdcard();
            String name_user = getLLInfoBean.getData().getData().getName_user();
            bank_name_tv.setText(name_user);

        }
    }

}
