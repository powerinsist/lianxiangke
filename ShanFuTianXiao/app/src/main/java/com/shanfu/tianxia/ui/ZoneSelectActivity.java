package com.shanfu.tianxia.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.SpinnerArrayAdapter;
import com.shanfu.tianxia.adapter.ZoneSelectDataAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.CategrayBean;
import com.shanfu.tianxia.bean.InitSearchBean;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.listener.OnMenuSelectedListener;
import com.shanfu.tianxia.model.ProductData;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.DropDownMenu;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class ZoneSelectActivity extends BaseFragmentActivity implements View.OnClickListener,PullLoadMoreRecyclerView.PullLoadMoreListener {

//    private DropDownMenu mMenu;
//    final String header[] = {"分类"};
//    private String[] items = {"数码手机","珠宝艺术品","家具厨具","家具家装","酒水饮料","服装鞋包","美妆个护","休闲零食"};
//    private String shop_cat;
//    private int categray_index;
    private int page=1;
//    private ZoneSelectDataAdapter madapter;

    @Bind(R.id.lv_list)
    PullLoadMoreRecyclerView mList;
    private RecyclerView mRecyclerView;

    private List<ZoneProductBean> list;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_select);
        ButterKnife.bind(this);
        initView();
        initSpinner();
    }

    private void initView() {
        mRecyclerView = mList.getRecyclerView();
        mRecyclerView.setVerticalScrollBarEnabled(true);
        mList.setRefreshing(true);
        mList.setFooterViewText("loading");
        mList.setGridLayout(2);

        list = new ArrayList<>();
//        String url = "http://pic1.sc.chinaz.com/files/pic/pic9/201312/apic2333.jpg";
//        for (int i  = 0;i < 5;i++){
////            ZoneProductBean zoneProductBean = new ZoneProductBean();
////            zoneProductBean.setShopname("苹果8 plus 64G 全网通"+i);
////            zoneProductBean.setShopprice("6888");
////            zoneProductBean.setShopimage(url);
////            list.add(zoneProductBean);
//        }
        mList.setOnPullLoadMoreListener(this);

        ZoneSelectDataAdapter adapter = new ZoneSelectDataAdapter(ZoneSelectActivity.this,list);
        adapter.addAllData(list);
        mList.setAdapter(adapter);

        mList.setPullLoadMoreCompleted();

    }

    private void initSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.my_spinner);
        String[] categray = getResources().getStringArray(R.array.categray);
        adapter = new SpinnerArrayAdapter(this,categray);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        initView();
    }

    @Override
    public void onLoadMore() {

    }
}
