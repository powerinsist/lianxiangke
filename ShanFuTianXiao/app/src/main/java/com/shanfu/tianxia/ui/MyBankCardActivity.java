package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shanfu.tianxia.bean.UserBankCardBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.SetBnakImage;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
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

    private List<UserBankCardBean.DataBeanX.DataBean.AgreementListBean> banks;
    private String t_status,p_status,b_status;
    private BankAdapter bankAdapter;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_card);
        ButterKnife.bind(this);

        initView();


    }

    private void initView() {
        content_head_title.setText("我的银行卡");
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);

        bankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MyBankCardActivity.this,MyBankManageActivity.class);
                intent.putExtra("bankName",banks.get(position).getBank_name());
                intent.putExtra("bank_code",banks.get(position).getBank_code());
                intent.putExtra("card_no",banks.get(position).getCard_no());
                intent.putExtra("card_type",banks.get(position).getCard_type());
                intent.putExtra("no_agree",banks.get(position).getNo_agree());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            //添加银行卡
            case R.id.content_head_right:
//                if(!"1".equals(t_status)){
//                    intent = new Intent(this, AuthenticationActivity.class);
//                    startActivity(intent);
//                }else if(!"1".equals(p_status)){
//                    intent = new Intent(this, SetUpPwdActivity.class);
//                    startActivity(intent);
//                }else if(!"1".equals(b_status)){
//                    intent = new Intent(this, BindingBankCardActivity.class);
//                    startActivity(intent);
//                }else{
//                    intent =  new Intent(this,BindingBankCardActivity.class);
//                    startActivity(intent);
//                }
                intent = new Intent(this,MyBankCardPwdActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");
//        requestData();
        requestUser();
    }

    private void requestUser() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.userbankcard)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<UserBankCardBean>(this) {
                        @Override
                        public void onSuccess(UserBankCardBean userBankCardBean, Call call, Response response) {
                            decodeUser(userBankCardBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyBankCardActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeUser(UserBankCardBean userBankCardBean) {
        String err_code = userBankCardBean.getErr_code();
        String err_msg = userBankCardBean.getErr_msg();
        String ret_code = userBankCardBean.getData().getData().getRet_code();
        String ret_msg = userBankCardBean.getData().getData().getRet_msg();
        banks = new ArrayList<>();

        if ("200".equals(err_code) && "0000".equals(ret_code)){
            banks = userBankCardBean.getData().getData().getAgreement_list();
                if(bankAdapter == null){
                    bankAdapter =  new  BankAdapter(MyBankCardActivity.this,banks);
                    bankList.setAdapter(bankAdapter);
                }else{
                    bankAdapter.notifyDataSetChanged();
                }

        }else {
            TUtils.showShort(MyBankCardActivity.this,ret_msg);
        }
    }

    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(MyBankCardActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            SPUtils.getInstance().getString("user_id","");
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
//            banks = bankResultBean.getData().getList();
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
