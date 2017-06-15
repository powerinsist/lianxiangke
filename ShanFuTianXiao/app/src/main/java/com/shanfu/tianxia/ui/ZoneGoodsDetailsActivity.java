package com.shanfu.tianxia.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ZonedetailsPagerAdapter;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.view.PagerSlidingTab;

public class ZoneGoodsDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout content_head_back;
    private TextView content_head_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_goods_details);
        initView();
    }
    private void initView(){
        content_head_back = (RelativeLayout)findViewById(R.id.content_head_back);
        content_head_right = (TextView) findViewById(R.id.content_head_right);
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);

        PagerSlidingTab pagerSlidingTab = (PagerSlidingTab) findViewById(R.id.slidingtab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ZonedetailsPagerAdapter(getSupportFragmentManager()));
        pagerSlidingTab.setViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.content_head_right:
                break;
        }
    }
}
