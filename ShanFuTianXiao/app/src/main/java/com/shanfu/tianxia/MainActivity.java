package com.shanfu.tianxia;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.appconfig.AppManager;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.GetLLInfoBean;
import com.shanfu.tianxia.bean.ResultTrueBean;
import com.shanfu.tianxia.bean.SaoMaCodeBean;
import com.shanfu.tianxia.bean.TabHostBean;
import com.shanfu.tianxia.fragment.FragmentTabHost;
import com.shanfu.tianxia.fragment.HomeFragment;
import com.shanfu.tianxia.fragment.MineFragment;
import com.shanfu.tianxia.fragment.ScanningFragment;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.CaptureTwoActivity;
import com.shanfu.tianxia.ui.LoginActivity;
import com.shanfu.tianxia.ui.PaymentActivity;
import com.shanfu.tianxia.ui.RealNameFirstActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.umeng.socialize.PlatformConfig;


//import com.umeng.socialize.PlatformConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = "LOG";
    private long exitTime = 0;
    private FragmentTabHost mTabHost;
    private LayoutInflater  mInflater;
    private List<TabHostBean> mTabs = new ArrayList<TabHostBean>();
    private final static int REQ_CODE = 1028;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    private static final int REQUEST_LOCATION_CODE = 2;
    @Bind(R.id.scanning_img)
    ImageView scanning_img;
    private AMapLocationClient mLocationClient;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String sha1 = sHA1(this);
        //LogUtil.log(LogType.ERROR, getClass(), sha1);
        ButterKnife.bind(this);
       PermissionGen.with(MainActivity.this)
                .addRequestCode(200)
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA)
                .request();
      /*  AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .rationale(mRationaleListener)
                .send();*/
      //  MPermissions.requestPermissions(MainActivity.this, REQUEST_LOCATION_CODE, Manifest.permission.ACCESS_FINE_LOCATION);

        NetworkManager.getInstance().init(getApplicationContext());

        String comefrom = getIntent().getStringExtra("comefrom");

        scanning_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCamare();
            }
        });

        String uid = SPUtils.getInstance().getString("uid","");

        initTabs();

        //微信平台的配置
        PlatformConfig.setWeixin("wx3fca228ffb8ecee1","bd51b52b4ccfbed57f317e92fbd58818");

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
       Intent intent = new Intent(MainActivity.this, CaptureTwoActivity.class);
        startActivityForResult(intent, REQ_CODE);
       // initLocation();

    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){

    }
     @PermissionSuccess(requestCode = 200)
    public void requestLocationSuccess(){


         initLocation();
    }
    @PermissionFail(requestCode = 200)
    public void requestLocationFials(){

    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(MainActivity.this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        double lx = aMapLocation.getLatitude();
                        double ly = aMapLocation.getLongitude();
                        String city =  aMapLocation.getCity();

                        //aMapLocation.getLongitude();
                        SPUtils.getInstance().putString("lx",lx+"");
                        SPUtils.getInstance().putString("ly",ly+"");
                        SPUtils.getInstance().putString("city", city);
                        //aMapLocation.getCity();
                      //  city = aMapLocation.getCity();
                       // LogUtil.log(LogType.ERROR,getClass(),"lx-----"+lx+"----ly=----"+ly);

                    } else {
                       // city = "北京";
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }


    private void initTabs() {
        TabHostBean tab_home = new TabHostBean(HomeFragment.class, R.string.home, R.drawable.selector_icon_home);
        TabHostBean tab_scan = new TabHostBean(ScanningFragment.class, R.string.mine, R.drawable.selector_icon_scanning);
        TabHostBean tab_mine = new TabHostBean(MineFragment.class, R.string.three, R.drawable.selector_icon_mine);
        //TabHostBean tab_scan = new TabHostBean( R.string.mine, R.drawable.selector_icon_mine);
        mTabs.add(tab_home);
        mTabs.add(tab_scan);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("扫码".equals(tabId)) {
                    checkCamare();
                }
                if("个人中心".equals(tabId)){
                    uid = SPUtils.getInstance().getString("uid","");
                    if(TextUtils.isEmpty(uid)){
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                       // intent.putExtra("comefrom","home");
                        startActivity(intent);
                    }
                    /*else if (TextUtils.isEmpty(user_id)){
                        showDialog();
                    }*/
//                    else {
//                        requestInfo();

//                    }

//                    mTabHost.setCurrentTab(2);
//                    if (TextUtils.isEmpty(user_id)){
//                        requestInfo();
//                        openPopup();
//                    }

                }
            }
        });
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        for (TabHostBean tab : mTabs) {

            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
                mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(TabHostBean tab) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        ImageView tabImg = (ImageView) view.findViewById(R.id.tab_icon);
        TextView tabDesc = (TextView) view.findViewById(R.id.tab_desc);
        tabImg.setBackgroundResource(tab.getIcon());
        tabDesc.setText(tab.getTitle());

        return view;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                TUtils.showShort(MainActivity.this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            }else{
                AppManager.getAppManager().AppExit(MainActivity.this);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void checkCamare(){
        PermissionGen.with(MainActivity.this)
                .addRequestCode(100)
                .permissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
      //  MPermissions.requestPermissions(MainActivity.this, REQUEST_PERMISSION_CAMERA_CODE, Manifest.permission.ACCESS_FINE_LOCATION);


    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        //AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        //MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String shopid,shopname,shopresult;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,  data);
        mTabHost.setCurrentTab(0);
        switch (requestCode)
        {
            case REQ_CODE:
                String uid = SPUtils.getInstance().getString("uid","");
                if (TextUtils.isEmpty(uid)){
                    TUtils.showShort(MainActivity.this,"您还没有登陆！");
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else if (data != null) {
                    final String result = data.getStringExtra("result");
                    Log.i(TAG, "onActivityResult: " + result);
                    // http://www.lianxiangke.cn/Home/Shop/show/id/100090   未实名
                    //http://www.lianxiangke.cn/Home/Shop/show/id/100143    已实名
//                    requestResult(result);
                    if (result.contains("/show/")){
                        OkGo.get(result)
                                .tag(this)
                                .url(result)
                                .execute(new DialogCallback<ResultTrueBean>(this) {
                                    @Override
                                    public void onSuccess(ResultTrueBean resultTrueBean, Call call, Response response) {
                                        String id = resultTrueBean.getId();
                                        String shopname = resultTrueBean.getShopname();
                                        String user_id = resultTrueBean.getUser_id();
                                        String name_user = resultTrueBean.getName_user();
                                        Log.e("LOG", "------OkGo---------" + user_id);

                                        if (result.contains("/show/")) {
                                            Log.e("LOG", result);
                                            if (TextUtils.isEmpty(user_id)) {
                                                Log.e("LOG", "-----null----" + user_id);
                                                TUtils.showShort(MainActivity.this, "对不起，该商户未开通钱包功能！");
                                                return;
                                            }
                                            if (result.contains("/shopname/")) {
                                                shopresult = result.split("/shopname/")[0];
                                                shopname = result.split("/shopname/")[1];
                                                if (shopresult.contains("id/")) {
                                                    shopid = shopresult.split("id/")[1];
                                                }
                                            } else {
                                                shopid = result.split("id/")[1];
                                            }
                                            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                                            intent.putExtra("shopid", shopid);
                                            intent.putExtra("shopname", shopname);
                                            intent.putExtra("url", result);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    } else if (result.contains("/user_id/")) {
                        String result1 = result.split("=/")[1];
                        String[] ss = result1.split("/");
                        String id_person = ss[1];
                        if (id_person.equals(uid)) {
                            TUtils.showShort(MainActivity.this, "自己不能向自己付款！");
                            return;
                        }else {
                            String name_user_person = ss[5];
                            String money = ss[7];
                            String user_id_person = ss[3];

                            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                            intent.putExtra("person_id", id_person);
                            intent.putExtra("person_name", name_user_person);
                            intent.putExtra("person_money", money);
                            intent.putExtra("person_user_id", user_id_person);
                            startActivity(intent);
                        }
                    } else {
                        TUtils.showShort(MainActivity.this, "请扫描正确的二维码");
                        return;
                    }
                }
                break;

            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == REQ_CODE) {
            if(data!=null){
                String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
                if(!TextUtils.isEmpty(result)){
                    if(result.contains("/shopname/")){
                        shopresult = result.split("/shopname/")[0];
                        shopname = result.split("/shopname/")[1];
                        if(shopresult.contains("id/")){
                            shopid = shopresult.split("id/")[1];
                        }
                    }else{
                        shopid =result.split("id/")[1];
                    }

                    Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                    intent.putExtra("shopid",shopid);
                    intent.putExtra("shopname",shopname);
                    startActivity(intent);
                }
                //TUtils.showShort(MainActivity.this, "扫码结果：" + result);
            }
        }*/
    }


    public  static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String comefrom = intent.getStringExtra("comefrom");
        if("home".equals(comefrom)){
            mTabHost.setCurrentTab(0);
        }else if("mine".equals(comefrom)){
            mTabHost.setCurrentTab(2);
        }
    }
}
