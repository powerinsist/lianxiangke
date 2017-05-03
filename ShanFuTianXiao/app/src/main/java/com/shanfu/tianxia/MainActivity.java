package com.shanfu.tianxia;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.shanfu.tianxia.appconfig.AppManager;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.TabHostBean;
import com.shanfu.tianxia.fragment.FragmentTabHost;
import com.shanfu.tianxia.fragment.HomeFragment;
import com.shanfu.tianxia.fragment.MineFragment;
import com.shanfu.tianxia.fragment.ScanningFragment;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.ui.CaptureTwoActivity;
import com.shanfu.tianxia.ui.LoginActivity;
import com.shanfu.tianxia.ui.PaymentActivity;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.umeng.socialize.PlatformConfig;

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

public class MainActivity extends BaseFragmentActivity {

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
        initTabs();

        //微信平台的配置
        PlatformConfig.setWeixin("wx3fca228ffb8ecee1","bd51b52b4ccfbed57f317e92fbd58818");

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
                    String uid = SPUtils.getInstance().getString("uid","");
                    if(TextUtils.isEmpty(uid)){
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                       // intent.putExtra("comefrom","home");
                        startActivity(intent);
                    }
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
                if (data != null)
                {
                    String result = data.getStringExtra("result");
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
                   /* if (result != null)
                        tv.setText(result);*/
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
