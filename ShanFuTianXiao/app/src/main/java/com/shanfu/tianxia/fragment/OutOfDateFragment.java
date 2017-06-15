package com.shanfu.tianxia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.OutOfDataAdapter;
import com.shanfu.tianxia.bean.UnusedBean;
import com.shanfu.tianxia.utils.TUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/15.
 */

public class OutOfDateFragment extends Fragment implements View.OnClickListener{

    private List<UnusedBean> list;
    private ListView lxp_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.outof_date_lxp,null);
        lxp_list = (ListView) view.findViewById(R.id.outof_list);
        initData();
        return view;
    }

    private void initData(){
//        list = new ArrayList<>();
//        for (int i = 0;i < 10;i++){
//            UnusedBean unusedBean = new UnusedBean();
//            unusedBean.setDate("2017.05.01-2017.05.06");
//            unusedBean.setNumber("123456789"+i);
//            list.add(unusedBean);
//        }
//        OutOfDataAdapter adapter = new OutOfDataAdapter(getActivity(),list);
//        lxp_list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
