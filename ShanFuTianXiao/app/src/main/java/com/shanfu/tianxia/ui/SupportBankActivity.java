package com.shanfu.tianxia.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.SupportBankAdapter;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.SupportBankBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupportBankActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.bank_list)
    ListView bank_list;
    private List<SupportBankBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_bank);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("支持银行卡");
        content_head_back.setOnClickListener(this);

        //初始化数据
        list = new ArrayList<>();
        //22个
        int[] drawables = {R.mipmap.wallet_bankcard_ccb,R.mipmap.wallet_bankcard_cmbc,R.mipmap.wallet_bankcard_agricultural,R.mipmap.wallet_bankcard_communications,
                R.mipmap.wallet_bankcard_ceb,R.mipmap.wallet_bankcard_zhongxin, R.mipmap.wallet_bankcard_hxb,R.mipmap.wallet_bankcard_pufa,R.mipmap.wallet_bankcard_pingan,
                R.mipmap.bgbank,R.mipmap.bohaib,R.mipmap.gfbank,R.mipmap.gsbank,R.mipmap.msbank,R.mipmap.yzbank,R.mipmap.zgbank,
                R.mipmap.hbbank,R.mipmap.hsbank,R.mipmap.hzbank,R.mipmap.nsbank,R.mipmap.shbank,R.mipmap.xybank};
        String[] strings = {"中国建设银行","中国招商银行","中国农业银行","中国交通银行","中国光大银行","中信银行","华夏银行","浦发银行","平安银行"
                ,"北京银行","渤海银行","广发银行","中国工商银行","中国民生银行","中国邮政储蓄","中国银行","河北银行","徽商银行","杭州银行","北京农商银行",
                "上海银行","兴业银行"};

        for (int i = 0 ; i < drawables.length; i ++){
            SupportBankBean supportBankBean = new SupportBankBean();
            supportBankBean.setBankName(strings[i]);
            supportBankBean.setBankImage(drawables[i]);
            list.add(supportBankBean);
        }
        SupportBankAdapter adapter = new SupportBankAdapter(this,list);
        bank_list.setAdapter(adapter);

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
