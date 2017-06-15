package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shanfu.tianxia.R;

/**
 * Created by xuchenchen on 2017/5/17.
 */

public class SpinnerArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] stringArray;
    public SpinnerArrayAdapter(@NonNull Context context, String[] stringArray) {
        super(context, R.layout.my_spinner,stringArray);
        this.context = context;
        this.stringArray = stringArray;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(stringArray[position]);
//        tv.setTextSize(22f);
        tv.setTextColor(Color.BLACK);

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
