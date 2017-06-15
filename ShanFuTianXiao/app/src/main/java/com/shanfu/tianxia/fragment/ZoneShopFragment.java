package com.shanfu.tianxia.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragment;
import com.shanfu.tianxia.ui.ZoneFrimOrderActivity;
import com.shanfu.tianxia.viewpagerindicator.CirclePageIndicator;

import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by xuchenchen on 2017/5/11.
 */

public class ZoneShopFragment extends Fragment implements View.OnClickListener{

    private View view;
    private AutoScrollViewPager scrollViewPager;
    private CirclePageIndicator indicator;
    private TextView zone_grid_shop_tv;
    private TextView zone_grid_lxp_tv;
    private TextView yuanjia_tv;
    private TextView kucun_tv;
    private TextView yiduihuan_tv;
    private ImageView zone_lxp_first_iv;
    private ImageView zone_lxp_second_iv;
    private TextView lijiduihuan_tv;

    private Context context;
    private ImageView shop_pic_iv;
    private TextView lxp_count_tv;
    private Button minus_btn;
    private Button add_btn;
    private TextView count_tv;
    private TextView query_tv;

    private int amount = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //中间加横线
//        tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        view = inflater.inflate(R.layout.shop_details_zone, null);
        init();
        return view;
    }
    private void init(){
        scrollViewPager = (AutoScrollViewPager) view.findViewById(R.id.zone_viewPager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.zone_indicator);
        zone_grid_shop_tv = (TextView) view.findViewById(R.id.zone_grid_shop_tv);
        zone_grid_lxp_tv = (TextView) view.findViewById(R.id.zone_grid_lxp_tv);
        yuanjia_tv = (TextView) view.findViewById(R.id.yuanjia_tv);
        kucun_tv = (TextView) view.findViewById(R.id.kucun_tv);
        yiduihuan_tv = (TextView) view.findViewById(R.id.yiduihuan_tv);
        zone_lxp_first_iv = (ImageView) view.findViewById(R.id.zone_lxp_first_iv);
        zone_lxp_second_iv = (ImageView) view.findViewById(R.id.zone_lxp_second_iv);
        lijiduihuan_tv = (TextView) view.findViewById(R.id.lijiduihuan_tv);
        lijiduihuan_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lijiduihuan_tv:
                openPopup();
                break;
        }
    }

    private void openPopup(){
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_selectcount_popup,null);
        shop_pic_iv = (ImageView) popupView.findViewById(R.id.shop_pic_iv);
        lxp_count_tv = (TextView) popupView.findViewById(R.id.lxp_count_tv);
        minus_btn = (Button) popupView.findViewById(R.id.minus_btn);
        add_btn = (Button) popupView.findViewById(R.id.add_btn);
        count_tv = (TextView) popupView.findViewById(R.id.count_tv);
        query_tv = (TextView) popupView.findViewById(R.id.query_tv);

        count_tv.setText(amount+"");

        final PopupWindow popupWindow = new PopupWindow(popupView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = getActivity().findViewById(R.id.root_view_shop);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
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

        minus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 1){
                    amount--;
                    count_tv.setText(amount+"");
                }
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount < 999){
                    amount++;
                    count_tv.setText(amount+"");
                }
            }
        });
        query_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZoneFrimOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
