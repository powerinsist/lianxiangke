package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.ShopAdressAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.AdressBean;
import com.shanfu.tianxia.bean.RsultBean;
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

    private static final String TAG = "LOG";
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
                finish();
                break;
            case R.id.content_head_back:
                finish();
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

            OkGo.post(Urls.address_list)
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
//            if (adapter == null) {
                adapter = new ShopAdressAdapter(this, adressBeanList);
                adress_list.setAdapter(adapter);
//            }
            adapter.notifyDataSetChanged();
        } else {
            TUtils.showShort(MyShopAdressActivity.this, msg);
        }
    }

    public class ShopAdressAdapter extends BaseAdapter {
        private static final String TAG = "LOG";
        private List<AdressBean.DataBean.ListBean> list;
        private Context context;
        private String checked;

        private String name;
        private String telephone;
        private String adress;
        private String detailaddress;
        private String address_id;

        public ShopAdressAdapter(Context context,List<AdressBean.DataBean.ListBean> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_shop_adress_item,null,false);
                viewHolder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
                viewHolder.phone_tv = (TextView) convertView.findViewById(R.id.phone_tv);
                viewHolder.adress_tv = (TextView) convertView.findViewById(R.id.adress_tv);
                viewHolder.radio_btn = (CheckBox) convertView.findViewById(R.id.radio_btn);
                viewHolder.moren_tv = (TextView) convertView.findViewById(R.id.moren_tv);
                viewHolder.bianji_tv = (TextView) convertView.findViewById(R.id.bianji_tv);
                viewHolder.delete_tv = (TextView) convertView.findViewById(R.id.delete_tv);
                convertView.setTag(viewHolder);
            }else
                viewHolder = (ViewHolder) convertView.getTag();
            final AdressBean.DataBean.ListBean adressBean = list.get(position);
            address_id = adressBean.getAddress_id();
            name = adressBean.getName();
            telephone = adressBean.getTelephone();
            adress = adressBean.getAddress();
            detailaddress = adressBean.getDetailaddress();
            checked = adressBean.getChecked();
            viewHolder.name_tv.setText(name);
            viewHolder.phone_tv.setText(telephone);
            viewHolder.adress_tv.setText(adress + detailaddress);
            Log.i(TAG, "getView: "+list.size());
            if (list.size() == 1){
                viewHolder.radio_btn.setChecked(true);
                viewHolder.radio_btn.setEnabled(false);
            }else if (list.size() != 0 && checked.equals("1")){
                viewHolder.radio_btn.setChecked(true);
            }else {
                viewHolder.radio_btn.setChecked(false);
            }
            viewHolder.radio_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                        params.put("name",list.get(position).getName());
                        params.put("telephone",list.get(position).getTelephone());
                        params.put("address",list.get(position).getAddress());
                        params.put("detailaddress",list.get(position).getDetailaddress());
                        params.put("checked","1");
                        params.put("address_id",list.get(position).getAddress_id());
                        Log.i(TAG, "requestCode: "+list.get(position).getAddress_id());

                        OkGo.post(Urls.adress_save)
                                .tag(this)
                                .params(params)
                                .execute(new DialogCallback<RsultBean>(MyShopAdressActivity.this) {

                                    @Override
                                    public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                                        if (rsultBean.getErr_code().equals("200")){
                                            requestData();
                                        }else {
                                            TUtils.showShort(MyShopAdressActivity.this,rsultBean.getErr_msg());
                                        }
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

            });

            viewHolder.bianji_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,AddAdressActivity.class);
                    intent.putExtra("name",list.get(position).getName());
                    intent.putExtra("phone",list.get(position).getTelephone());
                    intent.putExtra("address",list.get(position).getAddress()+
                            list.get(position).getDetailaddress());
                    intent.putExtra("checked",list.get(position).getChecked());
                    context.startActivity(intent);

                }
            });
            viewHolder.delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                        params.put("address_id",list.get(position).getAddress_id());

                        OkGo.post(Urls.address_delete)
                                .tag(this)
                                .params(params)
                                .execute(new DialogCallback<RsultBean>(MyShopAdressActivity.this) {

                                    @Override
                                    public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                                        if (rsultBean.getErr_code().equals("200")){
                                            requestData();
                                        }else {
                                            TUtils.showShort(MyShopAdressActivity.this,rsultBean.getErr_msg());
                                        }
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

            });

            return convertView;
        }

        class ViewHolder{
            public TextView name_tv;
            public TextView phone_tv;
            public TextView adress_tv;
            public CheckBox radio_btn;
            public TextView moren_tv;
            public TextView bianji_tv;
            public TextView delete_tv;
        }
    }

}