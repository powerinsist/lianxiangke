package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.BankResultBean;
import com.shanfu.tianxia.utils.SetBnakImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qing on 2017/3/16.
 * 银行卡列表
 */
public class BankAdapter extends BaseAdapter{
    private List<BankResultBean.BankBean> banks;
    private Context context;
    private LayoutInflater inflater;
    SetBnakImage sb = new SetBnakImage();
    public BankAdapter(Context context,List<BankResultBean.BankBean> banks){
      this.banks = banks;
        inflater =  LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return banks.size();
    }

    @Override
    public Object getItem(int position) {
        return banks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bank_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        String bankName = banks.get(position).getBankname();
        sb.set(bankName,holder.card_icon);
        holder.card_name.setText(bankName);
        holder.card_num.setText(banks.get(position).getCardno());
        return convertView;
    }



     class ViewHolder{
         @Bind(R.id.card_icon)
         ImageView card_icon;
         @Bind(R.id.card_name)
         TextView card_name;
         @Bind(R.id.card_type)
         TextView card_type;
         @Bind(R.id.card_num)
         TextView card_num;


         public ViewHolder(View convertView) {

             ButterKnife.bind(this, convertView);
         }
    }
}
