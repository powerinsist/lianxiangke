package com.shanfu.tianxia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.TraceListAdapter;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.TraceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息
 */

public class TraceActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ListView lvTrace;
    private List<TraceBean> traceBeanList = new ArrayList<>(10);
    private TraceListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        initView();
        initData();
    }

    private void initView(){
        lvTrace = (ListView) findViewById(R.id.lvTrace);
    }

    private void initData(){
        // 模拟一些假的数据
        traceBeanList.add(new TraceBean("2017-05-08 17:48:00", "北京市海淀区西三旗公司 签收 签收人：本人"));
        traceBeanList.add(new TraceBean("2017-05-08 14:13:00", "北京市海淀区西三旗公司 派件中 电话：15373550655"));
        traceBeanList.add(new TraceBean("2017-05-08 13:01:04", "卖家已发货"));
        traceBeanList.add(new TraceBean("2017-05-08 12:19:47", "您的包裹已出库"));
        traceBeanList.add(new TraceBean("2017-05-08 11:12:44", "您的订单已开始处理"));

        adapter = new TraceListAdapter(this, traceBeanList);
        lvTrace.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
