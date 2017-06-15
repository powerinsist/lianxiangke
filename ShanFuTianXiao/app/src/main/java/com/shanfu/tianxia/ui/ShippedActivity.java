package com.shanfu.tianxia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ShippedAdapter;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.ShippedBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShippedActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.detail_list)
    ListView detail_list;

    private List<ShippedBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipped);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("已发货");
        content_head_back.setOnClickListener(this);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0;i < 5;i++){
            ShippedBean shippedBean = new ShippedBean();
            shippedBean.setShopname("苹果8 plus 64G 全网通"+i);
            shippedBean.setLxpcount("3张红联享票+6张棕联享票"+i);
            shippedBean.setShopcount("共1"+i+"件商品  合计：");
            shippedBean.setStatus("卖家已发货"+i);
            list.add(shippedBean);
        }
        ShippedAdapter adapter = new ShippedAdapter(this,list);
        detail_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
