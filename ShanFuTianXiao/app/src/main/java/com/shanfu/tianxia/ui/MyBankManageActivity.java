package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.SetBnakImage;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyBankManageActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.content_head_back)
    RelativeLayout content_head_back;
    @Bind(R.id.content_head_right)
    TextView content_head_right;
    @Bind(R.id.bank_slect_iv)
    ImageView bank_slect_iv;
    @Bind(R.id.bank_select_tv)
    TextView bank_select_tv;
    @Bind(R.id.bank_num_tv)
    TextView bank_num_tv;
    @Bind(R.id.dbxe_tv)
    TextView dbxe_tv;
    @Bind(R.id.mrxe_tv)
    TextView mrxe_tv;
    private String bankName;
    private String bank_code;
    private String card_no;
    private String card_type;
    private String bankClass;
    private String no_agree;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_manage);
        ButterKnife.bind(this);

        bankName = getIntent().getStringExtra("bankName");
        bank_code = getIntent().getStringExtra("bank_code");
        card_no = getIntent().getStringExtra("card_no");
        card_type = getIntent().getStringExtra("card_type");

        initView();
    }

    private void initView() {
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);

        SetBnakImage sb = new SetBnakImage();
        sb.set(bank_code,bank_slect_iv);
        if ("2".equals(card_type)){
            bankClass = "借记卡";
        }
        if ("3".equals(card_type)){
            bankClass = "信用卡";
        }
        bank_select_tv.setText(bankName+bankClass);
        bank_num_tv.setText("**** **** **** " + card_no);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.content_head_right:
                openPopup();
                break;
        }
    }

    private void openPopup() {
        View popupView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_exitbank_popupwindow,null);
        TextView btn_exitbind = (TextView) popupView.findViewById(R.id.btn_exitbind);
        TextView btn_cancel = (TextView) popupView.findViewById(R.id.btn_cancel);
        final  PopupWindow popupWindow = new PopupWindow(popupView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
        View parent = findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
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
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btn_exitbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                no_agree = getIntent().getStringExtra("no_agree");
                intent = new Intent(MyBankManageActivity.this,BankCardUnBindActivity.class);
                intent.putExtra("no_agree",no_agree);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
