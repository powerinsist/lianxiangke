package com.shanfu.tianxia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.UnusedAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.bean.TicktListBean;
import com.shanfu.tianxia.listener.DialogCallback;
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
 */

public class UnusedFragment extends Fragment implements View.OnClickListener{

//    private List<UnusedBean> list;
    private List<TicktListBean.DataBean.List2Bean> list = new ArrayList<>();

    private ListView lxp_list;
    private UnusedAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unused_lxp,null);
        lxp_list = (ListView) view.findViewById(R.id.lxp_unused_list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
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
                            super.onError(call, response, e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeData(TicktListBean ticktListBean) {
        String err_code = ticktListBean.getErr_code();
        String err_msg = ticktListBean.getErr_msg();

        if ("200".equals(err_code)){
            List<TicktListBean.DataBean.List2Bean> list2 = ticktListBean.getData().getList2();
//            list = ticktListBean.getData().getList2();
            if (list2 != null){
                list.addAll(list2);
            }

            if (adapter == null) {
                adapter = new UnusedAdapter(getActivity(),list);
                lxp_list.setAdapter(adapter);

            } else {
                adapter.notifyDataSetChanged();
            }
        }else {
            TUtils.showShort(getActivity(),err_msg);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
