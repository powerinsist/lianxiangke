package com.shanfu.tianxia.ui;

import android.content.Intent;
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
import com.shanfu.tianxia.bean.SingleUserQueryBean;
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

/**
 * 余额
 */
public class MyWalletBalanceActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.chongzhi_btn)
    Button chongzhi_btn;
    @Bind(R.id.tixian_btn)
    Button tixian_btn;
    @Bind(R.id.content_head_back)
    RelativeLayout content_head_back;
    @Bind(R.id.content_head_right)
    TextView content_head_right;
    @Bind(R.id.bank_num_tv)
    TextView bank_num_tv;
    private Intent intent;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_balance);
        ButterKnife.bind(this);
        balance = getIntent().getStringExtra("balance");
        initView();
    }

    private void initView(){
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);
        chongzhi_btn.setOnClickListener(this);
        tixian_btn.setOnClickListener(this);

//        bank_num_tv.setText(balance);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.content_head_right:
                intent = new Intent(MyWalletBalanceActivity.this,ShouZhiDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.chongzhi_btn:
                intent = new Intent(MyWalletBalanceActivity.this,TopUpRechangeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tixian_btn:
                intent = new Intent(MyWalletBalanceActivity.this,WalletWithDrawActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.singleuserquery)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SingleUserQueryBean>(this) {
                        @Override
                        public void onSuccess(SingleUserQueryBean singleUserQueryBean, Call call, Response response) {
                            decodeData(singleUserQueryBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyWalletBalanceActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(SingleUserQueryBean singleUserQueryBean) {
        String err_code = singleUserQueryBean.getErr_code();
        String err_msg = singleUserQueryBean.getErr_msg();
        String ret_code = singleUserQueryBean.getData().getData().getRet_code();
        String ret_msg = singleUserQueryBean.getData().getData().getRet_msg();

        if ("200".equals(err_code)&&"0000".equals(ret_code)){
            balance = singleUserQueryBean.getData().getData().getBalance();
            bank_num_tv.setText(balance);
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }
}
