package com.shanfu.tianxia.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.bean.BankResultBean;
import com.shanfu.tianxia.bean.UserBankCardBean;
import com.shanfu.tianxia.ui.MyBankCardActivity;
import com.shanfu.tianxia.ui.MyBankManageActivity;
import com.shanfu.tianxia.utils.SetBnakImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by qing on 2017/3/16.
 * 银行卡列表
 */
public class BankAdapter extends BaseAdapter{
    private List<UserBankCardBean.DataBeanX.DataBean.AgreementListBean> banks;
    private Context context;
    private LayoutInflater inflater;
    SetBnakImage sb = new SetBnakImage();
    private String bankName;
    private String bank_code;
    private String card_no;
    private String card_type;

    public BankAdapter(Context context,List<UserBankCardBean.DataBeanX.DataBean.AgreementListBean> banks){
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
        //银行
        bankName = banks.get(position).getBank_name();
        //银行编号
        bank_code = banks.get(position).getBank_code();
        //银行卡后四位
        card_no = banks.get(position).getCard_no();
        //银行卡类别
        card_type = banks.get(position).getCard_type();
        if ("2".equals(card_type)){
            holder.card_type.setText("借记卡");
        }
        if ("3".equals(card_type)){
            holder.card_type.setText("信用卡");
        }

        holder.card_name.setText(bankName);
        holder.card_num.setText("**** **** **** "+ card_no);

        sb.set(bank_code,holder.card_icon,holder.bank_bg);

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
         @Bind(R.id.bank_bg)
         LinearLayout bank_bg;


         public ViewHolder(View convertView) {

             ButterKnife.bind(this, convertView);
         }
    }
}
