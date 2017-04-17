package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.BankAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BankResultBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的银行卡
 */
public class MyBankCardActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.content_head_back)
    RelativeLayout content_head_back;
    @Bind(R.id.content_head_right)
    ImageView content_head_right;
    @Bind(R.id.content_head_title)
    TextView content_head_title;
    @Bind(R.id.bankList)
    ListView bankList;

    private Intent intent;

    private List<BankResultBean.BankBean> banks;
    private String t_status,p_status,b_status;
    private BankAdapter bankAdapter;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_card);
        ButterKnife.bind(this);
        uid = SPUtils.getInstance().getString("uid","");
        initView();


    }

    private void initView() {
        content_head_title.setText("提现卡绑定");
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            //添加银行卡
            case R.id.content_head_right:
                if(!"1".equals(t_status)){
                    intent = new Intent(this, AuthenticationActivity.class);
                    startActivity(intent);
                }else if(!"1".equals(p_status)){
                    intent = new Intent(this, SetUpPwdActivity.class);
                    startActivity(intent);
                }else if(!"1".equals(b_status)){
                    intent = new Intent(this, BindingBankCardActivity.class);
                    startActivity(intent);
                }else{
                    intent =  new Intent(this,BindingBankCardActivity.class);
                    startActivity(intent);
                }
              /*  Intent intent = new Intent(this,BindingBankCardActivity.class);
                startActivity(intent);*/
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");
        requestData();
    }

    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(MyBankCardActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);

            OkGo.post(Urls.bankInfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankResultBean>(this) {
                        @Override
                        public void onSuccess(BankResultBean bankResultBean, Call call, Response response) {
                            decodeData(bankResultBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyBankCardActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(BankResultBean bankResultBean){
        String err_code = bankResultBean.getErr_code();
        String err_msg = bankResultBean.getErr_msg();
        if("200".equals(err_code)){
            SPUtils.getInstance().putString("ptoken", bankResultBean.getData().getPtoken());
            banks = bankResultBean.getData().getList();
            if(bankAdapter == null){
                bankAdapter =  new  BankAdapter(MyBankCardActivity.this,banks);
                bankList.setAdapter(bankAdapter);
            }else{
                bankAdapter.notifyDataSetChanged();
            }

        }else if("103".equals(err_code)){
            Intent intent = new Intent(MyBankCardActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(MyBankCardActivity.this,err_msg);
        }
    }
}
