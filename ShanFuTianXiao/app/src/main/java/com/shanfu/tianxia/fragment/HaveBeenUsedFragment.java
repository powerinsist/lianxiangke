package com.shanfu.tianxia.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.HaveBeenUsedAdapter;
import com.shanfu.tianxia.adapter.MylianxpAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.bean.BonusBean;
import com.shanfu.tianxia.bean.BonusRedPacketBean;
import com.shanfu.tianxia.bean.RedLxpBean;
import com.shanfu.tianxia.bean.TicktListBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.ui.BlueLxpDetailsActivity;
import com.shanfu.tianxia.ui.BrownLxpDetailsActivity;
import com.shanfu.tianxia.ui.GreenLxpDetailsActivity;
import com.shanfu.tianxia.ui.RedLxpDetailsActivity;
import com.shanfu.tianxia.ui.RedPacketRecordActivity;
import com.shanfu.tianxia.ui.RedPayPacketRecordActivity;
import com.shanfu.tianxia.ui.WalletBalancePayActivity;
import com.shanfu.tianxia.ui.YellowLxpDetailsActivity;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by xuchenchen on 2017/5/15.
 *
 * 未使用
 */

public class HaveBeenUsedFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "LOG";
    private Intent intent;
//    private List<TicktListBean.DataBean.List1Bean> list = new ArrayList<>();
    private ListView lxp_list;
    private View view;
    private TextView lingpiao_tv;
    private String lingpiao;

    private View headView;
    private ImageView color_iv;
    private TextView number_tv;
    private LinearLayout lianxp_lv;
    private String money;
    private String id;
    private List<TicktListBean.DataBean.List1Bean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.havebeen_used_lxp,null);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (adapter != null){
//            list.clear();
//            adapter.notifyDataSetChanged();
//        }
        requestData();
    }

    private void initView(){
        headView = LayoutInflater.from(getContext()).inflate(R.layout.layout_lxp_item_headview, null,false);
        lxp_list = (ListView) view.findViewById(R.id.lxp_list);
        lxp_list.addHeaderView(headView);
        lxp_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: "+position);
                doSomething(parent.getAdapter().getItem(position),position);
            }
            private void doSomething(Object item,int position) {
                if (position == 0){
                    return;
                }
                TicktListBean.DataBean.List1Bean ticktListBean = list.get(position-1);
                money = ticktListBean.getMoney();
                id = ticktListBean.getId();
                if ("0".equals(money)){
                    Log.i(TAG, "doSomething: "+ money);
                }else {
                    Log.i(TAG, "doSomething: else "+ money);

                    openPopup();
                }
            }
        });
    }

    private void requestData() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);

            OkGo.post(Urls.ticktlist)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<TicktListBean>(getActivity()) {
                        @Override
                        public void onSuccess(TicktListBean ticktListBean, Call call, Response response) {
                            decodeData(ticktListBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(),"数据获取失败，请检查网络后重试");
                            Log.i(TAG, "onError: "+e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private UnusedAdapter adapter;
    private void decodeData(TicktListBean ticktListBean) {
        String err_code = ticktListBean.getErr_code();
        String err_msg = ticktListBean.getErr_msg();

        list = new ArrayList<>();

        if ("200".equals(err_code)){
            lingpiao = ticktListBean.getData().getLingpiao();
            Log.i(TAG, "decodeData: "+lingpiao);
            lingpiao_tv = (TextView) headView.findViewById(R.id.lingpiao_tv);
            lingpiao_tv.setText(lingpiao+"张");

            List<TicktListBean.DataBean.List1Bean> list1 = ticktListBean.getData().getList1();
            Log.e("LOG","----list---"+list.toString());
            Log.e("LOG","----list1-----"+list1.toString());
//            list = ticktListBean.getData().getList1();
            if (list1.size() != 0){
                Log.e("LOG","xxxxxxx0000000");
                list.clear();
                list.addAll(list1);
                Log.e("LOG","xxxxxxx111111");
                list1.clear();
                Log.e("LOG","xxxxxxx222222");

            }
            if (adapter == null) {
                Log.e("LOG","11111111");
                adapter = new UnusedAdapter(getActivity(), list);
                lxp_list.setAdapter(adapter);

            } else {
                Log.e("LOG","222222222");
                adapter.notifyDataSetChanged();
            }

        }else {
            TUtils.showShort(getActivity(),err_msg);
        }
    }

    class ViewHolder{
        ImageView color_iv;
        TextView number_tv;
        LinearLayout lianxp_lv;
    }
    class UnusedAdapter extends BaseAdapter{

        private Context context;
        private List<TicktListBean.DataBean.List1Bean> list2;
        private View adapterview;


        public UnusedAdapter(Context context,List<TicktListBean.DataBean.List1Bean> list2){
            this.context = context;
            this.list2 = list2;
        }

        @Override
        public int getCount() {
            return list2.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                adapterview = LayoutInflater.from(getActivity()).inflate(R.layout.lxp_unused_item,null,false);
            }else {
                adapterview = convertView;
            }
            TicktListBean.DataBean.List1Bean ticktListBean = list2.get(position);
            String money = ticktListBean.getMoney();

            color_iv = (ImageView) adapterview.findViewById(R.id.color_iv);
            number_tv = (TextView) adapterview.findViewById(R.id.number_tv);
            lianxp_lv = (LinearLayout) adapterview.findViewById(R.id.lianxp_lv);

            if ("0".equals(money)){
                color_iv.setImageResource(R.mipmap.blueticket_bg_lianxp);
                number_tv.setText(ticktListBean.getId());
            }else{
                color_iv.setImageResource(R.mipmap.redticket_bg_lianxp);
                number_tv.setText(ticktListBean.getId());
            }
            return adapterview;
        }
    }

    private void openPopup(){
        View popupRedView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_red_packet_popup,null);
        Button close_btn = (Button) popupRedView.findViewById(R.id.close_btn);
        TextView red_money_tv = (TextView) popupRedView.findViewById(R.id.red_money_tv);
        Button get_redpacket_btn = (Button) popupRedView.findViewById(R.id.get_redpacket_btn);

        final PopupWindow popupWindow = new PopupWindow(popupRedView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = view.findViewById(R.id.root_view);
        popupWindow.showAtLocation(parent, Gravity.CENTER,0,0);
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getActivity().getWindow().setAttributes(params);
            }
        });
        red_money_tv.setText(money);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        get_redpacket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                requestRedData();

            }
        });
    }

    private void requestRedData() {
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

            params.put("id",id);
            params.put("money_order",money);

            OkGo.post(Urls.bonus)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<BonusBean>(getActivity()) {
                        @Override
                        public void onSuccess(BonusBean bonusBean, Call call, Response response) {
                            decodeBonus(bonusBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBonus(BonusBean bonusBean) {
        String err_code = bonusBean.getErr_code();
        String err_msg = bonusBean.getErr_msg();
        String ret_code = bonusBean.getData().getData().getRet_code();
        String ret_msg = bonusBean.getData().getData().getRet_msg();

        String money_order = bonusBean.getData().getData().getMoney_order();

        if ("200".equals(err_code) && "0000".equals(ret_code)){

            Intent intent = new Intent(getContext(), RedPacketRecordActivity.class);
            intent.putExtra("money_order",money_order);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            onDestroy();
        }else{
            TUtils.showShort(getActivity(),ret_msg);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: 3333");
    }

    @Override
    public void onClick(View v) {

    }
}
