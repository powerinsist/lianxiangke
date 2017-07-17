package com.shanfu.tianxia.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.OnLineAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.OnLineBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 排对详情
 */
public class OnLineActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout on_line_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private List<OnLineBean.DataBean.ListBean> lists;
    private OnLineAdapter adapter;

    @Bind(R.id.recommend_total_tv)
    TextView recommend_total_tv;
    @Bind(R.id.new_people_num_tv)
    TextView new_people_num_tv;
    @Bind(R.id.first_level_num_tv)
    TextView first_level_num_tv;
    @Bind(R.id.second_level_num_tv)
    TextView second_level_num_tv;
    @Bind(R.id.my_online_listview)
    ListView my_online_listview;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line);
        ButterKnife.bind(this);
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }
    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(OnLineActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("version", version);
            params.put("ptoken", ptoken);
            OkGo.post(Urls.mySpread)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<OnLineBean>(this) {
                        @Override
                        public void onSuccess(OnLineBean onLineBean, Call call, Response response) {
                            decodeResult(onLineBean);
                        }
                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(OnLineActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        on_line_top = (RelativeLayout) findViewById(R.id.on_line_top);
        content_head_back = (RelativeLayout) on_line_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) on_line_top.findViewById(R.id.content_head_title);
        content_head_title.setText("我的会员");
        content_head_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
        }
    }

    private void decodeResult(OnLineBean onLineBean){
        String code = onLineBean.getErr_code();
        String msg = onLineBean.getErr_msg();
        if("200".equals(code)){
            SPUtils.getInstance().putString("ptoken", onLineBean.getData().getPtoken());
            first_level_num_tv.setText("第一级会员: " + onLineBean.getData().getFirst_level_num() + "人");
            recommend_total_tv.setText("推荐总人数: " + onLineBean.getData().getRecommend_total() + "人");
            new_people_num_tv.setText("昨日新增: " + onLineBean.getData().getNew_people_num() + "人");
            second_level_num_tv.setText("第二级会员: " + onLineBean.getData().getSecond_level_num() + "人");
            lists = onLineBean.getData().getList();
            if(adapter ==null){
                adapter = new OnLineAdapter(OnLineActivity.this,lists);
                my_online_listview.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();


        }else if("103".equals(code)){
            Intent intent = new Intent(OnLineActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            TUtils.showShort(OnLineActivity.this,msg);
            first_level_num_tv.setText("第一级会员: " + "0人");
            recommend_total_tv.setText("推荐总人数: " +"0人");
            new_people_num_tv.setText("昨日新增: " +  "0人");
            second_level_num_tv.setText("第二级会员: " +  "0人");
        }
    }

    public class OnLineAdapter extends BaseAdapter {

        private List<OnLineBean.DataBean.ListBean> lists;
        private LayoutInflater inflater;
        private String member_name, member_id, reg_time;

        public OnLineAdapter(Context mContext, List<OnLineBean.DataBean.ListBean> lists) {
            inflater = LayoutInflater.from(mContext);
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_online_item, parent, false);
                convertView.setTag(new ViewHolder(convertView));
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            member_id = lists.get(position).getMember_id();
            member_name = lists.get(position).getMember_name();
            reg_time = lists.get(position).getReg_time();
            phone = lists.get(position).getPhone();
            holder.member_id_tv.setText(member_id);

            holder.member_name_tv.setText(member_name);

            holder.reg_time_tv.setText(reg_time);

            holder.phone_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PermissionGen.with(OnLineActivity.this)
                            .addRequestCode(100)
                            .permissions(Manifest.permission.CALL_PHONE)
                            .request();
                }
            });
            return convertView;

        }

        class ViewHolder {

            @Bind(R.id.member_name_tv)
            TextView member_name_tv;
            @Bind(R.id.member_id_tv)
            TextView member_id_tv;
            @Bind(R.id.reg_time_tv)
            TextView reg_time_tv;
            @Bind(R.id.phone_iv)
            ImageView phone_iv;


            public ViewHolder(View convertView) {

                ButterKnife.bind(this, convertView);
            }
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething(){

        CallPhone();
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        PermissionGen.with(this)
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
            Toast.makeText(OnLineActivity.this, "号码不能为空！", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
