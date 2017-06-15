package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ShopAdressAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.AdressBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class MyShopAdressActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.adress_list)
    ListView adress_list;
    @Bind(R.id.add_adress_tv)
    TextView add_adress_tv;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    private List<AdressBean.DataBean.ListBean> adressBeanList;
    private Intent intent;
    private AdressBean adressBean;
    private ShopAdressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop_adress);
        ButterKnife.bind(this);
        initview();
        requestData();
    }

    private void initview() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("管理收货地址");
        content_head_back.setOnClickListener(this);

        add_adress_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_adress_tv:
                intent = new Intent(MyShopAdressActivity.this, AddAdressActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("ptoken", ptoken);
            params.put("uid", uid);

            OkGo.post(Urls.adress_manager)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<AdressBean>(this) {

                        @Override
                        public void onSuccess(AdressBean adressBean, Call call, Response response) {
                            decodeData(adressBean);
                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyShopAdressActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(AdressBean adressBean) {
        String code = adressBean.getErr_code();
        String msg = adressBean.getErr_msg();
        if ("200".equals(code)) {
            adressBeanList = adressBean.getData().getList();
            if (adapter == null) {
                adapter = new ShopAdressAdapter(this, adressBeanList);
                adress_list.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
        } else {
            TUtils.showShort(MyShopAdressActivity.this, msg);
        }
    }

    //点击设置默认
    public void requestCode() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("ptoken", ptoken);
            params.put("uid", uid);

            OkGo.post(Urls.adress_manager)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<AdressBean>(this) {

                        @Override
                        public void onSuccess(AdressBean adressBean, Call call, Response response) {
//                            decodeData(adressBean);
//                            OkGo.getInstance().debug("OkHttpUtils");

                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(MyShopAdressActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}