package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WithDrawSucessActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.with_date_tv)
    TextView with_date_tv;
    @Bind(R.id.with_bank_tv)
    TextView with_bank_tv;
    @Bind(R.id.with_money)
    TextView with_money;
    @Bind(R.id.with_money_sxf)
    TextView with_money_sxf;
    @Bind(R.id.complete_btn)
    Button complete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw_sucess);
        ButterKnife.bind(this);
        initView();

        String bankname = getIntent().getStringExtra("bankName");
        String money = getIntent().getStringExtra("money");
        String counterfee = getIntent().getStringExtra("counterfee");
        with_bank_tv.setText(bankname);
        with_money.setText(money);
        with_money_sxf.setText(counterfee);
        Log.e("LOG",bankname);
        Log.e("LOG",money);
        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();
        long timemillis = timeInMillis + 2*60*60*1000;

        Date date = new Date(timemillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        String format1 = simpleDateFormat.format(new Date());
        with_date_tv.setText(format);

    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("提现详情");
        content_head_back.setOnClickListener(this);

        complete_btn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.complete_btn:
                Intent intent = new Intent(WithDrawSucessActivity.this,MyWalletBalanceActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
