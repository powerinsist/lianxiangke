package com.shanfu.tianxia.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.ButterKnife;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends BaseFragmentActivity implements View.OnClickListener {
    private RelativeLayout add_bank_card_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        add_bank_card_top = (RelativeLayout) findViewById(R.id.add_bank_card_top);
        content_head_back = (RelativeLayout) add_bank_card_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) add_bank_card_top.findViewById(R.id.content_head_title);
        content_head_title.setText("添加银行卡");
        content_head_back.setOnClickListener(this);

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
