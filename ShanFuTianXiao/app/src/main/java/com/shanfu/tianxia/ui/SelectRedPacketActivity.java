package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.SelectLxpRedPacketAdapter;
import com.shanfu.tianxia.adapter.SelectRedPacketAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.AlreadyReceiverBean;
import com.shanfu.tianxia.bean.RedPacketBean;
import com.shanfu.tianxia.bean.TabHostBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SelectRedPacketActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.record_list)
    ListView record_list;
    @Bind(R.id.count_money_tv)
    TextView count_money_tv;

    private List<AlreadyReceiverBean.DataBeanX.DataBean> list;
    private SelectLxpRedPacketAdapter adapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_red_packet);
        ButterKnife.bind(this);
        initView();
        requestData();
    }

    private void requestData() {
        String time = DateUtils.getLinuxTime();
        String token = MD5Utils.MD5(Constants.appKey + time);
        String uid = SPUtils.getInstance().getString("uid", "");

        HttpParams params = new HttpParams();
        params.put("time", time);
        params.put("token", token);
        params.put("uid", uid);

        OkGo.post(Urls.already_received)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<AlreadyReceiverBean>(this) {
                    @Override
                    public void onSuccess(AlreadyReceiverBean alreadyReceiverBean, Call call, Response response) {
                        decodeRedPacket(alreadyReceiverBean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        TUtils.showShort(SelectRedPacketActivity.this,"数据获取失败，请检查网络后重试");
                    }
                });
    }

    private void decodeRedPacket(AlreadyReceiverBean alreadyReceiverBean) {
        String err_code = alreadyReceiverBean.getErr_code();
        String err_msg = alreadyReceiverBean.getErr_msg();
        list = new ArrayList<>();
        if ("200".equals(err_code)){
            String totalmoney = alreadyReceiverBean.getData().getTotalmoney();
            count_money_tv.setText(totalmoney);
            list = alreadyReceiverBean.getData().getData();
            if (adapter == null) {
                Collections.reverse(list);
                adapter = new SelectLxpRedPacketAdapter(this,list);
                record_list.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

        }else {
            TUtils.showShort(this,err_msg);
        }
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("红包记录");
        content_head_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                intent = new Intent(SelectRedPacketActivity.this,MyLianxpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
