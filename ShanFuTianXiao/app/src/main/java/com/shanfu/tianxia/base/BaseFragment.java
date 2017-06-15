package com.shanfu.tianxia.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = createView(inflater,container,savedInstanceState);
        ButterKnife.bind(this,view);

        initToolBar();

        init();
        return view;

    }

    public void  initToolBar(){

    }

    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void init();

    @Override
    public void onPause() {
        super.onPause();
        OkGo.getInstance().cancelTag(this);
        //MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
       // MobclickAgent.onResume(getActivity());       //统计时长
    }
}
