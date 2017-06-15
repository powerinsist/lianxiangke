package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class MyWalletActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.my_wallet_pay)
    RelativeLayout my_wallet_pay;
    @Bind(R.id.my_wallet_balance)
    RelativeLayout my_wallet_balance;
    @Bind(R.id.my_wallet_bankcard)
    RelativeLayout my_wallet_bankcard;
    @Bind(R.id.my_wallet_authen)
    RelativeLayout my_wallet_authen;
    @Bind(R.id.my_wallet_binding)
    RelativeLayout my_wallet_binding;
    @Bind(R.id.my_wallet_detail)
    RelativeLayout my_wallet_detail;
    @Bind(R.id.my_wallet_manage)
    RelativeLayout my_wallet_manage;
    @Bind(R.id.my_wallet_recharge)
    RelativeLayout my_wallet_recharge;
    @Bind(R.id.my_wallet_withdraw)
    RelativeLayout my_wallet_withdraw;
    @Bind(R.id.balance_tv)
    TextView balance_tv;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private Intent intent;
    private String balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("我的钱包");
        content_head_back.setOnClickListener(this);

        my_wallet_pay.setOnClickListener(this);
        my_wallet_balance.setOnClickListener(this);
        my_wallet_bankcard.setOnClickListener(this);
        my_wallet_authen.setOnClickListener(this);
        my_wallet_binding.setOnClickListener(this);
        my_wallet_detail.setOnClickListener(this);
        my_wallet_manage.setOnClickListener(this);
        my_wallet_recharge.setOnClickListener(this);
        my_wallet_withdraw.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //收付款
            case R.id.my_wallet_pay:
//                intent = new Intent(MyWalletActivity.this,RecivePayMentActivity.class);
                intent = new Intent(MyWalletActivity.this,ReciveMoneyActivity.class);
                startActivity(intent);
                break;
            //余额
            case R.id.my_wallet_balance:
                intent = new Intent(MyWalletActivity.this,MyWalletBalanceActivity.class);
                intent.putExtra("balance",balance);
                startActivity(intent);
                break;
            //银行卡
            case R.id.my_wallet_bankcard:
                intent = new Intent(MyWalletActivity.this,MyBankCardActivity.class);
                startActivity(intent);
                break;
            //实名认证
            case R.id.my_wallet_authen:
                intent = new Intent(MyWalletActivity.this,AuthenticationActivity.class);
                startActivity(intent);
                break;
            //绑定账号
            case R.id.my_wallet_binding:
                intent = new Intent(MyWalletActivity.this,MyBankCardActivity.class);
                startActivity(intent);
                break;
            //收支明细
            case R.id.my_wallet_detail:
                intent = new Intent(MyWalletActivity.this,ShouZhiDetailActivity.class);
                startActivity(intent);
                break;
            //密码管理
            case R.id.my_wallet_manage:
                intent = new Intent(MyWalletActivity.this,PassWordManagementActivity.class);
                startActivity(intent);
                break;
            //充值
            case R.id.my_wallet_recharge:
                intent = new Intent(MyWalletActivity.this,TopUpRechangeActivity.class);
                startActivity(intent);
                break;
            //提现
            case R.id.my_wallet_withdraw:
                intent = new Intent(MyWalletActivity.this,WalletWithDrawActivity.class);
                startActivity(intent);
                break;
            //返回
            case R.id.content_head_back:
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
                            TUtils.showShort(MyWalletActivity.this,"数据获取失败，请检查网络后重试");
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
            balance_tv.setText(balance);
        }else {
            TUtils.showShort(MyWalletActivity.this,ret_msg);
        }
    }
}
