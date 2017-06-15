package com.shanfu.tianxia.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.EvaluationAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.CommodityDetailsItemBean;
import com.shanfu.tianxia.bean.ShopDetailBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.MyRatingBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 首页listview点击进云的商品详情
 */
public class CommodityDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.commodity_details_back)
    ImageButton commodity_details_back;
    private AMapLocationClient mLocationClient;

    private String shopid, shopname;
    private NetworkImageView details_img;
    private TextView details_shop_name, shop_address;

    private EvaluationAdapter mAdapter;
    private ArrayList<CommodityDetailsItemBean.ItemBean> data;
    private int page = 1;
    private String phone;
    private RelativeLayout call_phone;
    private LinearLayout address_layout;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private TextView number_of_people;
    private MyRatingBar shop_title_grade,shop_grade;

    private String uid;
    private String grade,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details);
        ButterKnife.bind(this);

        shopid = getIntent().getStringExtra("shopid");
        shopname = getIntent().getStringExtra("shopname");
        grade = getIntent().getStringExtra("grade");
        img = getIntent().getStringExtra("img");
        initView();
        Log.e("LOG",shopid);
        //requestItemData(1);

    }

    TextView details_fraction,enviroonment,waiter,price,taste;
    private void initView() {
        // share.setOnClickListener(this);
        commodity_details_back.setOnClickListener(this);

        View headView = View.inflate(this, R.layout.commodity_details_head, null);
        details_img = (NetworkImageView) headView.findViewById(R.id.details_img);
        details_shop_name = (TextView) headView.findViewById(R.id.details_shop_name);
        details_fraction = (TextView) headView.findViewById(R.id.details_fraction);
        enviroonment = (TextView) headView.findViewById(R.id.enviroonment);
        waiter = (TextView) headView.findViewById(R.id.waiter);
        price = (TextView) headView.findViewById(R.id.price);
        taste = (TextView) headView.findViewById(R.id.taste);

        details_shop_name.setOnClickListener(this);
        shop_address = (TextView) headView.findViewById(R.id.shop_address);
        call_phone = (RelativeLayout) headView.findViewById(R.id.call_phone);
        address_layout = (LinearLayout)headView.findViewById(R.id.address_layout);
        shop_title_grade = (MyRatingBar) headView.findViewById(R.id.shop_title_grade);
        shop_grade = (MyRatingBar) headView.findViewById(R.id.shop_grade);
        shop_title_grade.setClickable(false);
        shop_grade.setClickable(false);

        if(!TextUtils.isEmpty(grade)){
            shop_title_grade.setStar(Float.valueOf(grade));
            shop_grade.setStar(Float.valueOf(grade));
        }else{
            shop_title_grade.setStar(0);
            shop_grade.setStar(0);
        }


        number_of_people = (TextView)headView.findViewById(R.id.number_of_people);
        address_layout.setOnClickListener(this);
        call_phone.setOnClickListener(this);
        listView.addHeaderView(headView);
        number_of_people.setOnClickListener(this);

        mAdapter = new EvaluationAdapter(this, new ArrayList<CommodityDetailsItemBean.ItemBean>());
        listView.setAdapter(mAdapter);
        requestItemData(page);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                requestItemData(page);
            }
        });


    }

    /**
     * 请求评论列表
     */
    private void requestItemData(int page) {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("shopid", shopid);
            params.put("page", page + "");
            OkGo.post(Urls.loadCommnetList)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<CommodityDetailsItemBean>(this) {
                        @Override
                        public void onSuccess(CommodityDetailsItemBean commodityDetailsItemBean, Call call, Response response) {
                            decodeItem(commodityDetailsItemBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(CommodityDetailsActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeItem(CommodityDetailsItemBean commodityDetailsItemBean) {
        String err_code = commodityDetailsItemBean.getErr_code();
        String err_msg = commodityDetailsItemBean.getErr_msg();
        if ("200".equals(err_code)) {
            data = commodityDetailsItemBean.getData();
            mAdapter.setData(data);
            page++;
            ptr.refreshComplete();
        } else {
            TUtils.showShort(CommodityDetailsActivity.this, err_msg);
        }
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("shopid", shopid);


            OkGo.post(Urls.shopDetail)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ShopDetailBean>(this) {
                        @Override
                        public void onSuccess(ShopDetailBean shopDetailBean, Call call, Response response) {
                            decodeShopDetail(shopDetailBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(CommodityDetailsActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String lx,ly;
    private String address;
    private void decodeShopDetail(ShopDetailBean shopDetailBean) {
        String err_code = shopDetailBean.getErr_code();
        String err_msg = shopDetailBean.getErr_msg();
        if ("200".equals(err_code)) {
            String url = shopDetailBean.getData().getBanner();
            lx = shopDetailBean.getData().getLx();
            ly = shopDetailBean.getData().getLy();
            //
            NetworkManager.getInstance().setImageUrl(details_img, url);

           if(!TextUtils.isEmpty(shopname)){
               details_shop_name.setText(shopname);
           }
            details_fraction.setText(shopDetailBean.getData().getComment_num()+"人评价");
            address = shopDetailBean.getData().getAddress();
            enviroonment.setText("环境不错("+shopDetailBean.getData().getHj()+")");
            waiter.setText("服务态度好(" + shopDetailBean.getData().getFw() + ")");
            price.setText("价格实惠(" + shopDetailBean.getData().getJg() + ")");
            taste.setText("味道不错(" + shopDetailBean.getData().getWd()+ ")");
            shop_address.setText(address);
            phone = shopDetailBean.getData().getPhone();
        } else {
            TUtils.showShort(CommodityDetailsActivity.this, err_msg);
        }

    }
    private Intent intent;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_layout:

                intent = new Intent(CommodityDetailsActivity.this, RouteActivity.class);
                intent.putExtra("lx",lx);
                intent.putExtra("ly",ly);
                startActivity(intent);
                break;

            case R.id.commodity_details_back:
                finish();
                break;

            case R.id.call_phone:

                PermissionGen.with(CommodityDetailsActivity.this)
                        .addRequestCode(100)
                        .permissions(Manifest.permission.CALL_PHONE)
                        .request();
               /* if(ContextCompat.checkSelfPermission(CommodityDetailsActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CommodityDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }else{
                    CallPhone();
                }*/
                break;
            case R.id.number_of_people:
                uid = SPUtils.getInstance().getString("uid","");
                if(TextUtils.isEmpty(uid)){
                    Intent intent = new Intent(CommodityDetailsActivity.this,LoginActivity.class);
                    intent.putExtra("comefrom","CommodityDetailsActivity");
                    startActivity(intent);
                }else{
                    intent = new Intent(CommodityDetailsActivity.this, EvaluationMerchantActivity.class);
                    intent.putExtra("shopid",shopid);
                    intent.putExtra("address",address);
                    intent.putExtra("shopname",shopname);
                    intent.putExtra("img",img);

                    startActivity(intent);
                }

                break;
        }

    }






    @PermissionSuccess(requestCode = 100)
    public void doSomething(){

        CallPhone();
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        PermissionGen.with(CommodityDetailsActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.CALL_PHONE
                )
                .request();
    }
    private void CallPhone() {

        if (TextUtils.isEmpty(phone)) {
            // 提醒用户
            // 注意：在这个匿名内部类中如果用this则表示是View.OnClickListener类的对象，
            // 所以必须用MainActivity.this来指定上下文环境。
            Toast.makeText(CommodityDetailsActivity.this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + phone); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }
    // 处理权限申请的回调
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
        requestItemData(1);
    }
}
