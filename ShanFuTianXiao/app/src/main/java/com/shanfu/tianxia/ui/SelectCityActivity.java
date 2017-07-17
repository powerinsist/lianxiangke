package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.SelectCityAdapter;
import com.shanfu.tianxia.adapter.SelectCityDataAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.InitSearchBean;
import com.shanfu.tianxia.bean.SelectCityBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.listener.OnMenuSelectedListener;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.LogType;
import com.shanfu.tianxia.utils.LogUtil;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
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

/**
 * 选择城市
 */
public class SelectCityActivity extends BaseFragmentActivity implements View.OnClickListener, PullLoadMoreRecyclerView.PullLoadMoreListener {

    private DropDownMenu mMenu;


    private int city_index;//分类
    private int sex_index;//地区
    private int age_index;//排序
    private List<String> data;
   // final String[] arr1=new String[]{"全部城市","北京","上海","广州","深圳"};
   // final String[] arr2=new String[]{"性别","男","女"};
   // final String[] arr3=new String[]{"全部年龄","10","20","30","40","50","60","70"};
    private ArrayList<InitSearchBean.InitBean> areas=null;
    private ArrayList<InitSearchBean.InitBean> categorys=null;
    private ArrayList<InitSearchBean.InitBean> paixus=null;

    final String[] strings=new String[]{"分类","地区","排序"};

    @Bind(R.id.select_city_back)
    ImageButton select_city_back;
    @Bind(R.id.select_city_ed_search)
    EditText select_city_ed_search;
    @Bind(R.id.select_city_search)
    ImageButton select_city_search;
    @Bind(R.id.lv_list)
    PullLoadMoreRecyclerView mList;
    private String tid;
    private Intent intent;

    private int page=1;
    private String keyword,area_name,shop_cat,paixue;

    private List<SelectCityBean.SelectCityData>  citydatas;
    private RecyclerView mRecyclerView;

    private String lx,ly,city;

    private SelectCityDataAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        initList();
        shop_cat = getIntent().getStringExtra("tid");
        lx = SPUtils.getInstance().getString("lx", "0.0");
        ly =SPUtils.getInstance().getString("ly","0.0");
        city = SPUtils.getInstance().getString("city","北京");
        initView();
       // requestSearch();
        requestData();

