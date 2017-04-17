package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.InitSearchBean;

import java.util.ArrayList;

/**
 *
 *  <a href="http://fangjie.info">JayFang</a>
 *  Email:JayFang1993@gmail.com
 *  GitHub:github.com/JayFang1993
 *
 */

public class MenuListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<InitSearchBean.InitBean> strs;

    private int SelectIndex;
    private int TextSize;
    private int TextColor;
    private boolean showCheck;
    private int CheckIcon;

    public MenuListAdapter(Context context,ArrayList<InitSearchBean.InitBean> strs) {
        this.context = context;
        this.strs=strs;
        this.TextColor= Color.BLACK;
        this.TextSize=15;
    }

    public void setSelectIndex(int selectIndex) {
        SelectIndex = selectIndex;
    }


    public void setShowCheck(boolean showCheck) {
        this.showCheck = showCheck;
    }

    public void setTextSize(int textSize) {
        TextSize = textSize;
    }

    public void setTextColor(int textColor) {
        TextColor = textColor;
    }

    public void setCheckIcon(int checkIcon) {
        CheckIcon = checkIcon;
    }

    @Override
    public int getCount() {
        return strs.size();
    }

    @Override
    public Object getItem(int position) {
        return strs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.menu_list_item,parent,false);
        TextView textView=(TextView)v.findViewById(R.id.tv_menu_item);
        textView.setTextSize(TextSize);
        textView.setTextColor(TextColor);
        textView.setText(strs.get(position).getName());

        if (showCheck&&SelectIndex==position) {
            ImageView imageView = (ImageView) v.findViewById(R.id.iv_menu_select);
            imageView.setVisibility(View.VISIBLE);
            if (CheckIcon!=0)
               imageView.setImageResource(CheckIcon);
        }
        return v;
    }
}