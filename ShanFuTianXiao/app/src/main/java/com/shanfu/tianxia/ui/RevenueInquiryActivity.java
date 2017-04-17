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
import com.shanfu.tianxia.adapter.RevenueInquiryAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RevenueInquiryBean;
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
 * 账户流水
 */
public class RevenueInquiryActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout revenue_inquiry_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private String uid;
    private List<RevenueInquiryBean.RevenueBean> lists;

    @Bind(R.id.my_revenue_listview)
    ListView my_revenue_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_inquiry);
        ButterKnife.bind(this);

        uid = SPUtils.getInstance().getString("uid","");
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initView() {
        revenue_inquiry_top = (RelativeLayout) findViewById(R.id.revenue_inquiry_top);
        content_head_back = (RelativeLayout) revenue_inquiry_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) revenue_inquiry_top.findViewById(R.id.content_head_title);
        content_head_title.setText("帐户流水");
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

    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(RevenueInquiryActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.accountFlow)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RevenueInquiryBean>(this) {
                        @Override
                        public void onSuccess(RevenueInquiryBean revenueInquiryBean, Call call, Response response) {
                            decodeResult(revenueInquiryBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(RevenueInquiryActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RevenueInquiryAdapter adapter;
    private void decodeResult(RevenueInquiryBean revenueInquiryBean){
        String code = revenueInquiryBean.getErr_code();
        String msg = revenueInquiryBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", revenueInquiryBean.getData().getPtoken());
            lists = revenueInquiryBean.getData().getList();
            if(adapter ==null){
                adapter = new RevenueInquiryAdapter(RevenueInquiryActivity.this,lists);
                my_revenue_listview.setAdapter(adapter);
            }
                adapter.notifyDataSetChanged();


        }else if("103".equals(code)){
            Intent intent = new Intent(RevenueInquiryActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(RevenueInquiryActivity.this,msg);
        }
    }
}
