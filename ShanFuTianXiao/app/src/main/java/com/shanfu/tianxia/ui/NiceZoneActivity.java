package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.BasicAdapter;
import com.shanfu.tianxia.adapter.BasicViewHolder;
import com.shanfu.tianxia.adapter.NiceZoneAdapter;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.ZoneImageUrlBean;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.recyclerviewholder.BannerHolder;
import com.shanfu.tianxia.recyclerviewholder.LoadMore;
import com.shanfu.tianxia.recyclerviewholder.NavHolder;
import com.shanfu.tianxia.testRecyclerView.PowerRecyclerView;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import okhttp3.Call;
import okhttp3.Response;

import static com.shanfu.tianxia.recyclerviewholder.Home_Normal.totalnumber;

//import static com.shanfu.tianxia.recyclerviewholder.Home_Normal.totalnumber;

public class NiceZoneActivity extends BaseFragmentActivity implements View.OnClickListener {

    private static final String TAG = "LOG";
    @Bind(R.id.power_rv)
    RecyclerView mRecyclerView;


    private List<ZoneProductBean.DataBean.ListBean> listBeansAll = new ArrayList<>();
    private List<ZoneProductBean.DataBean.ListBean> listBeansMore = new ArrayList<>();
    /**
     * 网络请求判断条件
     */
    private int dataPage = 1;


    /**
     * 标题栏
     */
    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;
    private TextView content_head_right;

    /**
     * 广告图片的url数组
     */
    private NiceZoneAdapter niceZoneAdapter;
    private LayoutInflater inflater;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nice_zone);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initView();
//        requestDataBanner();
//        requestHotShop();

    }

    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("优品直购");
        content_head_right = (TextView) setting_top.findViewById(R.id.content_head_right);
        content_head_right.setText("更多");
        content_head_right.setOnClickListener(this);
        content_head_back.setOnClickListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        niceZoneAdapter = new NiceZoneAdapter(listBeansAll, this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (niceZoneAdapter.isFootView(position) || niceZoneAdapter.isHeadView(position))
                    return 2;
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(manager);

        niceZoneAdapter.addHeadView(new BasicAdapter.HeadAndFoot() {
            @Override
            public BasicViewHolder createHolder(ViewGroup parent) {
                return new BannerHolder(inflater.inflate(R.layout.nice_zone_headview, parent, false), NiceZoneActivity.this);
            }
        });

        niceZoneAdapter.addHeadView(new BasicAdapter.HeadAndFoot() {
            @Override
            public BasicViewHolder createHolder(ViewGroup parent) {
                return new NavHolder(inflater.inflate(R.layout.nice_zone_nav_view, parent, false));
            }
        });

        niceZoneAdapter.addFootView(new BasicAdapter.HeadAndFoot() {
            @Override
            public BasicViewHolder createHolder(ViewGroup parent) {
//                return new LoadMore(inflater.inflate(R.layout.nice_zone_footview, parent, false), NiceZoneActivity.this);
                return new LoadMore(inflater.inflate(R.layout.nice_zone_footview, parent, false));

            }
        });

        mRecyclerView.setAdapter(niceZoneAdapter);

        niceZoneAdapter.setOnItemClickListener(new BasicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(NiceZoneActivity.this, ZoneGoodsDetailsActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 首页商品列表
     */
    public void requestHotShop() {
        try {

            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("p", dataPage);

                OkGo.post(Urls.goodslist)
                        .tag(this)
                        .params(params)
                        .execute(new DialogCallback<ZoneProductBean>(this) {
                            @Override
                            public void onSuccess(ZoneProductBean zoneProductBean, Call call, Response response) {
                                decodeHotShop(zoneProductBean);
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                TUtils.showShort(getApplicationContext(), "数据获取失败，请检查网络后重试");
                            }
                        });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeHotShop(ZoneProductBean zoneProductBean) {
        String code = zoneProductBean.getErr_code();
        String msg = zoneProductBean.getErr_msg();
        if ("200".equals(code)) {
            dataPage += 1;
            listBeansMore.clear();
            listBeansMore = zoneProductBean.getData().getList();
            listBeansAll.addAll(listBeansMore);
            niceZoneAdapter.notifyDataSetChanged();
        } else {
            TUtils.showShort(NiceZoneActivity.this, msg);
        }
    }

    class LoadMore extends BasicViewHolder {
        private View itemView;

        public LoadMore(final View itemView) {
            super(itemView);
            this.itemView = itemView;

            final TextView footView = (TextView) itemView.findViewById(R.id.footTextView);

            itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                    Log.i(TAG, "onViewAttachedToWindow: "+totalnumber);
                    Handler handler = new Handler();

                    if (totalnumber == 0){
                        requestHotShop();
                    }else if (totalnumber > 0 && totalnumber/4 + 1 >= dataPage){
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                requestHotShop();
                            }
                        },500);
                    }else {
                        TUtils.showShort(NiceZoneActivity.this,"没有更多数据了...");
                        footView.setText("已经到底了哦...");
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                }
            });
        }

        @Override
        public void init(int position) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            case R.id.content_head_right:
                openPopup();
                break;
        }
    }

    private void openPopup() {
        View popupPayView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_zone_select_popup,null);
        final PopupWindow popupWindow = new PopupWindow(popupPayView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout mine_alredycargo_ll = (LinearLayout) popupPayView.findViewById(R.id.mine_alredycargo_ll);
        LinearLayout mine_waitcargo_ll = (LinearLayout) popupPayView.findViewById(R.id.mine_waitcargo_ll);
        LinearLayout zone_dingdan_mingxi_ll = (LinearLayout) popupPayView.findViewById(R.id.zone_dingdan_mingxi_ll);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        View parent = findViewById(R.id.content_head_right);
        popupWindow.showAtLocation(parent, Gravity.RIGHT|Gravity.TOP,50,140);
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        mine_alredycargo_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                intent = new Intent(NiceZoneActivity.this, ShippedActivity.class);
                startActivity(intent);
            }
        });
        mine_waitcargo_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                intent = new Intent(NiceZoneActivity.this, DropShipingActivity.class);
                startActivity(intent);
            }
        });
        zone_dingdan_mingxi_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                intent = new Intent(NiceZoneActivity.this, CheckForTheDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
