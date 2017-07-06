package com.shanfu.tianxia.recyclerviewholder;

import android.util.Log;
import android.view.View;

import com.shanfu.tianxia.adapter.BasicViewHolder;
import com.shanfu.tianxia.bean.ZoneProductBean;
import com.shanfu.tianxia.ui.NiceZoneActivity;
import com.shanfu.tianxia.utils.TUtils;

import java.util.List;

import static com.shanfu.tianxia.recyclerviewholder.Home_Normal.totalnumber;
/**
 * Created by xuchenchen on 2017/7/3.
 */

public class LoadMore extends BasicViewHolder {
    private NiceZoneActivity niceZoneActivity;
    private View itemView;

    public LoadMore(View itemView, final NiceZoneActivity niceZoneActivity) {
        super(itemView);
        this.itemView = itemView;
        this.niceZoneActivity = niceZoneActivity;

        itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                niceZoneActivity.requestHotShop();
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
