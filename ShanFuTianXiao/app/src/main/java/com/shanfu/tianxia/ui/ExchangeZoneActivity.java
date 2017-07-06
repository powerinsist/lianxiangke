package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.GridItemDecoration;
import com.shanfu.tianxia.adapter.ZoneGridAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.ZoneImageUrlBean;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.noscrollview.FullyGridLayoutManager;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.PwdEditText;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.viewpagerindicator.CirclePageIndicator;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import okhttp3.Call;
import okhttp3.Response;

public class ExchangeZoneActivity extends BaseFragmentActivity implements View.OnClickListener,PullLoadMoreRecyclerView.PullLoadMoreListener {

    /**
     * 广告自动循环切换的时间间隔
     */
    private static final int AUTO_SCROLL_INTERVAL = 3000;
    private AutoScrollViewPager viewPager;
    private CirclePageIndicator indicator;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewContainer;

    private List<ZoneImageUrlBean> datas;
    /**
     * 广告图片的url数组
     */
    private ArrayList<String> urls;
    /**
     * 商品列表部分
     */
    @Bind(R.id.item_zone_grid)
    PullLoadMoreRecyclerView mList;
    private ZoneGridAdapter listAdapter;
    private List<ZoneProductBean.DataBean.ListBean> listBeans;
    private RecyclerView mRecyclerView;
    private int page=1;
    /**
     * 标题栏
     */
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    /*//数码手机
    @Bind(R.id.zone_phone)
    LinearLayout zone_phone;
    //珠宝艺术品
    @Bind(R.id.zone_art)
    LinearLayout zone_art;
    //家具厨具
    @Bind(R.id.zone_fitment)
    LinearLayout zone_fitment;
    //家具家装
    @Bind(R.id.zone_home)
    LinearLayout zone_home;
    //酒水饮料
    @Bind(R.id.zone_drink)
    LinearLayout zone_drink;
    //服装鞋包
    @Bind(R.id.zone_clothes)
    LinearLayout zone_clothes;
    //美妆个护
    @Bind(R.id.zone_face)
    LinearLayout zone_face;
    //休闲零食
    @Bind(R.id.zone_rest)*/
//    LinearLayout zone_rest;
    private Intent intent;
    private TextView content_head_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_zone);
        ButterKnife.bind(this);
        init();
//        initData();
        initList();
