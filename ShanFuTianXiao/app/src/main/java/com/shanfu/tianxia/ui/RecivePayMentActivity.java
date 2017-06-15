package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecivePayMentActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.wallet_pay_change)
    TextView wallet_pay_change;
    @Bind(R.id.wallet_recivemoney)
    RelativeLayout wallet_recivemoney;
    @Bind(R.id.pay_iv)
    ImageView pay_iv;
    @Bind(R.id.wallet_pay_fangshi)
    TextView wallet_pay_fangshi;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive_pay_ment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("收付款");
        content_head_back.setOnClickListener(this);

        wallet_pay_change.setOnClickListener(this);
        wallet_recivemoney.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             //更换
             case R.id.wallet_pay_change:
                 openPopup();
                 break;
             //我要收款
             case R.id.wallet_recivemoney:
                 intent = new Intent(RecivePayMentActivity.this,ReciveMoneyActivity.class);
                 startActivity(intent);
                 break;
             //返回
             case R.id.content_head_back:
                 finish();
                 break;
         }
    }

    private void openPopup(){
        View popupView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_wallet_popupwindow,null);
        LinearLayout bank_ll = (LinearLayout) popupView.findViewById(R.id.bank_ll);
        Button close_btn = (Button) popupView.findViewById(R.id.close_btn);
        final RadioButton radio_balance = (RadioButton) popupView.findViewById(R.id.radio_balance);
        RadioButton radio_bank = (RadioButton) popupView.findViewById(R.id.radio_bank);
        ImageView bank_im = (ImageView) popupView.findViewById(R.id.bank_im);
        TextView bank_name_tv = (TextView) popupView.findViewById(R.id.bank_name_tv);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        popupWindow.update();
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_balance.isChecked() == true){
                    pay_iv.setImageResource(R.mipmap.wallet_pay_balance_new);
                    wallet_pay_fangshi.setText("余额支付");
                }else {

                }
                popupWindow.dismiss();
            }
        });
        bank_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RecivePayMentActivity.this,TopSelectBankActivity.class);
                intent.putExtra("code",2);
                startActivityForResult(intent,102);
                //TODO 返回银行卡数据
            }
        });
    }
}
