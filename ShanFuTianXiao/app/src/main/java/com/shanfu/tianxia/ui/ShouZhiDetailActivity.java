package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ShouZhiDetailAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.AdateBean;
import com.shanfu.tianxia.bean.UserPayMentBean;
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

public class ShouZhiDetailActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "LOG";
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private List<UserPayMentBean.DataBean.ListBean> list = new ArrayList<>();

    @Bind(R.id.detail_list)
    ListView detail_list;
    private ShouZhiDetailAdapter adapter;

    private LayoutInflater inflater;
    private LinearLayout footView;
    private TextView load_more_tv;

    private Handler handler = new Handler();
    private List<UserPayMentBean.DataBean.ListBean> list1 = new ArrayList<>();

    private int a = 1;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_zhi_detail);
        ButterKnife.bind(this);

        initView();
        requestData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void requestData() {
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
            params.put("p",a);
            Log.e("TAG","request-----------"+a);
            OkGo.post(Urls.userpayment_self)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<UserPayMentBean>(this) {
                        @Override
                        public void onSuccess(UserPayMentBean userPayMentBean, Call call, Response response) {
                            decodeData(userPayMentBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(ShouZhiDetailActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(UserPayMentBean userPayMentBean) {
        String err_code = userPayMentBean.getErr_code();
        String err_msg = userPayMentBean.getErr_msg();
        if ("200".equals(err_code)){
            page = userPayMentBean.getData().getPage();
            Log.e("TAG","------------"+page);
//            list = userPayMentBean.getData().getList();
            list1 = userPayMentBean.getData().getList();
            //倒序
            //Collections.reverse(list1);
            if (list1 != null){
                list.addAll(list1);
            }
            if (adapter == null){
                inflater = LayoutInflater.from(this);
                footView = (LinearLayout) inflater.inflate(R.layout.layout_item_detail_foot,null);
                adapter = new ShouZhiDetailAdapter(this,list);
                load_more_tv = (TextView) footView.findViewById(R.id.load_more_tv);
                detail_list.addFooterView(footView);
                detail_list.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
        }else {
            TUtils.showShort(ShouZhiDetailActivity.this,err_msg);
        }
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("收支明细");
        content_head_back.setOnClickListener(this);

        detail_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String counterfee = list.get(position).getCounterfee();
                String money = list.get(position).getMoney();
                String orderid = list.get(position).getOrderid();
                String payment = list.get(position).getPayment();
                String source = list.get(position).getSource();
                String time = list.get(position).getTime();
                String type = list.get(position).getType();

                Intent intent = new Intent(ShouZhiDetailActivity.this,SzDetialNextActivity.class);
                intent.putExtra("counterfee",counterfee);
                intent.putExtra("money",money);
                intent.putExtra("orderid",orderid);
                intent.putExtra("payment",payment);
                intent.putExtra("source",source);
                intent.putExtra("time",time);
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_head_back:
                finish();
                break;
//            case R.id.footerView:
//                loadMore(footView);
        }
    }

    /**
     * 点击按钮事件
     * @param view
     */
    public void loadMore(View view) {
        Log.i(TAG, "loadMore: ------11111------>");
        load_more_tv.setText("正在加载...");   //设置按钮文字loading
        Log.i(TAG, "loadMore: -------2222------------->");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ----33333---------->");
                ++a;
                Log.i(TAG, "run: ----44444---------->");
                if (a <= page){
                    Log.i(TAG, "run: ----55555---------->");
                    requestData();
                    Log.i(TAG, "run: ----66666---------->");
                    adapter.notifyDataSetChanged(); //数据集变化后,通知adapter
                    Log.i(TAG, "run: ----77777---------->");
                }else {
                    TUtils.showShort(ShouZhiDetailActivity.this,"没有更多数据了哦...");
                }
                load_more_tv.setText("加载更多");    //恢复按钮文字
            }
        }, 1000);
    }
}
