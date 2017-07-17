package com.shanfu.tianxia.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.OnLineBean;
import com.shanfu.tianxia.ui.CommodityDetailsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by qing on 2017/3/30.
 */
public class OnLineAdapter extends BaseAdapter {

    private List<OnLineBean.DataBean.ListBean> lists;
    private LayoutInflater inflater;
    private String member_name, member_id, reg_time;
    private Context mContext;
    private String phone;

    public OnLineAdapter(Context mContext, List<OnLineBean.DataBean.ListBean> lists) {
        inflater = LayoutInflater.from(mContext);
        this.lists = lists;
        this.mContext = mContext;
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
                PermissionGen.with((Activity) mContext)
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

    @PermissionSuccess(requestCode = 100)
    public void doSomething(){

        CallPhone();
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        PermissionGen.with((Activity) mContext)
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
            Toast.makeText(mContext, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + phone); // 设置数据
            intent.setData(data);
            mContext.startActivity(intent); // 激活Activity组件
        }
    }

}

