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
import com.shanfu.tianxia.adapter.XiaoFeiFanHuanAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.XiaoFeiFanHuanBean;
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

public class FanHuiMingXiActivity extends BaseFragmentActivity implements View.OnClickListener {
    private RelativeLayout xiaofei_fanhuan_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    @Bind(R.id.ciaofei_fanhuan_listview)
    ListView ciaofei_fanhuan_listview;
    private String uid;
    private List<XiaoFeiFanHuanBean.CiaoFeiBean> lists;
    private XiaoFeiFanHuanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_hui_ming_xi);
        ButterKnife.bind(this);
        uid = SPUtils.getInstance().getString("uid","");
        initView();
    }
    private void initView() {
        xiaofei_fanhuan_top = (RelativeLayout) findViewById(R.id.xiaofei_fanhuan_top);
        content_head_back = (RelativeLayout) xiaofei_fanhuan_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) xiaofei_fanhuan_top.findViewById(R.id.content_head_title);
        content_head_title.setText("我的返还");
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
            String version = AppUtils.getVerName(FanHuiMingXiActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.remissionDetail)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<XiaoFeiFanHuanBean>(this) {
                        @Override
                        public void onSuccess(XiaoFeiFanHuanBean xiaoFeiFanHuanBean, Call call, Response response) {
                            decodeResult(xiaoFeiFanHuanBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(FanHuiMingXiActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(XiaoFeiFanHuanBean xiaoFeiFanHuanBean){
        String code = xiaoFeiFanHuanBean.getErr_code();
        String msg = xiaoFeiFanHuanBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", xiaoFeiFanHuanBean.getData().getPtoken());
            lists = xiaoFeiFanHuanBean.getData().getList();
            if(adapter ==null){
                adapter = new XiaoFeiFanHuanAdapter(FanHuiMingXiActivity.this,lists);
                ciaofei_fanhuan_listview.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();


        }else if("103".equals(code)){
            Intent intent = new Intent(FanHuiMingXiActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(FanHuiMingXiActivity.this,msg);
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
