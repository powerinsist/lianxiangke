package com.shanfu.tianxia.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuchenchen on 2017/7/3.
 */

public abstract  class BasicAdapter extends RecyclerView.Adapter<BasicViewHolder> {
    private static final int HEAD_KEY = 10000;
    private static final int FOOT_KEY = 20000;
    private SparseArrayCompat<HeadAndFoot> mHeadViews = new SparseArrayCompat<>();
    private SparseArrayCompat<HeadAndFoot> mFootViews = new SparseArrayCompat<>();

    //回调监听
    protected OnItemClickListener listener;

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO 这里的填充使用 LayoutInflater.from(context).inflate(id,parent,false) 不然布局会设置与实际显示不一样
        if (mHeadViews.get(viewType) != null) return mHeadViews.get(viewType).createHolder(parent);
        if (mFootViews.get(viewType) != null) return mFootViews.get(viewType).createHolder(parent);
        return createRelViewHolder(parent);
    }

    /**
     * @return 这里一般返回可重复利用的holder，也就是除了头布局和脚布局之外的holder
     */
    protected abstract BasicViewHolder createRelViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {
        holder.init(position - mHeadViews.size());
    }

    @Override
    public int getItemCount() {
        return mHeadViews.size() + getRelCount() + mFootViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeadView(position)) return mHeadViews.keyAt(position);
        if (isFootView(position)) return mFootViews.keyAt(position - mHeadViews.size() - getRelCount());
        return super.getItemViewType(position);
    }

    /**
     * @return 除了头布局 脚布局之外的布局个数
     */
    protected abstract int getRelCount();


    /**
     * @param position
     * @return 是否是头布局，只要当前position小于头布局内个数即可
     */
    public boolean isHeadView(int position) {
        return position < mHeadViews.size();
    }

    /**
     * @param position
     * @return 是否是脚布局，当前position ≥ 头布局+RelCount
     */
    public boolean isFootView(int position) {
        return position >= mHeadViews.size() + getRelCount();
    }

    public void addHeadView(HeadAndFoot headAndFoot) {
        mHeadViews.put(mHeadViews.size() + HEAD_KEY, headAndFoot);
    }

    public void addFootView(HeadAndFoot headAndFoot) {
        mFootViews.put(mFootViews.size() + FOOT_KEY, headAndFoot);
    }



    //添加头布局和脚布局的接口类
    public interface HeadAndFoot {
        BasicViewHolder createHolder(ViewGroup parent);
    }

    //增加Item监听
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
