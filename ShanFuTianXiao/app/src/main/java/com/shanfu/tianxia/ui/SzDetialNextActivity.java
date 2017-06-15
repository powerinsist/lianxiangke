package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SzDetialNextActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.sz_next_fangshi_tv)
    TextView sz_next_fangshi_tv;
    @Bind(R.id.sz_next_money_tv)
    TextView sz_next_money_tv;
    @Bind(R.id.sz_next_leixing_tv)
    TextView sz_next_leixing_tv;
    @Bind(R.id.sz_next_time_tv)
    TextView sz_next_time_tv;
    @Bind(R.id.sz_next_danhao_tv)
    TextView sz_next_danhao_tv;
//    @Bind(R.id.sz_next_yue_tv)
//    TextView sz_next_yue_tv;
    @Bind(R.id.sz_next_beizhu_tv)
    TextView sz_next_beizhu_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sz_detial_next);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("收支明细");
        content_head_back.setOnClickListener(this);

        Intent intent = getIntent();
        String counterfee = intent.getStringExtra("counterfee");
        String money = intent.getStringExtra("money");
        String orderid = intent.getStringExtra("orderid");
        String payment = intent.getStringExtra("payment");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String type = intent.getStringExtra("type");

        sz_next_beizhu_tv.setText(counterfee);
        if ("+".equals(payment)){
            sz_next_money_tv.setText("+ "+money);
            sz_next_money_tv.setTextColor(Color.rgb(26, 173, 25));
            sz_next_fangshi_tv.setText("入账金额");
            sz_next_leixing_tv.setText("收入");
        }else {
            sz_next_money_tv.setText("- "+money);
            sz_next_money_tv.setTextColor(Color.BLACK);
            sz_next_fangshi_tv.setText("出账金额");
            sz_next_leixing_tv.setText("支出");
        }
        sz_next_time_tv.setText(time);
        sz_next_danhao_tv.setText(orderid);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
        }
    }
}
