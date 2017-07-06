package com.shanfu.tianxia.recyclerviewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.adapter.BasicViewHolder;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.ZoneImageUrlBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by xuchenchen on 2017/7/3.
 */

public class BannerHolder extends BasicViewHolder {
    private View itemView;
    private BaseFragmentActivity mContext;
    private AutoScrollViewPager viewPager;
    private CirclePageIndicator indicator;
    private PagerAdapter pagerAdapter;
    private ArrayList<View> viewContainer;
    private static final int AUTO_SCROLL_INTERVAL = 3000;
    private ArrayList<String> urls;


    public BannerHolder(View itemView, BaseFragmentActivity context) {
        super(itemView);
        this.itemView = itemView;
        mContext = context;
        viewPager = (AutoScrollViewPager) itemView.findViewById(R.id.zone_viewPager);
        indicator = (CirclePageIndicator) itemView.findViewById(R.id.zone_indicator);
        requestDataBanner();
    }

    @Override
    public void init(int position) {
    }

    /**
     * 请求首页的bannar
     */
    private void requestDataBanner() {
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            OkGo.post(Urls.zone_banner)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<ZoneImageUrlBean>(mContext) {

                        @Override
                        public void onSuccess(ZoneImageUrlBean zoneImageUrlBean, Call call, Response response) {
                            decodeBanner(zoneImageUrlBean);
                            OkGo.getInstance().debug("OkHttpUtils");
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(mContext.getApplicationContext(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBanner(ZoneImageUrlBean zoneImageUrlBean) {
        String code = zoneImageUrlBean.getErr_code();
        String msg = zoneImageUrlBean.getErr_msg();
        if ("200".equals(code)) {
            ZoneImageUrlBean.ImageBean data = zoneImageUrlBean.getData();

            if (viewContainer == null) {
                viewContainer = new ArrayList<View>();
            }
            if (urls == null) {
                urls = new ArrayList<String>();
            }
            urls.clear();

            ZoneImageUrlBean.ImageUrls list = data.getList();
            String images0 = list.getImages0();
            String images1 = list.getImages1();
            String images2 = list.getImages2();

            urls.add(images0);
            urls.add(images1);
            urls.add(images2);

            viewContainer.clear();
            for (String url : urls) {
                NetworkImageView iv = new NetworkImageView(mContext.getApplicationContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                NetworkManager.getInstance().setImageUrl(iv, url);
                viewContainer.add(iv);
            }

            if (pagerAdapter == null) {
                pagerAdapter = new PagerAdapter() {

                    @Override
                    public void destroyItem(ViewGroup container, int position,
                                            Object object) {
                        ((AutoScrollViewPager) container).removeView(viewContainer
                                .get(position));
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        ((AutoScrollViewPager) container).addView(viewContainer
                                .get(position));
                        return viewContainer.get(position);
                    }

                    @Override
                    public boolean isViewFromObject(View arg0, Object arg1) {
                        return arg0 == arg1;
                    }

                    @Override
                    public int getCount() {
                        return viewContainer.size();
                    }
                };
            }

            viewPager.setAdapter(pagerAdapter);

            indicator.setViewPager(viewPager);
            viewPager.setInterval(AUTO_SCROLL_INTERVAL);
            viewPager.startAutoScroll();

            //设置完成滚动头
        }
    }

}
