package com.shanfu.tianxia.ui;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.SpinnerArrayAdapter;
import com.shanfu.tianxia.adapter.ZoneSelectDataAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.ZoneSelectBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import static com.shanfu.tianxia.adapter.ZoneSelectDataAdapter.totalnumber;

public class ZoneSelectActivity extends BaseFragmentActivity implements View.OnClickListener,PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static final String TAG = "LOG";
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

    private List<ZoneSelectBean.DataBean.ListBean> list;
    private ArrayAdapter<String> adapter;
    @Bind(R.id.content_head_back)
    RelativeLayout content_head_back;
    private String shop_id;
    private String shop_name;
    private ZoneSelectDataAdapter adapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_select);
        ButterKnife.bind(this);

        shop_id = getIntent().getStringExtra("shop_id");
        Log.i(TAG, "onCreate: "+ shop_id);
        shop_name = getIntent().getStringExtra("shop_name");
        Log.i(TAG, "onCreate: "+ shop_name);

        initView();
        initSpinner();
        requestShop();
    }

    private void requestShop() {
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            params.put("p",page);
            params.put("id",shop_id);


            OkGo.post(Urls.classified_list)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ZoneSelectBean>(this) {
                        @Override
                        public void onSuccess(ZoneSelectBean zoneSelectBean, Call call, Response response) {
                            decodeShop(zoneSelectBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getApplicationContext(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeShop(ZoneSelectBean zoneSelectBean) {
        String code = zoneSelectBean.getErr_code();
        String msg = zoneSelectBean.getErr_msg();
        if (code.equals("200")){
//            TUtils.showShort(ZoneSelectActivity.this,msg);
            list = zoneSelectBean.getData().getList();
            if (list == null){
                mList.setPullLoadMoreCompleted();
                TUtils.showShort(this,"没有更多数据了...");
            }else {
                adapter1.addAllData(list);
                mList.setPullLoadMoreCompleted();
            }
        }else {
            mList.setPullLoadMoreCompleted();
            TUtils.showShort(ZoneSelectActivity.this,msg);
        }
    }

    private void initView() {
        content_head_back.setOnClickListener(this);
        mRecyclerView = mList.getRecyclerView();
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mList.setRefreshing(true);
        mList.setFooterViewText("正在加载...");
        mList.setGridLayout(2);

        list = new ArrayList<>();
        /*String url = "http://pic1.sc.chinaz.com/files/pic/pic9/201312/apic2333.jpg";
        for (int i  = 0;i < 5;i++){
            ZoneProductBean zoneProductBean = new ZoneProductBean();
            zoneProductBean.setShopname("苹果8 plus 64G 全网通"+i);
            zoneProductBean.setShopprice("6888");
            zoneProductBean.setShopimage(url);
            list.add(zoneProductBean);
        }*/
        mList.setOnPullLoadMoreListener(this);

        adapter1 = new ZoneSelectDataAdapter(ZoneSelectActivity.this,list);
        adapter1.addAllData(list);
        mList.setAdapter(adapter1);

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
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
//        initView();
        page = page +1;
        Log.i(TAG, "onRefresh: "+page);
        Log.i(TAG, "onRefresh: "+totalnumber);
        if (page <= totalnumber/4 ){
            requestShop();
            clearData();
            adapter1.addAllData(list);
        }else {
            mList.setPullLoadMoreCompleted();
            TUtils.showShort(this,"没有更多数据了...");
        }

    }

    @Override
    public void onLoadMore() {
        if (list == null){
            mList.setPullLoadMoreCompleted();
            TUtils.showShort(this,"没有更多数据了...");
        }else {
            page = page + 1;
            requestShop();
        }
    }

    private void setRefresh() {
        adapter1.clearData();
        page = 1;
    }

    public void clearData() {
        adapter1.clearData();
        adapter1.notifyDataSetChanged();
    }
}
