package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BonusRedPacketBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.MyDialogUtils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class WalletBalancePayActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.money_tv)
    TextView money_tv;
    @Bind(R.id.pay_ok_btn)
    Button pay_ok_btn;
    private Intent intent;
    private int code;
    private String money_balabce;
    private Handler mHandler = new Handler();
    private String url;
    private String shopspeople_redmoney;
    private String redid;
    private String ticketnum;
    private String money_order;
    private float red_money;
    private float tick_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_balance_pay);
        ButterKnife.bind(this);

        money_balabce = getIntent().getStringExtra("money");
        url = getIntent().getStringExtra("url");

        initView();

        money_order = getIntent().getStringExtra("money_order");
        shopspeople_redmoney = getIntent().getStringExtra("shopspeople_redmoney");
        redid = getIntent().getStringExtra("redid");
        ticketnum = getIntent().getStringExtra("ticketnum");
        mHandler.postDelayed(mRunnable,500);


    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if ("领取红包".equals(url)){
                red_money = Float.parseFloat(shopspeople_redmoney);
                tick_num = Float.parseFloat(ticketnum);
                //TODO 请求服务器获取红包信息
                if (tick_num > 0){
//                    openPupRed();
                    showDialog();
                }
            }
        }
    };

    private void requestRedData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            params.put("redid",redid);
            params.put("money_order",shopspeople_redmoney);

            OkGo.post(Urls.Bonus_redpacket)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BonusRedPacketBean>(this) {
                        @Override
                        public void onSuccess(BonusRedPacketBean bonusRedPacketBean, Call call, Response response) {
                            decodeBonus(bonusRedPacketBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(WalletBalancePayActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBonus(BonusRedPacketBean bonusRedPacketBean) {
        String err_code = bonusRedPacketBean.getErr_code();
        String err_msg = bonusRedPacketBean.getErr_msg();
        String ret_code = bonusRedPacketBean.getData().getData().getRet_code();
        String ret_msg = bonusRedPacketBean.getData().getData().getRet_msg();

        String money_order = bonusRedPacketBean.getData().getData().getMoney_order();

        if ("200".equals(err_code) && "0000".equals(ret_code)){
            Intent intent = new Intent(WalletBalancePayActivity.this,RedPayPacketRecordActivity.class);
            intent.putExtra("money_order",money_order);
            intent.putExtra("redid",redid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else {
            TUtils.showShort(this,ret_msg);
        }
    }

    private void initView(){
        pay_ok_btn.setOnClickListener(this);
        money_tv.setText(money_balabce);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_ok_btn:
                intent = new Intent(WalletBalancePayActivity.this,MyWalletBalanceActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void showDialog() {
//        AlertDialog dialog = new AlertDialog.Builder(this).create();
        final MyDialogUtils dialog = new MyDialogUtils(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_red_paket_popup,null);
        TextView red_money_tv = (TextView) dialogView.findViewById(R.id.red_money_tv);
        TextView red_lxp_tv = (TextView) dialogView.findViewById(R.id.red_lxp_tv);
        Button get_red_btn = (Button) dialogView.findViewById(R.id.get_red_btn);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        if (red_money > 0){
            red_money_tv.setText("￥ "+shopspeople_redmoney+"元");
            red_lxp_tv.setText(shopspeople_redmoney+"元红包+"+ticketnum+"张联享票");
        }else {
            red_money_tv.setText(ticketnum+"张联享票");
            red_lxp_tv.setText(ticketnum+"张联享票");
            get_red_btn.setText("关  闭");
        }

        get_red_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (red_money > 0){
                    requestRedData();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            intent = new Intent(WalletBalancePayActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
