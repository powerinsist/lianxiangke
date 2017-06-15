package com.shanfu.tianxia.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.RedLxpAdapter;
import com.shanfu.tianxia.adapter.YellowLxpAdapter;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RedLxpBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YellowLxpDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.detail_list)
    ListView detail_list;

    private List<RedLxpBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_lxp_details);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("联享票");
        content_head_back.setOnClickListener(this);

    }

    private void initData(){
        list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            RedLxpBean redLxpBean = new RedLxpBean();
            redLxpBean.setTime("2017.05.01-2017.05.01" + i);
            redLxpBean.setDate("剩余1天" + i);
            list.add(redLxpBean);
        }
        YellowLxpAdapter adapter = new YellowLxpAdapter(this,list);
        detail_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
