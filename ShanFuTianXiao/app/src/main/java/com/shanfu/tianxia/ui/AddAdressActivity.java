package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.city.CityPicker;
import com.shanfu.tianxia.city.CitycodeUtil;
import com.shanfu.tianxia.city.ScrollerNumberPicker;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class AddAdressActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "LOG";
    @Bind(R.id.shouhuoren_ed)
    EditText shouhuoren_ed;
    @Bind(R.id.phone_ed)
    EditText phone_ed;
    @Bind(R.id.diqu_iv)
    ImageView diqu_iv;
    @Bind(R.id.tianxie_ed)
    EditText tianxie_ed;
    @Bind(R.id.moren_switch)
    Switch moren_switch;
    @Bind(R.id.address_tv)
    TextView address_tv;

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private TextView content_head_right;

    private String checked = "0";
    private String name;
    private String telephone;
    private String address;
    private String detailaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_right = (TextView) setting_top.findViewById(R.id.content_head_right);
        content_head_title.setText("添加新地址");
        content_head_back.setOnClickListener(this);
        content_head_right.setOnClickListener(this);

        shouhuoren_ed.setOnClickListener(this);
        phone_ed.setOnClickListener(this);
        diqu_iv.setOnClickListener(this);
        tianxie_ed.setOnClickListener(this);
        moren_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checked = "1";
                }else {
                    checked = "0";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.content_head_right:
                name = shouhuoren_ed.getText().toString().trim();
                telephone = phone_ed.getText().toString().trim();
                address = address_tv.getText().toString().trim();
                detailaddress = tianxie_ed.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    TUtils.showShort(this,"请输入收货人姓名");
                    return;
                }
                if (TextUtils.isEmpty(telephone)||telephone.length() != 11){
                    TUtils.showShort(this,"请输入正确联系电话");
                    return;
                }
                if (address.equals("请选择")){
                    TUtils.showShort(this,"请选择所在地区");
                    return;
                }
                if (TextUtils.isEmpty(detailaddress)||detailaddress.length()<5){
                    TUtils.showShort(this,"请输入详细地址");
                    return;
                }
                requestData(name,telephone,address,detailaddress,checked);
                break;
            case R.id.diqu_iv:
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAdressActivity.this);
                View view = LayoutInflater.from(AddAdressActivity.this).inflate(R.layout.addressdialog, null);
                builder.setView(view);
                LinearLayout addressdialog_linearlayout = (LinearLayout)view.findViewById(R.id.addressdialog_linearlayout);
                final ScrollerNumberPicker provincePicker = (ScrollerNumberPicker)view.findViewById(R.id.province);
                final ScrollerNumberPicker cityPicker = (ScrollerNumberPicker)view.findViewById(R.id.city);
                final ScrollerNumberPicker counyPicker = (ScrollerNumberPicker)view.findViewById(R.id.couny);
                final AlertDialog dialog = builder.show();

                addressdialog_linearlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*CitycodeUtil citycodeUtil = CitycodeUtil.getSingleton();
                        CityPicker picker = new CityPicker(AddAdressActivity.this);
                        ArrayList<String> city_list_code = citycodeUtil.getCity_list_code();
                        Log.i(TAG, "onClick: "+city_list_code);
                        ArrayList<String> province_list_code = citycodeUtil.getProvince_list_code();
                        Log.i(TAG, "onClick: "+province_list_code);
                        ArrayList<String> couny_list_code = citycodeUtil.getCouny_list_code();
                        Log.i(TAG, "onClick: "+couny_list_code);*/
                        address_tv.setText(provincePicker.getSelectedText()+cityPicker.getSelectedText()+counyPicker.getSelectedText());
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    private void requestData(String name,String telephone,String address,String detailaddress,String checked){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            final HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("ptoken", ptoken);

            params.put("name",name);
            params.put("telephone",telephone);
            params.put("address",address);
            params.put("detailaddress",detailaddress);
            params.put("checked",checked);
            OkGo.post(Urls.add_adress)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RsultBean>(this){
                        @Override
                        public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(AddAdressActivity.this, "数据获取失败，请检查网络后重试");

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(RsultBean rsultBean){
        String code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        if(!"200".equals(code)){
            TUtils.showShort(AddAdressActivity.this,msg);
        }else{
            TUtils.showShort(this,msg);
            Intent intent = new Intent(AddAdressActivity.this, MyShopAdressActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
