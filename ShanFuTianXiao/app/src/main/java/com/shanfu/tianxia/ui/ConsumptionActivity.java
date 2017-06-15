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
import com.shanfu.tianxia.adapter.ConsumptionAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.ConsumptionBean;
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
 * 消费明细
 */
public class ConsumptionActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout consumption_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private List<ConsumptionBean.DataBean.ListBean> lists;
    private ConsumptionAdapter adapter;

    @Bind(R.id.consumption_listview)
    ListView consumption_listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);
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
            String version = AppUtils.getVerName(ConsumptionActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.buyInfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ConsumptionBean>(this) {
                        @Override
                        public void onSuccess(ConsumptionBean consumptionBean, Call call, Response response) {
                           decodeResult(consumptionBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ConsumptionActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(ConsumptionBean consumptionBean){
        String code = consumptionBean.getErr_code();
        String msg = consumptionBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", consumptionBean.getData().getPtoken());
            lists = consumptionBean.getData().getList();
            if(adapter ==null){
                
                adapter = new ConsumptionAdapter(ConsumptionActivity.this,lists);
                consumption_listview.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();


        }else if("103".equals(code)){
            Intent intent = new Intent(ConsumptionActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(ConsumptionActivity.this,msg);
        }
    }


    private void initView() {
        consumption_top = (RelativeLayout) findViewById(R.id.consumption_top);
        content_head_back = (RelativeLayout) consumption_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) consumption_top.findViewById(R.id.content_head_title);
        content_head_title.setText("消费明细");
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
