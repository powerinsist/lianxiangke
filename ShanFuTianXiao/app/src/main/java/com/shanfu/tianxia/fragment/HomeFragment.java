package com.shanfu.tianxia.fragment;


import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.HomeListAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragment;
import com.shanfu.tianxia.bean.BannerImageUrlBean;
import com.shanfu.tianxia.bean.GetLLInfoBean;
import com.shanfu.tianxia.bean.HotShopBean;
import com.shanfu.tianxia.bean.LoginEvent;
import com.shanfu.tianxia.bean.LogoutEvent;
import com.shanfu.tianxia.bean.MiddleAdvertisementBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.model.HomeFloorData;
import com.shanfu.tianxia.model.ProductData;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.noscrollview.NoScrollListView;
import com.shanfu.tianxia.ui.LocationCityActivity;
import com.shanfu.tianxia.ui.LoginActivity;
import com.shanfu.tianxia.ui.SelectCityActivity;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.HomeUpdateDialogs;
import com.shanfu.tianxia.viewpagerindicator.CirclePageIndicator;
import com.zaaach.citypicker.CityPickerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import okhttp3.Call;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * HomeFragment界面的初步代码
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_PICK_CITY = 233;
    TextView location_city;
    private NetworkImageView new_user;
    public static boolean flag = true;

    /**
     * 广告自动循环切换的时间间隔
     */
    private static final int AUTO_SCROLL_INTERVAL = 3000;
    private AutoScrollViewPager viewPager;
    private CirclePageIndicator indicator;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewContainer;
    /**
     * 广告图片的url数组
     */
    private ArrayList<String> urls;
    /**
     * 商品列表部分
     */
    private NoScrollListView listView;
    private HomeListAdapter listAdapter;
    private ArrayList<HomeFloorData> floorDatas;
    /**
     * 顶部搜索
     */
    private EditText etSearch;
    private ImageButton ibSearch;
    private List<BannerImageUrlBean.Data> datas;

    private ImageButton home_login;
    private AMapLocationClient mLocationClient;
    private String city ;

    @Bind(R.id.tea)
    LinearLayout tea;
    @Bind(R.id.food)
    LinearLayout food;
    @Bind(R.id.hotel)
    LinearLayout hotel;
    @Bind(R.id.ktv)
    LinearLayout ktv;
    @Bind(R.id.shop)
    LinearLayout shop;
    @Bind(R.id.relax)
    LinearLayout relax;
    @Bind(R.id.life)
    LinearLayout life;
    @Bind(R.id.beauty)
    LinearLayout beauty;
    private Intent intent;
    //进入进获取的城市
    private String get_city;

    private String lx,ly;
    private String uid;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // requestDataBanner();
        //// TODO: 2017/3/31    layout_select_shop_item  是筛选的item 
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent event) {
//        refreshData();
        //TODO  不知道这样行不行，先试试吧
//        lx = SPUtils.getInstance().getString("lx","0.0");
//        ly = SPUtils.getInstance().getString("ly","0.0");
//        requestDataBanner();
//        requestDataMiddlead();
//        requestHotShop(get_city,lx,ly);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LoginEvent event) {
//        refreshData();
//        lx = SPUtils.getInstance().getString("lx","0.0");
//        ly = SPUtils.getInstance().getString("ly","0.0");
//        requestDataBanner();
//        requestDataMiddlead();
//        requestHotShop(get_city,lx,ly);
    }


    @Override
    public void init() {
      /*  */
        tea.setOnClickListener(this);
        food.setOnClickListener(this);
        hotel.setOnClickListener(this);
        ktv.setOnClickListener(this);
        shop.setOnClickListener(this);
        relax.setOnClickListener(this);
        life.setOnClickListener(this);
        beauty.setOnClickListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
//        refreshData();
        uid = SPUtils.getInstance().getString("uid", "");
    }

    /**
     * 收到广播的时候，调用一下这个就行了
     */
    public void refreshData(){
        Log.e("HomeFragment", "------refreshData------>>>>>>>");
//        OkGo.getInstance().cancelTag(this);
//        requestInfo();
    }

    private void requestInfo() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
