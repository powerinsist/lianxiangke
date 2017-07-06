package com.shanfu.tianxia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xuchenchen on 2017/7/3.
 */

public abstract  class BasicViewHolder extends RecyclerView.ViewHolder {
    public BasicViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void init(int position);
}
