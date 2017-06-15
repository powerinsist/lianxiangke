package com.shanfu.tianxia.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.CityListAdapter;
import com.shanfu.tianxia.adapter.ResultListAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.HotCityResult;
import com.shanfu.tianxia.dbhelper.DBManager;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.model.City;
import com.shanfu.tianxia.model.LocateState;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.LogType;
import com.shanfu.tianxia.utils.LogUtil;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.SideLetterBar;

import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Response;


public class LocationCityActivity extends BaseFragmentActivity implements View.OnClickListener {


    public static final String KEY_PICKED_CITY = "picked_city";

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;

    private AMapLocationClient mLocationClient;
    //private PermissionHelper permissionHelper;
    private List<String> record_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cp_activity_city_list);
        record_city = SPUtils.getInstance().getSaveCity("city");

        //permissionHelper = PermissionHelper.getInstance(this);
      //  permissionHelper.request(Manifest.permission.ACCESS_FINE_LOCATION);
       //permissionHelper.request(Manifest.permission.INTERNET);
        //requestAllCity();
       // initLocation();
        PermissionGen.with(LocationCityActivity.this)
                .addRequestCode(100)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .request();
        initLocation();
    }
    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
      //  TUtils.showShort(this, "doSomething");
        requestData();
       // initLocation();
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){

        TUtils.showShort(this, "请开启定位权限");
        requestData();
    }
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }


    private void initLocation() {
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        Log.e("LOG","--------定位---------"+city);
//                        Log.e("LOG",city);
                       // String district = aMapLocation.getDistrict();
                       // String location = StringUtils.extractLocation(city, district);
                       // double latitude = aMapLocation.getLatitude();
                        //  double longitude = aMapLocation.getLongitude();
                       // LogUtil.log(LogType.ERROR,getClass(),latitude+"");
                       // LogUtil.log(LogType.ERROR,getClass(),longitude+"");
                       // LogUtil.log(LogType.ERROR,getClass(),city+"----");
                        // LogUtil.log(LogType.ERROR, getClass(), city + "----");
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, city);
                    } else {
                        //定位失败
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void initData(List<HotCityResult.DataBean>  lists) {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();

        mCityAdapter = new CityListAdapter(this, mAllCities, lists, record_city);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                mLocationClient.startLocation();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

   private void init() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());

            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (ImageView) findViewById(R.id.back);

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    private void back(String city){

        SPUtils.getInstance().putCity("city",city);
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationClient!=null){
            mLocationClient.stopLocation();
        }

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.back) {
            finish();

        }
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token =MD5Utils.MD5(Constants.appKey + time);


            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            OkGo.post(Urls.home_hotcity)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<HotCityResult>(this) {
                        @Override
                        public void onSuccess(HotCityResult hotCityResult, Call call, Response response) {
                           decodeHotCity(hotCityResult);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(LocationCityActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    List<HotCityResult.DataBean>  lists;
    private void decodeHotCity(HotCityResult hotCityResult){

        String errorCode = hotCityResult.getStatus().getSucceed();
        String msg = hotCityResult.getMessage();
        if("1".equals(errorCode)){
            lists =  hotCityResult.getData();
            initData(lists);
            initLocation();
            init();

        }else{
            TUtils.showShort(LocationCityActivity.this, msg);
        }


    }


}