//            String uid = SPUtils.getInstance().getString("uid","");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid",uid);
            OkGo.post(Urls.getllinfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GetLLInfoBean>(getActivity()) {
                        @Override
                        public void onSuccess(GetLLInfoBean getLLInfoBean, Call call, Response response) {
                            decodeInfo(getLLInfoBean,call);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeInfo(GetLLInfoBean getLLInfoBean,Call call) {
        String code = getLLInfoBean.getErr_code();
        String msg = getLLInfoBean.getErr_msg();

        if("200".equals(code)){
            String user_id = getLLInfoBean.getData().getData().getUser_id();
            String name_user = getLLInfoBean.getData().getData().getName_user();
            SPUtils.getInstance().putString("name_user", name_user);
            SPUtils.getInstance().putString("user_id",user_id);
        }
    }


    /**
     * 初始化视图
     */
    private void initView(View view) {
        get_city = SPUtils.getInstance().getString("city","北京市");
        location_city = (TextView) view.findViewById(R.id.location_city);
        location_city.setText(get_city);
        location_city.setOnClickListener(this);
        // 广告位部分
        viewPager = (AutoScrollViewPager) view.findViewById(R.id.home_viewPager);
        indicator = (CirclePageIndicator) view
                .findViewById(R.id.home_indicator);
        new_user = (NetworkImageView) view.findViewById(R.id.new_user);
        // 商品列表部分
        listView = (NoScrollListView) view.findViewById(R.id.home_lv);
        home_login = (ImageButton) view.findViewById(R.id.home_login);

        // 顶部搜索部分
        etSearch = (EditText) view.findViewById(R.id.home_top_et_search);
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), SelectCityActivity.class);
                startActivity(intent);
            }
        });
        home_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(uid)){
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("comefrom","mine");
                    startActivity(intent);
                }


            }
        });

        lx = SPUtils.getInstance().getString("lx","0.0");
        ly = SPUtils.getInstance().getString("ly","0.0");
        requestDataBanner();
        requestDataMiddlead();
        requestHotShop(get_city,lx,ly);

    }

    /**
     * 获取广告图片列表
     */
    private void getViewImage() {
        if (viewContainer == null) {
            viewContainer = new ArrayList<View>();
        }
        if (urls == null) {
            urls = new ArrayList<String>();
        }
        urls = getImageUrls();
        viewContainer.clear();
        for (String url : urls) {
            NetworkImageView iv = new NetworkImageView(getActivity());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            NetworkManager.getInstance().setImageUrl(iv, url);
            viewContainer.add(iv);
        }
    }

    /**
     * 获取广告位的图片资源url数组
     * 每次从服务器获得url数组先做永久性存储，获取时先从本地显示之前的缓存，等获取成功之后再调用notifyDataSetChanged()
     *
     * @return url数组
     */
    private ArrayList<String> getImageUrls() {
        ArrayList<String> list = new ArrayList<String>();
      /*for(int x=0;x<datas.size();x++){
            list.add(datas.get(x).getPic());
        }*/
        // //////////////////////////////////////
        // ////////////////假数据/////////////////
        list.add("http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg");
        // list.add("http://c.hiphotos.baidu.com/image/pic/item/37d12f2eb9389b503564d2638635e5dde7116e63.jpg");
        // list.add("http://g.hiphotos.baidu.com/image/pic/item/cf1b9d16fdfaaf517578b38e8f5494eef01f7a63.jpg");
        //list.add("http://f.hiphotos.baidu.com/image/pic/item/77094b36acaf2eddce917bd88e1001e93901939a.jpg");
        //list.add("http://g.hiphotos.baidu.com/image/pic/item/f703738da97739124dd7b750fb198618367ae20a.jpg");
        // //////////////////////////////////////
        return list;
    }

    /**
     * 获取楼层列表的数据
     */
    private ArrayList<HomeFloorData> getListData() {
        ArrayList<HomeFloorData> list = new ArrayList<HomeFloorData>();
       /* String imgUrl = "http://b.hiphotos.baidu.com/image/pic/item/14ce36d3d539b6006bae3d86ea50352ac65cb79a.jpg";
        ArrayList<ProductData> products;
        HomeFloorData floor;
        ProductData product;
        for (int j = 0; j < 2; j++) {
            products = new ArrayList<ProductData>();
            for (int i = 0; i < 10; i++) {
               product = new ProductData();
                product.setId(i);
                product.setImgUrl(imgUrl);
                product.setInfo("上岛咖啡上岛咖啡上岛咖啡上岛咖啡上岛咖啡");
                product.setPrice(i * 2);
                products.add(product);
            }
            floor = new HomeFloorData();
            floor.setFloor(j + "F");
            floor.setProducts(products);
            list.add(floor);
        }*/
        // ///////////////////////////////////////
        return list;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_city:
               startActivityForResult(new Intent(getActivity(), LocationCityActivity.class),
                        REQUEST_CODE_PICK_CITY);
               /* startActivityForResult(new Intent(getActivity(), PayWebViewActivity.class),
                        REQUEST_CODE_PICK_CITY);
*/
               /* startActivityForResult(new Intent(getActivity(), EvaluationMerchantActivity.class),
                        REQUEST_CODE_PICK_CITY);*/
                /*Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);*/
                break;
            case R.id.tea:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","1");
                startActivity(intent);
                break;
            case R.id.food:
               intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid", "2");
                startActivity(intent);

                break;
            case R.id.hotel:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","4");
                startActivity(intent);
                break;
            case R.id.ktv:
                //// TODO: 2017/3/31 这个没有定义
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","9");
                startActivity(intent);
                break;
            case R.id.shop:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","6");
                startActivity(intent);
                break;
            case R.id.relax:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","7");
                startActivity(intent);
                break;
            case R.id.life:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","8");
                startActivity(intent);
                break;
            case R.id.beauty:
                intent = new Intent(getActivity(), SelectCityActivity.class);
                intent.putExtra("tid","10");
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == -1) {
            if (data != null) {
                city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                location_city.setText(city);
                SPUtils.getInstance().putString("city", city);
                if(city.equals(get_city)){
                    requestHotShop(city,SPUtils.getInstance().getString("lx","0.0"), SPUtils.getInstance().getString("ly","0.0"));
                }else{
                    requestHotShop(city,"0.0", "0.0");
                }

            }
        }
    }

    /**
     * 请求首页的bannar
     */
    private void requestDataBanner() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            version = AppUtils.getVerName(getActivity());


            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            OkGo.post(Urls.home_banner)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BannerImageUrlBean>(getActivity()) {

                        @Override
                        public void onSuccess(BannerImageUrlBean bannerImageUrlBean, Call call, Response response) {
                            decodeBanner(bannerImageUrlBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void decodeBanner(BannerImageUrlBean bannerImageUrlBean) {
        String code = bannerImageUrlBean.getErr_code();
        String msg = bannerImageUrlBean.getErr_msg();
        if ("200".equals(code)) {
            datas = bannerImageUrlBean.getData();
            if (viewContainer == null) {
                viewContainer = new ArrayList<View>();
            }
            if (urls == null) {
                urls = new ArrayList<String>();
            }
            urls.clear();
            for (int x = 0; x < datas.size(); x++) {
                urls.add(datas.get(x).getPic());
            }
            viewContainer.clear();
            for (String url : urls) {
                NetworkImageView iv = new NetworkImageView(getActivity());
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
        }
    }


    /**
     * 首页中部广告位
     */
    private void requestDataMiddlead() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            OkGo.post(Urls.middlead)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MiddleAdvertisementBean>(getActivity()) {
                        @Override
                        public void onSuccess(MiddleAdvertisementBean middleAdvertisementBean, Call call, Response response) {
                            decodeMiddle(middleAdvertisementBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocationManager locationManager;
    private String locationProvider = null;
    private double latitude=0.0;
    private double longitude =0.0;


    private String code,msg,updaversion,updataurl,version,apkUrl;
    private HomeUpdateDialogs homeUpdateDialogs;
    private void decodeMiddle(MiddleAdvertisementBean middleAdvertisementBean){
        code = middleAdvertisementBean.getErr_code();
        msg = middleAdvertisementBean.getErr_msg();
        updaversion = middleAdvertisementBean.getData().getVersion();
        String url =  middleAdvertisementBean.getData().getImg();
        version = AppUtils.getVerName(getActivity());
        new_user.setScaleType(ImageView.ScaleType.FIT_XY);
        apkUrl = middleAdvertisementBean.getData().getUrl();
        NetworkManager.getInstance().setImageUrl(new_user, url);
        if(!version.equals(updaversion)){
            homeUpdateDialogs = new HomeUpdateDialogs(getActivity(),apkUrl);
            homeUpdateDialogs.checkUpdateInfo();
        }
    }

    /**
     *首页下部商品热卖
     */
    private void requestHotShop(String city,String slx,String sly){

        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("lx", slx);
            params.put("ly", sly);
            params.put("page", "1");
            params.put("pageSize", "50");
            params.put("version", version);
            params.put("area_name", city);

            OkGo.post(Urls.hotshops)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<HotShopBean>(getActivity()) {
                        @Override
                        public void onSuccess(HotShopBean hotShopBean, Call call, Response response) {
                            decodeHotShop(hotShopBean,call);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    HomeFloorData floor;

    private void decodeHotShop(HotShopBean hotShopBean,Call call){

        String errorCode = hotShopBean.getErr_code();
        String msg = hotShopBean.getErr_msg();
        if("200".equals(errorCode)){
            List<ProductData> list = hotShopBean.getData();

            floorDatas = new ArrayList<HomeFloorData>();
            floor = new HomeFloorData();
            floor.setProducts(list);
            floorDatas.add(floor);
           /* if(listAdapter ==null){
                listAdapter = new HomeListAdapter(getActivity(), floorDatas,
                        R.layout.item_home_lv);
                listView.setAdapter(listAdapter);
            }else{
                LogUtil.log(LogType.ERROR,getClass(),"listAdapter---------");
                listAdapter.updateState(floorDatas);
            }*/
            listAdapter = new HomeListAdapter(getActivity(), floorDatas,
                    R.layout.item_home_lv);
            listView.setAdapter(listAdapter);
            listView.setDivider(null);
            // 手动给ListView内容设置了高度，导致页面进入不在顶端，通过给顶端控件设置焦点的方法使view显示在顶端
            viewPager.setFocusable(true);
            viewPager.setFocusableInTouchMode(true);
            viewPager.requestFocus();
//            call.cancel();
        }else{
            TUtils.showShort(getActivity(),msg);
            floorDatas = new ArrayList<HomeFloorData>();
            listAdapter = new HomeListAdapter(getActivity(), floorDatas,
                    R.layout.item_home_lv);
            listView.setAdapter(listAdapter);
            listView.setDivider(null);
        }
    }
}
