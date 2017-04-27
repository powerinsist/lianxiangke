package com.shanfu.tianxia.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.MyInComeAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.MyInComeBean;
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

public class MyInComeActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.my_in_come_top)
    RelativeLayout my_in_come_top;
    @Bind(R.id.my_in_come_listview)
    ListView my_in_Come_listviwe;
    private String uid;
    private List<MyInComeBean.InComeBean> lists;
    private MyInComeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_in_come);
        ButterKnife.bind(this);
        uid = SPUtils.getInstance().getString("uid","");
        initView();
    }
    public void initView(){
        content_head_back = (RelativeLayout) my_in_come_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) my_in_come_top.findViewById(R.id.content_head_title);
        content_head_title.setText("我的提现");
        content_head_back.setOnClickListener(this);
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
            String version = AppUtils.getVerName(MyInComeActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");

            final HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.my_income)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MyInComeBean>(this) {
                        @Override
                        public void onSuccess(MyInComeBean myInComeBean, Call call, Response response) {
                            decodeResult(myInComeBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyInComeActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(MyInComeBean myInComeBean){
        String code = myInComeBean.getErr_code();
        String msg = myInComeBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", myInComeBean.getData().getPtonken());
            lists = myInComeBean.getData().getList();
            if(adapter ==null){
                adapter = new MyInComeAdapter(MyInComeActivity.this,lists);
                my_in_Come_listviwe.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        }
        else if("103".equals(code)){
            Intent intent = new Intent(MyInComeActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            TUtils.showShort(MyInComeActivity.this,msg);
        }
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
