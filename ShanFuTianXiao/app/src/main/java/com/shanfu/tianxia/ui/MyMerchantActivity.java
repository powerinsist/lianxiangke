package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.MyMerchantAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.MyMerchantBean;
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
 * 我的商户
 */
public class MyMerchantActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout my_merchant_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private List<MyMerchantBean.MyMerchantListBean> lists;
    private MyMerchantAdapter adapter;

    @Bind(R.id.my_merchant_listview)
    ListView my_merchant_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_merchant);
        ButterKnife.bind(this);
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }
    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(MyMerchantActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.recomendShop)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MyMerchantBean>(this) {
                        @Override
                        public void onSuccess(MyMerchantBean myMerchantBean, Call call, Response response) {
                            decodeResult(myMerchantBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyMerchantActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(MyMerchantBean myMerchantBean){
        String code = myMerchantBean.getErr_code();
        String msg = myMerchantBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", myMerchantBean.getData().getPtoken());
            lists = myMerchantBean.getData().getList();
            if(adapter ==null){
                adapter = new MyMerchantAdapter(MyMerchantActivity.this,lists);
                my_merchant_listview.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();


        }else if("103".equals(code)){
            Intent intent = new Intent(MyMerchantActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(MyMerchantActivity.this,msg);
        }
    }
    private void initView() {
        my_merchant_top = (RelativeLayout) findViewById(R.id.my_merchant_top);
        content_head_back = (RelativeLayout) my_merchant_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) my_merchant_top.findViewById(R.id.content_head_title);
        content_head_title.setText("我的商户");
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