//        requestDataBanner();
        requestHotShop();
    }

    /*初始化视图*/
    private void init(){

        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("兑换专区");
        content_head_right = (TextView) setting_top.findViewById(R.id.content_head_right);
        content_head_right.setText("更多");
        content_head_right.setOnClickListener(this);
        content_head_back.setOnClickListener(this);

        /*zone_phone.setOnClickListener(this);
        zone_art.setOnClickListener(this);
        zone_fitment.setOnClickListener(this);
        zone_home.setOnClickListener(this);
        zone_drink.setOnClickListener(this);
        zone_clothes.setOnClickListener(this);
        zone_face.setOnClickListener(this);*/
//        zone_rest.setOnClickListener(this);

       /* viewPager = (AutoScrollViewPager) findViewById(R.id.zone_viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.zone_indicator);*/


    }
    /**
     * 请求首页的bannar
     */
    private void requestDataBanner() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            OkGo.post(Urls.zone_banner)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ZoneImageUrlBean>(this) {

                        @Override
                        public void onSuccess(ZoneImageUrlBean zoneImageUrlBean, Call call, Response response) {
                            decodeBanner(zoneImageUrlBean);
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

    private void decodeBanner(ZoneImageUrlBean zoneImageUrlBean) {
        String code = zoneImageUrlBean.getErr_code();
        String msg = zoneImageUrlBean.getErr_msg();
        if ("200".equals(code)) {
            ZoneImageUrlBean.ImageBean data = zoneImageUrlBean.getData();

            if (viewContainer == null) {
                viewContainer = new ArrayList<View>();
            }
            if (urls == null) {
                urls = new ArrayList<String>();
            }
            urls.clear();

            ZoneImageUrlBean.ImageUrls list = data.getList();
            String images0 = list.getImages0();
            String images1 = list.getImages1();
            String images2 = list.getImages2();

            urls.add(images0);
            urls.add(images1);
            urls.add(images2);

            viewContainer.clear();
            for (String url : urls) {
                NetworkImageView iv = new NetworkImageView(getApplicationContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                NetworkManager.getInstance().setImageUrl(iv, url);
                viewContainer.add(iv);
            }

            if (pagerAdapter == null) {
                pagerAdapter = new PagerAdapter() {

                    @Override
                    public void destroyItem(ViewGroup container, int position,
                                            Object object) {
                        ((AutoScrollViewPager) container).removeView(viewContainer
                                .get(position));
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        ((AutoScrollViewPager) container).addView(viewContainer
                                .get(position));
                        return viewContainer.get(position);
                    }

                    @Override
                    public boolean isViewFromObject(View arg0, Object arg1) {
                        return arg0 == arg1;
                    }

                    @Override
                    public int getCount() {
                        return viewContainer.size();
                    }
                };
            }

            viewPager.setAdapter(pagerAdapter);

            indicator.setViewPager(viewPager);
            viewPager.setInterval(AUTO_SCROLL_INTERVAL);
            viewPager.startAutoScroll();
            requestHotShop();
        }
    }
    /**
     * 通知ViewPager广告位数据源改变
     */
    public void notifyDataSetChanged() {
        if (pagerAdapter != null)
            pagerAdapter.notifyDataSetChanged();
        if (indicator != null)
            indicator.notifyDataSetChanged();
    }

    private void initList(){
        //获取mRecyclerView对象
        mRecyclerView = mList.getRecyclerView();
//        mRecyclerView.setNestedScrollingEnabled(false);
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new GridItemDecoration(this, true));

        //设置是否可以下拉刷新
        mList.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        mList.setPushRefreshEnable(true);
        //显示下拉刷新
        mList.setRefreshing(true);
        //设置上拉刷新文字
        mList.setFooterViewText("加载中...");
        /*FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(ExchangeZoneActivity.this,2);
        fullyGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(fullyGridLayoutManager);*/
        mList.setGridLayout(2);
        mList.setOnPullLoadMoreListener(this);
        listAdapter = new ZoneGridAdapter(ExchangeZoneActivity.this);
        mList.setAdapter(listAdapter);

        View header = LayoutInflater.from(this).inflate(R.layout.nice_zone_headview, null, false);
        viewPager = (AutoScrollViewPager) header.findViewById(R.id.zone_viewPager);
        indicator = (CirclePageIndicator) header.findViewById(R.id.zone_indicator);
        listAdapter.setHeaderView(header);
    }

    /**
     * 首页商品列表
     */
    private void requestHotShop(){
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("p", page);
//            params.put("pageSize", "50");

            OkGo.post(Urls.goodslist)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ZoneProductBean>(this) {
                        @Override
                        public void onSuccess(ZoneProductBean zoneProductBean, Call call, Response response) {
                            decodeHotShop(zoneProductBean);
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

    private void decodeHotShop(ZoneProductBean zoneProductBean){
        String code = zoneProductBean.getErr_code();
        String msg = zoneProductBean.getErr_msg();
        if ("200".equals(code)){
            listBeans = zoneProductBean.getData().getList();
            listAdapter.addAll(listBeans);
            mList.setPullLoadMoreCompleted();
        }else {
            mList.setPullLoadMoreCompleted();
            TUtils.showShort(ExchangeZoneActivity.this,msg);
        }
    }

    private void initData(){
//        list = new ArrayList<>();
//        String url = "http://pic1.sc.chinaz.com/files/pic/pic9/201312/apic2333.jpg";
//        for (int i=0;i<20;i++){
//            ZoneProductBean zoneProductBean = new ZoneProductBean();
//            zoneProductBean.setLxpchange("2张红色联想票+3张绿色联想票");
//            zoneProductBean.setShopname("OPPOA57   3GB+32GB内存版   玫瑰金   全网通   4G   双卡双待");
//            zoneProductBean.setShopimage(url);
//            zoneProductBean.setShopprice("1599");
//            list.add(zoneProductBean);
//        }
//        ZoneGridAdapter zoneGridAdapter = new ZoneGridAdapter(this,list);
//        gridView.setAdapter(zoneGridAdapter);
//        // 手动给ListView内容设置了高度，导致页面进入不在顶端，通过给顶端控件设置焦点的方法使view显示在顶端
//        viewPager.setFocusable(true);
//        viewPager.setFocusableInTouchMode(true);
//        viewPager.requestFocus();
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                intent = new Intent(ExchangeZoneActivity.this,ZoneGoodsDetailsActivity.class);
//                intent.putExtra("id",position);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.zone_phone:
                intent = new Intent(ExchangeZoneActivity.this,ZoneSelectActivity.class);
//                intent = new Intent(ExchangeZoneActivity.this, SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.zone_art:
                break;
            case R.id.zone_fitment:
                break;
            case R.id.zone_home:
                break;
            case R.id.zone_drink:
                break;
            case R.id.zone_clothes:
                break;
            case R.id.zone_face:
                break;
            case R.id.zone_rest:
                break;*/
            case R.id.content_head_back:
                finish();
                break;
            case R.id.content_head_right:
                openPopup();
                break;
        }
    }

    private void openPopup() {
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_zone_select_popup,null);
        final PopupWindow popupWindow = new PopupWindow(popupPayView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout mine_alredycargo_ll = (LinearLayout) popupPayView.findViewById(R.id.mine_alredycargo_ll);
        LinearLayout mine_waitcargo_ll = (LinearLayout) popupPayView.findViewById(R.id.mine_waitcargo_ll);
        LinearLayout zone_dingdan_mingxi_ll = (LinearLayout) popupPayView.findViewById(R.id.zone_dingdan_mingxi_ll);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.content_head_right);
        popupWindow.showAtLocation(parent, Gravity.RIGHT|Gravity.TOP,50,140);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    params.alpha = 1.0f;
                    getWindow().setAttributes(params);
                }
            });
        mine_alredycargo_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                intent = new Intent(ExchangeZoneActivity.this, ShippedActivity.class);
                startActivity(intent);
            }
        });
        mine_waitcargo_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                intent = new Intent(ExchangeZoneActivity.this, DropShipingActivity.class);
                startActivity(intent);
            }
        });
        zone_dingdan_mingxi_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                intent = new Intent(ExchangeZoneActivity.this, CheckForTheDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        page = page + 1;
//        setRefresh();
//        initList();
        requestHotShop();
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        requestHotShop();
    }

    private void setRefresh() {
        listAdapter.clearData();
        page = 1;
    }

    public void clearData() {
        listAdapter.clearData();
        listAdapter.notifyDataSetChanged();
    }
}
