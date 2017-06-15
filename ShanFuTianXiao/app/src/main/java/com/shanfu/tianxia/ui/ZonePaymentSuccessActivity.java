package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ZonePaymentSuccessActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.shop_name_tv)
    TextView shop_name_tv;
    @Bind(R.id.shop_phone_tv)
    TextView shop_phone_tv;
    @Bind(R.id.shop_address_tv)
    TextView shop_address_tv;
    @Bind(R.id.shop_pay_tv)
    TextView shop_pay_tv;
    @Bind(R.id.check_log_tv)
    TextView check_log_tv;
    @Bind(R.id.back_shop_tv)
    TextView back_shop_tv;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_payment_success);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("支付成功");
        content_head_back.setOnClickListener(this);

        check_log_tv.setOnClickListener(this);
        back_shop_tv.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.check_log_tv:
                intent = new Intent(ZonePaymentSuccessActivity.this,TraceActivity.class);
                startActivity(intent);
                break;
            case R.id.back_shop_tv:
                intent = new Intent(ZonePaymentSuccessActivity.this,ExchangeZoneActivity.class);
                startActivity(intent);
                break;
        }
    }
}
