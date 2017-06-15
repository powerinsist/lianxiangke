package com.shanfu.tianxia.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.BankLianLianBean;
import com.shanfu.tianxia.bean.TopBankBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

//充值页面选择银行卡
public class TopSelectBankActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "LOG";
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.bank_list)
    ListView bank_list;
    private List<BankLianLianBean.DataBeanX.DataBean> list;
    private HashMap<Integer, Integer> map = new HashMap<>();
    private TopBankBean topBankBean;
    private RadioAdapter adapter;
    private String dataname = "请添加银行卡";
    private int code;
    private Intent mIntent;
    private String bankname;
    private String card_no;
    private String no_agree;
    private String pay_type;
    private String bind_mob;
    private String vali_date;
    private String cvv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_select_bank);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        code = intent.getIntExtra("code", 1111);
        //create 时创建intent，设置 name，在接收方，如果 datename = "1" 表示没有请求到网络

        //直接按返回键，返回的intent是空
        mIntent = new Intent();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.banklianlian)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BankLianLianBean>(this) {
                        @Override
                        public void onSuccess(BankLianLianBean bankLianLianBean, Call call, Response response) {
                            decodeData(bankLianLianBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(TopSelectBankActivity.this,"数据获取失败，请检查网络后重试");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void decodeData(BankLianLianBean bankLianLianBean) {
        String err_code = bankLianLianBean.getErr_code();
        String err_msg = bankLianLianBean.getErr_msg();
        list = new ArrayList<>();
        if ("200".equals(err_code)) {
            list = bankLianLianBean.getData().getData();
            if (adapter == null) {
                adapter = new RadioAdapter(this, list);
                bank_list.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

        } else {
            TUtils.showShort(TopSelectBankActivity.this, err_msg);
        }
    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("选择银行卡");
        content_head_back.setOnClickListener(this);

        //默认选中第一个
//        map.put(0, 100);
        bank_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub

                RadioHolder holder = new RadioHolder(arg1);
                holder.radio_btn.setChecked(true);
//                map.clear();
//                map.put(arg2, 100);
                //这里就知道被点击的position
                BankLianLianBean.DataBeanX.DataBean bankLianLianBen = list.get(arg2);
                bankname = bankLianLianBen.getBankname();
                card_no = bankLianLianBen.getCard_no();
                no_agree = bankLianLianBen.getNo_agree();
                pay_type = bankLianLianBen.getPay_type();
                bind_mob = bankLianLianBen.getBind_mob();
                vali_date = bankLianLianBen.getVali_date();
                cvv2 = bankLianLianBen.getCvv2();

                String substring = card_no.substring(card_no.length() - 4, card_no.length());
                dataname = bankname + "(" + substring + ")";
                Log.i("LOG", "onItemClick: " + dataname);
//                adapter.notifyDataSetChanged();
            }
        });
    }

    class RadioHolder {
        private RadioButton radio_btn;
        private TextView bank_name;

        public RadioHolder(View view) {
            this.radio_btn = (RadioButton) view.findViewById(R.id.radio_btn);
            this.bank_name = (TextView) view.findViewById(R.id.bank_name);
        }
    }

    class RadioAdapter extends BaseAdapter {
        private Context context;
        private List<BankLianLianBean.DataBeanX.DataBean> list;

        public RadioAdapter(Context context, List<BankLianLianBean.DataBeanX.DataBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RadioHolder radioHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.top_select_bank_item, null, false);
                radioHolder = new RadioHolder(convertView);
                convertView.setTag(radioHolder);
            } else {
                radioHolder = (RadioHolder) convertView.getTag();
            }
            BankLianLianBean.DataBeanX.DataBean bankLianLianBen = list.get(position);
            String bankname = bankLianLianBen.getBankname();
            String card_no = bankLianLianBen.getCard_no();
            String substring = card_no.substring(card_no.length() - 4, card_no.length());
            dataname = bankname + "(" + substring + ")";
//            topBankBean = list.get(position);
            radioHolder.bank_name.setText(bankname + "(" + substring + ")");
            radioHolder.radio_btn.setChecked(map.get(position) == null ? false : true);

            return convertView;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_head_back:
                back();
                break;
        }
    }

   //对返回键做监听试试

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back(){
        mIntent.putExtra("name", dataname);
        mIntent.putExtra("no_agree",no_agree);
        mIntent.putExtra("bankName",bankname);
        mIntent.putExtra("card_no",card_no);
        mIntent.putExtra("pay_type",pay_type);
        mIntent.putExtra("bind_mob",bind_mob);
        mIntent.putExtra("vali_date",vali_date);
        mIntent.putExtra("cvv2",cvv2);
//        Log.i("LOG", "back: " + dataname);
        setResult(code, mIntent);
        finish();
    }
}
