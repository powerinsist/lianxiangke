package com.shanfu.tianxia.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.MylianxpAdapter;
import com.shanfu.tianxia.adapter.ZonedetailsPagerAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.TicktListBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.PagerSlidingTab;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MyLianxpActivity extends BaseFragmentActivity implements View.OnClickListener {


    private static final String TAG = "LOG";
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    private List<TicktListBean.DataBean.List1Bean> list1;
    private List<TicktListBean.DataBean.List2Bean> list2;
    private int size1;
    private int size2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lianxp);
        ButterKnife.bind(this);

        initView();

    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("联享票");
        content_head_back.setOnClickListener(this);

        PagerSlidingTab pagerSlidingTab = (PagerSlidingTab) findViewById(R.id.slidingtab);
        pagerSlidingTab.setTextSize(35);
        pagerSlidingTab.setPadding(0,5,0,0);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(3);

//        requestData();

        MylianxpAdapter adapter = new MylianxpAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        pagerSlidingTab.setViewPager(viewPager);
        adapter.notifyDataSetChanged();

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
