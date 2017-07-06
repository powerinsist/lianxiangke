package com.shanfu.tianxia.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ZonedetailsPagerAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.GoodsDetailsBean;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.fragment.ZoneShopFragment;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.PagerSlidingTab;

import okhttp3.Call;
import okhttp3.Response;

//import static com.shanfu.tianxia.recyclerviewholder.Home_Normal.maxPager;

public class ZoneGoodsDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "LOG";
    private RelativeLayout content_head_back;
    private TextView content_head_right;
    private String goods_id;
    private String shop_id;
    private String shop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_goods_details);
        goods_id = getIntent().getStringExtra("goods_id");
        shop_id = getIntent().getStringExtra("shop_id");
        shop_name = getIntent().getStringExtra("shop_name");
        Log.i(TAG, "onCreate: "+goods_id);
        Log.i(TAG, "onCreate: "+shop_id);
        initView();
    }
    private void initView(){
        content_head_back = (RelativeLayout)findViewById(R.id.content_head_back);
        content_head_right = (TextView) findViewById(R.id.content_head_right);
        TextView content_head_title = (TextView) findViewById(R.id.content_head_title);
        content_head_title.setText(shop_name);
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);

        PagerSlidingTab pagerSlidingTab = (PagerSlidingTab) findViewById(R.id.slidingtab);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ZonedetailsPagerAdapter(getSupportFragmentManager(),goods_id,shop_id));
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