        //requestSearch("ktv","37","37","1","1","1","39.995576","116.481288");
    }
    private void initList(){
        //获取mRecyclerView对象
        mRecyclerView = mList.getRecyclerView();
        //代码设置scrollbar无效？未解决！
        mRecyclerView.setVerticalScrollBarEnabled(true);

        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        mList.setRefreshing(true);
        //设置上拉刷新文字
        mList.setFooterViewText("loading");


        //设置上拉刷新文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        mList.setLinearLayout();

        mList.setOnPullLoadMoreListener(this);
        madapter = new SelectCityDataAdapter(SelectCityActivity.this);
        mList.setAdapter(madapter);
      /*  madapter.setOnItemClickListener(new SelectCityDataAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtil.log(LogType.ERROR,getClass(),position+"----------------postion");
                LogUtil.log(LogType.ERROR,getClass(),citydatas.size()+"----------------size");
                intent = new Intent(SelectCityActivity.this,CommodityDetailsActivity.class);
                intent.putExtra("shopid",citydatas.get(position).getId());
                intent.putExtra("shopname",citydatas.get(position).getShopname());
                intent.putExtra("grade",citydatas.get(position).getGrade());
                intent.putExtra("img",citydatas.get(position).getImg());
                startActivity(intent);
            }
        });*/

    }

    private void initView() {
        select_city_back.setOnClickListener(this);
        select_city_search.setOnClickListener(this);
        /**
         * mMenu.setMenuCount(3);//Menu的个数
         mMenu.setShowCount(6);//Menu展开list数量太多是只显示的个数
         mMenu.setShowCheck(true);//是否显示展开list的选中项
         mMenu.setMenuTitleTextSize(16);//Menu的文字大小
         mMenu.setMenuTitleTextColor(Color.WHITE);//Menu的文字颜色
         mMenu.setMenuListTextSize(16);//Menu展开list的文字大小
         mMenu.setMenuListTextColor(Color.BLACK);//Menu展开list的文字颜色
         mMenu.setMenuBackColor(Color.GRAY);//Menu的背景颜色
         mMenu.setMenuPressedBackColor(Color.WHITE);//Menu按下的背景颜色
         mMenu.setCheckIcon(R.drawable.ico_make);//Menu展开list的勾选图片
         mMenu.setUpArrow(R.drawable.arrow_up);//Menu默认状态的箭头
         mMenu.setDownArrow(R.drawable.arrow_down);//Menu按下状态的箭头
         mMenu.setDefaultMenuTitle(strings);//默认未选择任何过滤的Menu title
         */



    }

   /* private void setFilter(){
        List<String> temp=new ArrayList<String>();
        for (int i=0;i<getData().size();i++){
            boolean city=((city_index==0)?true:data.get(i).contains(arr1[city_index]));
            boolean sex=((sex_index==0)?true:data.get(i).contains(arr2[sex_index]));
            boolean age=((age_index==0)?true:data.get(i).contains(arr3[age_index]));
            if(city && sex && age){
                temp.add(data.get(i));
            }
        }
        mList.setAdapter(new ArrayAdapter<String>(SelectCityActivity.this, android.R.layout.simple_expandable_list_item_1, temp));
    }*/



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_city_back:
                finish();
                break;
            //点击搜索
            case R.id.select_city_search:
                keyword =  select_city_ed_search.getText().toString().trim();
                if(TextUtils.isEmpty(area_name)){
                    requestSearch(city);
                }else{
                    requestSearch(area_name);
                }

                break;
        }
    }


    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("city", city);
            OkGo.post(Urls.initSearch)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<InitSearchBean>(this) {
                        @Override
                        public void onSuccess(InitSearchBean initSearchBean, Call call, Response response) {
                            decodeInit(initSearchBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SelectCityActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeInit(InitSearchBean initSearchBean){
        String code = initSearchBean.getErr_code();
        String msg = initSearchBean.getErr_msg();
        if("200".equals(code)){
            areas = initSearchBean.getData().getArea();
            categorys = initSearchBean.getData().getCategory();
            paixus = initSearchBean.getData().getPaixue();
            init();
        }else{
            madapter.clearData();
            TUtils.showShort(SelectCityActivity.this,msg);
        }
    }

    /*private void requestListShops(String tid,String lx,String ly,String page){

        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("tid", tid);
            params.put("lx", lx);
            params.put("ly", ly);
            params.put("page", page);

            OkGo.post(Urls.Search)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SelectCityBean>(this) {
                        @Override
                        public void onSuccess(SelectCityBean selectCityBean, Call call, Response response) {
                            decodeSelectCity(selectCityBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SelectCityActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    private SelectCityAdapter adapter;
    private void decodeSelectCity(SelectCityBean selectCityBean){
        String code = selectCityBean.getErr_code();
        String msg = selectCityBean.getErr_msg();
        if("200".equals(code)){
            citydatas = selectCityBean.getData();
          /* if(madapter == null){
               madapter = new SelectCityDataAdapter(SelectCityActivity.this);
                //adapter = new SelectCityAdapter(SelectCityActivity.this,citydatas);
                mList.setAdapter(madapter);
            }else{
               madapter.addAllData(citydatas);
            }*/
            madapter.addAllData(citydatas);
            mList.setPullLoadMoreCompleted();
          //  mList.setAdapter(new SelectCityAdapter(SelectCityActivity.this,citydatas));
        }else{
            //madapter.clearData();
            mList.setPullLoadMoreCompleted();
            TUtils.showShort(SelectCityActivity.this, msg);
        }
    }



    private void requestSearch(String cityname){
        //LogUtil.log(LogType.ERROR,getClass(),"cityname--------"+cityname);
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("keyword", keyword);
           // params.put("area_name", area_name);
            params.put("tid", shop_cat);
            params.put("paixue", paixue);
            params.put("page", page);
            params.put("lx", lx);
            params.put("ly", ly);
            params.put("area_name",cityname);
            OkGo.post(Urls.Search)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SelectCityBean>(this) {
                        @Override
                        public void onSuccess(SelectCityBean selectCityBean, Call call, Response response) {
                            decodeSelectCity(selectCityBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SelectCityActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            LogUtil.log(LogType.ERROR,getClass(),e.toString());
            e.printStackTrace();
        }
    }

    private void init(){
        mMenu=(DropDownMenu)findViewById(R.id.dropDownMenu);

        mMenu.setmMenuCount(3);
        mMenu.setmShowCount(20);
        mMenu.setShowCheck(true);

        mMenu.setmMenuTitleTextSize(16);
        mMenu.setmMenuTitleTextColor(Color.parseColor("#777777"));
        mMenu.setmMenuListTextSize(16);
        mMenu.setmMenuListTextColor(Color.BLACK);
        mMenu.setmMenuBackColor(Color.WHITE);
        mMenu.setmMenuPressedBackColor(Color.WHITE);
        mMenu.setmMenuPressedTitleTextColor(Color.BLACK);

        mMenu.setmCheckIcon(R.mipmap.ico_make);
       // mMenu.setmUpArrow(R.mipmap.arrow_up);
        mMenu.setmDownArrow(R.mipmap.arrow_down);

        mMenu.setDefaultMenuTitle(strings);
        mMenu.setmMenuTitleTextColor(Color.BLACK);
        mMenu.setShowDivider(false);
        mMenu.setmMenuListBackColor(getResources().getColor(R.color.white));
        mMenu.setmMenuListSelectorRes(R.color.white);
        mMenu.setmArrowMarginTitle(20);

        mMenu.setMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            public void onSelected(View listview, int RowIndex, int ColumnIndex) {

                if (ColumnIndex == 0) {
                    city_index = RowIndex;
                } else if (ColumnIndex == 1) {
                    sex_index = RowIndex;
                } else {
                    age_index = RowIndex;
                }

                LogUtil.log(LogType.ERROR,getClass(),"city_index------------"+city_index);
                LogUtil.log(LogType.ERROR,getClass(),"sex_index------------"+sex_index);
                LogUtil.log(LogType.ERROR,getClass(),"age_index------------"+age_index);
                shop_cat = categorys.get(city_index).getId();
                area_name = areas.get(sex_index).getName();

                paixue = paixus.get(age_index).getId();
                if(madapter !=null){
                    madapter.clearData();
                }
                page =1;
                requestSearch(area_name);
               // LogUtil.log(LogType.ERROR,getClass(),"-----"+city_index+"-----"+sex_index+"-----"+age_index);
                //过滤筛选
              //  setFilter();
            }
        });

       /* List<String[]> items = new ArrayList<>();
        items.add(arr1);
        items.add(arr2);
        items.add(arr3);*/

        List<ArrayList<InitSearchBean.InitBean>> items = new ArrayList<>();
        items.add(categorys);
        items.add(areas);
        items.add(paixus);
        mMenu.setmMenuItems(items);
        mMenu.setIsDebug(false);

        if(TextUtils.isEmpty(area_name)){
            requestSearch(city);
        }else{
            requestSearch(area_name);
        }

        //data=getData();
       // mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data));

    }

    @Override
    public void onRefresh() {
        setRefresh();
        if(TextUtils.isEmpty(area_name)){
            requestSearch(city);
        }else{
            requestSearch(area_name);
        }
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        if(TextUtils.isEmpty(area_name)){
            requestSearch(city);
        }else{
            requestSearch(area_name);
        }
    }
    private void setRefresh() {
        madapter.clearData();
        page = 1;
    }

    public void clearData() {
        madapter.clearData();
        madapter.notifyDataSetChanged();
    }
}
