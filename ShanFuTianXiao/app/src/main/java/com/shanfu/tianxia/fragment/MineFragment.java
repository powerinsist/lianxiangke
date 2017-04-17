package com.shanfu.tianxia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragment;
import com.shanfu.tianxia.bean.MineBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.ui.AuthenticationActivity;
import com.shanfu.tianxia.ui.BindingBankCardActivity;
import com.shanfu.tianxia.ui.ConsumptionActivity;
import com.shanfu.tianxia.ui.FanHuiMingXiActivity;
import com.shanfu.tianxia.ui.LoginActivity;
import com.shanfu.tianxia.ui.MyBankCardActivity;
import com.shanfu.tianxia.ui.MyMerchantActivity;
import com.shanfu.tianxia.ui.OnLineActivity;
import com.shanfu.tianxia.ui.QueryIncomeActivity;
import com.shanfu.tianxia.ui.RechargeActivity;
import com.shanfu.tianxia.ui.RevenueInquiryActivity;
import com.shanfu.tianxia.ui.SecurityActivity;
import com.shanfu.tianxia.ui.SetUpPwdActivity;
import com.shanfu.tianxia.ui.SettingActivity;
import com.shanfu.tianxia.ui.WithdrawalsActivity;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView like;
    private Intent intent;

    @Bind(R.id.recharge)
    RelativeLayout recharge;
    @Bind(R.id.withdrawals)
    RelativeLayout withdrawals;
    @Bind(R.id.con_my_con_safe_normal_layout)
    LinearLayout con_my_con_safe_normal_layout;
    @Bind(R.id.con_my_con_name_normal_layout)
    LinearLayout con_my_con_name_normal_layout;

    @Bind(R.id.my_bank_card)
    RelativeLayout my_bank_card;
    @Bind(R.id.revenue_inquiry)
    RelativeLayout revenue_inquiry;
    @Bind(R.id.on_line)
    RelativeLayout on_line;
    //消费
    @Bind(R.id.consumption)
    RelativeLayout consumption;
    @Bind(R.id.account)
    RelativeLayout account;
    @Bind(R.id.my_merchant)
    RelativeLayout my_merchant;
    @Bind(R.id.receiving_address)
    RelativeLayout receiving_address;
    @Bind(R.id.fanhui_mingxi)
    RelativeLayout fanhui_mingxi;


    @Bind(R.id.my_con)
    TextView my_con;
    @Bind(R.id.my_con_wop)
    TextView my_con_wop;
    @Bind(R.id.con_my_con_balance)
    TextView con_my_con_balance;
    @Bind(R.id.con_my_con_goback)
    TextView con_my_con_goback;
    @Bind(R.id.profile_image)
    CircleImageView profile_image;
    @Bind(R.id.con_my_con_name_normal_img)
    ImageView con_my_con_name_normal_img;
    @Bind(R.id.con_my_con_safe_normal_img)
    ImageView con_my_con_safe_normal_img;
    @Bind(R.id.user_name_tv)
    TextView user_name_tv;
    @Bind(R.id.uid_tv)
    TextView uid_tv;
    @Bind(R.id.dengji)
    ImageView dengji;

    /**
     * 实名认证
     */
    @Bind(R.id.con_my_con_name_normal)
    TextView con_my_con_name_normal;






    @Override
    public View createView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        return view;
    }



    @Override
    public void init() {
        recharge.setOnClickListener(this);
        withdrawals.setOnClickListener(this);
        con_my_con_safe_normal_layout.setOnClickListener(this);
        con_my_con_name_normal_layout.setOnClickListener(this);
        my_bank_card.setOnClickListener(this);
        revenue_inquiry.setOnClickListener(this);
        on_line.setOnClickListener(this);
        consumption.setOnClickListener(this);
        account.setOnClickListener(this);
        my_merchant.setOnClickListener(this);
        receiving_address.setOnClickListener(this);
        fanhui_mingxi.setOnClickListener(this);
        profile_image.setOnClickListener(this);
       // requestData();

    }
    private String t_status,p_status,b_status;
    @Override
    public void onResume() {

        super.onResume();

        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");

        if("1".equals(t_status)&&"1".equals(p_status)&&"1".equals(b_status)){
            con_my_con_name_normal_img.setImageResource(R.mipmap.con_my_con_name_selected);
        }else{
            con_my_con_name_normal_img.setImageResource(R.mipmap.con_my_con_name_normal);
        }

        if("1".equals(p_status)){
            con_my_con_safe_normal_img.setImageResource(R.mipmap.con_my_con_safe_normal);
        }else{
            con_my_con_safe_normal_img.setImageResource(R.mipmap.con_my_con_safe_normal_hui);
        }
        boolean request = SPUtils.getInstance().getBoolean("request",true);

        if(request){
            requestData();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        OkGo.getInstance().cancelTag(this);
    }

    private void requestData(){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(getActivity());
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);

            OkGo.post(Urls.index)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MineBean>(getActivity()) {
                        @Override
                        public void onSuccess(MineBean mineBean, Call call, Response response) {
                            decodeData(mineBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String avater;
    private String level;
    private void decodeData(MineBean mineBean){
        String code = mineBean.getErr_code();
        String msg = mineBean.getErr_msg();
        if("200".equals(code)){
            avater = mineBean.getData().getAvater();
            SPUtils.getInstance().putString("ptoken",mineBean.getData().getPtoken());
            SPUtils.getInstance().putString("avater",avater);
            SPUtils.getInstance().putString("nickname", mineBean.getData().getNickname());
            SPUtils.getInstance().putString("uid", mineBean.getData().getUid());
            SPUtils.getInstance().putString("bankcard", mineBean.getData().getBankcard());
            level =  mineBean.getData().getLevel();
            if("1".equals(level)){
                dengji.setImageResource(R.mipmap.dengji1);
            }else if("2".equals(level)){
                dengji.setImageResource(R.mipmap.dengji2);
            }else if("3".equals(level)){
                dengji.setImageResource(R.mipmap.dengji3);
            }

            my_con.setText(mineBean.getData().getIncome());
            my_con_wop.setText(mineBean.getData().getKintegral());
            con_my_con_balance.setText(mineBean.getData().getAvailable_money());
            con_my_con_goback.setText(mineBean.getData().getYreturnmoney());
            uid_tv.setText("ID:"+mineBean.getData().getUid());
            user_name_tv.setText(mineBean.getData().getNickname());
          // setImage(getActivity(), profile_image, avater);
            NineGridView.getImageLoader().onDisplayImage(getActivity(),profile_image,avater);
        } else if ("103".equals(code)){
            intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        }else{

            TUtils.showShort(getActivity(), msg);
            intent = new Intent(getActivity(),MainActivity.class);
            intent.putExtra("comefrom","home");
            startActivity(intent);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //充值
            case R.id.recharge:
                intent = new Intent(getActivity(), RechargeActivity.class);
                startActivity(intent);
               /* intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);*/
                break;
            //提现
            case R.id.withdrawals:
               /* if(!"1".equals(b_status)){
                    intent = new Intent(getActivity(), BindingBankCardActivity.class);
                    startActivity(intent);
                }else if(!"1".equals(p_status)){
                    intent = new Intent(getActivity(), SetUpPwdActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(getActivity(), WithdrawalsActivity.class);
                    startActivity(intent);
                }*/
                if(!"1".equals(t_status)){
                    intent = new Intent(getActivity(), AuthenticationActivity.class);
                    startActivity(intent);
                }else if(!"1".equals(p_status)){
                    intent = new Intent(getActivity(), SetUpPwdActivity.class);
                    startActivity(intent);
                }else if(!"1".equals(b_status)){
                    intent = new Intent(getActivity(), BindingBankCardActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(getActivity(), WithdrawalsActivity.class);
                    startActivity(intent);
                }


                break;
            //安全设置
            case R.id.con_my_con_safe_normal_layout:
                intent = new Intent(getActivity(), SecurityActivity.class);
                startActivity(intent);
                break;
            //实名认证
            case R.id.con_my_con_name_normal_layout:
                if("1".equals(t_status)&&"1".equals(p_status)&&"1".equals(b_status)){
//                    con_my_con_name_normal.setText("已认证");
                }else{
                    if(!"1".equals(t_status)){
                        intent = new Intent(getActivity(), AuthenticationActivity.class);
                        startActivity(intent);
                    }else if(!"1".equals(p_status)){
                        intent = new Intent(getActivity(), SetUpPwdActivity.class);
                        startActivity(intent);
                    }else if(!"1".equals(b_status)){
                        intent = new Intent(getActivity(), BindingBankCardActivity.class);
                        startActivity(intent);
                    }
                }

                break;
            // 消费返回明细
            case R.id.fanhui_mingxi:
                intent = new Intent(getActivity(), FanHuiMingXiActivity.class);
                startActivity(intent);
                break;
            //我的银行卡
            case R.id.my_bank_card:
                intent = new Intent(getActivity(), MyBankCardActivity.class);
                startActivity(intent);
                break;
                //我的收入查询
            case R.id.revenue_inquiry:
                intent = new Intent(getActivity(), QueryIncomeActivity.class);
                startActivity(intent);
                break;
            //我的会员
            case R.id.on_line:
                intent = new Intent(getActivity(), OnLineActivity.class);
                startActivity(intent);
                break;
            //我的消费查询
            case R.id.consumption:
                intent = new Intent(getActivity(), ConsumptionActivity.class);
                startActivity(intent);
                break;
            //账户流水
            case R.id.account:
                intent = new Intent(getActivity(), RevenueInquiryActivity.class);
                startActivity(intent);
                break;
            //我的商户
            case R.id.my_merchant:
                intent = new Intent(getActivity(), MyMerchantActivity.class);
                startActivity(intent);
                break;
            //设置
             case R.id.receiving_address:
                 intent = new Intent(getActivity(), SettingActivity.class);
                 startActivity(intent);
                break;
            case R.id.profile_image:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
