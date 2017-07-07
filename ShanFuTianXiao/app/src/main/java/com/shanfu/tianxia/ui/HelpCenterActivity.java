package com.shanfu.tianxia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.HelpCenterAdapter;
import com.shanfu.tianxia.adapter.ShouZhiDetailAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.HelpCenterBean;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.bean.UserPayMentBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class HelpCenterActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.help_list)
    ListView help_list;
    private List<HelpCenterBean.DataBean.ListBean> list = new ArrayList<>();
    private HelpCenterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("帮助中心");
        content_head_back.setOnClickListener(this);

    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);

            OkGo.post(Urls.helplist)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<HelpCenterBean>(this) {
                        @Override
                        public void onSuccess(HelpCenterBean helpCenterBean, Call call, Response response) {
                            decodeData(helpCenterBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(HelpCenterActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(HelpCenterBean helpCenterBean) {
        String err_code = helpCenterBean.getErr_code();
        String err_msg = helpCenterBean.getErr_msg();
        if ("200".equals(err_code)){
            list = helpCenterBean.getData().getList();
            if (adapter == null){
                adapter = new HelpCenterAdapter(this, list);
                help_list.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
        }else {
            TUtils.showShort(HelpCenterActivity.this,err_msg);
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
