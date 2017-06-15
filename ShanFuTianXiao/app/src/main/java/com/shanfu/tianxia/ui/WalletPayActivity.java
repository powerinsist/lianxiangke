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

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.utils.TUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WalletPayActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.amount_entered)
    EditText amount_entered;
    @Bind(R.id.wallet_pay_next)
    Button wallet_pay_next;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_pay);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("设置金额");
        content_head_back.setOnClickListener(this);

        amount_entered.setOnClickListener(this);
        wallet_pay_next.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //下一步
            case R.id.wallet_pay_next:
                String money = amount_entered.getText().toString().trim();
                if (TextUtils.isEmpty(money)){
                    TUtils.showShort(this,"请输入收款金额");
                    return;
                }
                Intent intent = new Intent(this,ReciveMoneyActivity.class);
                intent.putExtra("money",money);
                startActivity(intent);
                finish();
                break;
            //取消
            case R.id.content_head_back:
                finish();
                break;
        }
    }
}
