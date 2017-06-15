package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.RegeditBean;
import com.shanfu.tianxia.date.ChangeDatePopwindow;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 绑定银行卡
 */
public class BindingBankCardActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout bind_card_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.support_card_tv)
    TextView support_card_tv;
    @Bind(R.id.binding_bank_card_name)
    EditText binding_bank_card_name;
    @Bind(R.id.select_band_card)
    Spinner select_band_card;
    @Bind(R.id.binding_bank_card_number)
    EditText binding_bank_card_number;
    @Bind(R.id.date_rl)
    RelativeLayout date_rl;
    @Bind(R.id.date_tv)
    TextView date_tv;
    @Bind(R.id.cvv2_ed)
    EditText cvv2_ed;
    @Bind(R.id.binding_card)
    Button binding_card;
    @Bind(R.id.gone_ll)
    LinearLayout gone_ll;

    private String cardNum,phoneNum;
    private String t_status,p_status,b_status;
    private List<String> list;
    private String bank;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_bank_card);
        ButterKnife.bind(this);
        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");

        initView();

    }

    private void initView() {
        bind_card_top = (RelativeLayout) findViewById(R.id.bind_card_top);
        content_head_back = (RelativeLayout) bind_card_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) bind_card_top.findViewById(R.id.content_head_title);
        content_head_title.setText("添加银行卡");
        content_head_back.setOnClickListener(this);

        binding_card.setOnClickListener(this);
        support_card_tv.setOnClickListener(this);
        date_rl.setOnClickListener(this);

        /*选择银行卡类别*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,getDatabanks());
        select_band_card.setAdapter(adapter);
        select_band_card.setOnItemSelectedListener(new OnItemSelectedListenerSpinner());
        /*年*/

    }
    private class OnItemSelectedListenerSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //银行卡类别 bank
            bank = select_band_card.getSelectedItem().toString();
            if ("借记卡".equals(bank)||"选择类别".equals(bank)){
                gone_ll.setVisibility(View.GONE);
            } else {
                gone_ll.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

    public List<String> getDatabanks(){
        list = new ArrayList<>();
        list.add("选择类别");
        list.add("借记卡");
        list.add("信用卡");
        return list;
    }

    private String[] selectDate() {
        final String[] str = new String[10];
        final ChangeDatePopwindow mChangeBirthDialog = new ChangeDatePopwindow(BindingBankCardActivity.this);
        mChangeBirthDialog.showAtLocation(date_rl, Gravity.BOTTOM, 0, 0);
        mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {

            @Override
            public void onClick(String year, String month, String day) {
                // TODO Auto-generated method stub
//                    Toast.makeText(MainActivity.this,year + "-" + month + "-" + day,Toast.LENGTH_LONG).show();
                StringBuilder sb = new StringBuilder();
                sb.append(year.substring(0, year.length() - 1)).append("-").append(month.substring(0, day.length() - 1)).append("-").append(day);
                str[0] = year + "-" + month + "-" + day;
                str[1] = sb.toString();
                if (month.length() != 2){
                    month = "0"+month;
                }
                date_tv.setText(year + month);

            }
        });
        return str;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.binding_card:
                String name = binding_bank_card_name.getText().toString().trim();
                String number = binding_bank_card_number.getText().toString().trim();
                String date = date_tv.getText().toString().trim();
                String cvv2 = cvv2_ed.getText().toString().trim();
                if (TextUtils.isEmpty(name)||name.length()>6){
                    TUtils.showShort(BindingBankCardActivity.this,"请填写持卡人姓名");
                    return;
                }
                if (TextUtils.isEmpty(number)){
                    TUtils.showShort(BindingBankCardActivity.this,"银行卡号不能为空");
                    return;
                }
                if ("选择类别".equals(bank)){
                    TUtils.showShort(BindingBankCardActivity.this,"请选择银行卡类别");
                    return;
                }
                if ("借记卡".equals(bank)){
                    intent = new Intent(BindingBankCardActivity.this,BindBankNextActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("number",number);
                    intent.putExtra("bank",bank);
                    startActivity(intent);
                    finish();
                }
                if ("信用卡".equals(bank)){
                    if ("请选择".equals(date)){
                        TUtils.showShort(BindingBankCardActivity.this,"请选择正确有效期");
                        return;
                    }
                    if (TextUtils.isEmpty(cvv2)){
                        TUtils.showShort(BindingBankCardActivity.this,"请输入CVV2号");
                        return;
                    }
                    String date_four = date.substring(date.length()-4, date.length());
                    intent = new Intent(BindingBankCardActivity.this,BindBankNextActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("number",number);
                    intent.putExtra("bank",bank);
                    intent.putExtra("date",date_four);
                    intent.putExtra("cvv2",cvv2);
                    startActivity(intent);
                    finish();
                }
                break;
//            case R.id.binding_card:
//                cardNum = binding_bank_card_number.getText().toString().trim();
//                String bankNum = cardNum.substring(0,5);
//                phoneNum = binding_bank_card_phone_number.getText().toString().trim();
//                if(TextUtils.isEmpty(cardNum)){
//                    TUtils.showShort(BindingBankCardActivity.this,"银行卡号不能为空");
//                    return;
//                }
//                if(TextUtils.isEmpty(phoneNum)){
//                    TUtils.showShort(BindingBankCardActivity.this,"手机号不能为空");
//                    return;
//                }
//                if(phoneNum.length()!=11){
//                    TUtils.showShort(BindingBankCardActivity.this, "请输入正确的手机号码");
//                    return;
//                }
//                requestData(cardNum,phoneNum);
//                intent = new Intent(BindingBankCardActivity.this,BindBankNextActivity.class);
//                startActivity(intent);
//                break;
            case R.id.support_card_tv:
                intent = new Intent(BindingBankCardActivity.this,SupportBankActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.date_rl:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding_bank_card_number.getWindowToken(), 0);
                selectDate();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void requestData(String card,String num){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(BindingBankCardActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("bankCardNum", card);
            params.put("prePhone", num);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);
            //选择银行
            params.put("bank",bank);

            OkGo.post(Urls.bindBankCard)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<RegeditBean>(this) {
                        @Override
                        public void onSuccess(RegeditBean rsultBean, Call call, Response response) {
                            decodeResult(rsultBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(BindingBankCardActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeResult(RegeditBean rsultBean){
        String err_code = rsultBean.getErr_code();
        String msg = rsultBean.getErr_msg();
        if("200".equals(err_code)){
            TUtils.showShort(BindingBankCardActivity.this, "绑定成功");
            SPUtils.getInstance().putString("ptoken", rsultBean.getData().getPtoken());
            SPUtils.getInstance().putString("b_status", "1");
            SPUtils.getInstance().putString("bankcard", cardNum);
            Intent intent = new Intent(BindingBankCardActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("comefrom","mine");
            startActivity(intent);
            finish();
        }else if("103".equals(err_code)){
            Intent intent = new Intent(BindingBankCardActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            TUtils.showShort(BindingBankCardActivity.this, msg);
        }
    }
}
