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
import com.shanfu.tianxia.adapter.QueryIncomeAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.QueryIncomeBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 收益查询
 */
public class QueryIncomeActivity extends BaseFragmentActivity implements View.OnClickListener {

    private String uid;
    private RelativeLayout query_income_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private List<QueryIncomeBean.QueryBean> lists;
    @Bind(R.id.query_icome_listview)
    ListView query_icome_listview;
    private QueryIncomeAdapter adapter;
    @Bind(R.id.query_income_leiji)
    TextView query_income_leiji;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_income);
        ButterKnife.bind(this);
        uid = SPUtils.getInstance().getString("uid", "");
        initView();
        requestData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initView() {
        query_income_top = (RelativeLayout) findViewById(R.id.query_income_top);
        content_head_back = (RelativeLayout) query_income_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) query_income_top.findViewById(R.id.content_head_title);
        content_head_title.setText("市场收入");
        content_head_back.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_head_back:
                finish();
                break;
        }
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
//            String version = AppUtils.getVerName(QueryIncomeActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
//            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.queryIncome)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<QueryIncomeBean>(this) {
                        @Override
                        public void onSuccess(QueryIncomeBean queryIncomeBean, Call call, Response response) {
                            decodeResult(queryIncomeBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(QueryIncomeActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(QueryIncomeBean queryIncomeBean) {
        String code = queryIncomeBean.getErr_code();
        String msg = queryIncomeBean.getErr_msg();

        if ("200".equals(code)) {
            SPUtils.getInstance().putString("ptoken", queryIncomeBean.getData().getPtoken());
            lists = queryIncomeBean.getData().getList();
            String accumulated_income = queryIncomeBean.getData().getAccumulated_income();
            query_income_leiji.setText("累计收入 ： " + accumulated_income + " 元");
            Collections.reverse(lists);
            if (adapter == null) {
                adapter = new QueryIncomeAdapter(QueryIncomeActivity.this, lists);
                query_icome_listview.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();

        } else if ("103".equals(code)) {
            Intent intent = new Intent(QueryIncomeActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            TUtils.showShort(QueryIncomeActivity.this, msg);
        }
    }
}
