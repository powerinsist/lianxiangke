package com.shanfu.tianxia.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.shanfu.tianxia.bean.GetLLInfoBean;
import com.shanfu.tianxia.bean.LoginEvent;
import com.shanfu.tianxia.bean.LogoutEvent;
import com.shanfu.tianxia.bean.MineBean;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.bean.SingleUserQueryBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.ui.CheckForTheDetailActivity;
import com.shanfu.tianxia.ui.ConsumptionActivity;
import com.shanfu.tianxia.ui.DropShipingActivity;
import com.shanfu.tianxia.ui.ExchangeZoneActivity;
import com.shanfu.tianxia.ui.HelpCenterActivity;
import com.shanfu.tianxia.ui.KeFuJiaXinActivity;
import com.shanfu.tianxia.ui.LoginActivity;
import com.shanfu.tianxia.ui.MerchantsInActivity;
import com.shanfu.tianxia.ui.MyLianxpActivity;
import com.shanfu.tianxia.ui.MyMerchantActivity;
import com.shanfu.tianxia.ui.MyWalletActivity;
import com.shanfu.tianxia.ui.MyWalletBalanceActivity;
import com.shanfu.tianxia.ui.NiceZoneActivity;
import com.shanfu.tianxia.ui.OnLineActivity;
import com.shanfu.tianxia.ui.PlatformAnnouncementActivity;
import com.shanfu.tianxia.ui.QueryIncomeActivity;
import com.shanfu.tianxia.ui.RealNameFirstActivity;
import com.shanfu.tianxia.ui.SecurityActivity;
import com.shanfu.tianxia.ui.SelectRedPayPacketActivity;
import com.shanfu.tianxia.ui.SettingActivity;
import com.shanfu.tianxia.ui.ShippedActivity;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private FragmentTabHost mTabHost;
    private Intent intent;
    //头像id
    @Bind(R.id.profile_image)
    CircleImageView profile_image;
    //等级id
    @Bind(R.id.dengji)
    ImageView dengji;
    //姓名id
    @Bind(R.id.user_name_tv)
    TextView user_name_tv;
    //uid
    @Bind(R.id.uid_tv)
    TextView uid_tv;
    //个人设置id
    @Bind(R.id.receiving_address)
    RelativeLayout receiving_address;
    //账户余额id
    @Bind(R.id.con_my_con_balance)
    TextView con_my_con_balance;
    //    //剩余积分id
//    @Bind(R.id.my_con_wop)
//    TextView my_con_wop;
    //市场总收入id
    @Bind(R.id.my_con)
    TextView my_con;
    //累计返还id
    @Bind(R.id.con_my_con_goback)
    TextView con_my_con_goback;
    //我的钱包id
    @Bind(R.id.mine_wallet)
    LinearLayout mine_wallet;
    //我的联享票id
    @Bind(R.id.mine_lianxiangpiao)
    LinearLayout mine_lianxiangpiao;
    //兑换专区id
    @Bind(R.id.mine_duihuan)
    LinearLayout mine_duihuan;
    /*//兑换明细id
    @Bind(R.id.mine_duihuanmingxi)
    RelativeLayout mine_duihuanmingxi;
    //查看全部id
    @Bind(R.id.mine_selectduihuan)
    RelativeLayout mine_selectduihuan;
    //待发货id
    @Bind(R.id.mine_waitcargo)
    RelativeLayout mine_waitcargo;
    //已发货id
    @Bind(R.id.mine_alredycargo)
    RelativeLayout mine_alredycargo;*/
    //我的积分id
//    @Bind(R.id.mine_my_count)
//    RelativeLayout mine_my_count;
    //消费明细id
    @Bind(R.id.mine_consumption)
    RelativeLayout mine_consumption;
    //市场收入id
//    @Bind(R.id.revenue_inquiry)
//    RelativeLayout revenue_inquiry;
    //商户入驻id
    @Bind(R.id.mine_shopinto)
    RelativeLayout mine_shopinto;
    //我的商户id
    @Bind(R.id.my_merchant)
    RelativeLayout my_merchant;
    //我的会员id
    @Bind(R.id.mine_on_line)
    RelativeLayout mine_on_line;
    //安全设置id
    @Bind(R.id.mine_safe_normal)
    RelativeLayout mine_safe_normal;
    //客服中心id
    @Bind(R.id.mine_kefucenter)
    RelativeLayout mine_kefucenter;
    @Bind(R.id.my_walletbalance_rl)
    RelativeLayout my_walletbalance_rl;
    @Bind(R.id.my_con_rl)
    RelativeLayout my_con_rl;
    @Bind(R.id.con_my_con_goback_rl)
    RelativeLayout con_my_con_goback_rl;
    //平台公告
    @Bind(R.id.platform_announcement)
    RelativeLayout platform_announcement_rl;
    @Bind(R.id.help_center)
    RelativeLayout help_center_rl;

    private View view;


    @Override
    public View createView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void init() {
        profile_image.setOnClickListener(this);
        dengji.setOnClickListener(this);
        receiving_address.setOnClickListener(this);
        mine_wallet.setOnClickListener(this);
        mine_lianxiangpiao.setOnClickListener(this);
        mine_duihuan.setOnClickListener(this);
//        mine_selectduihuan.setOnClickListener(this);
//        mine_waitcargo.setOnClickListener(this);
//        mine_alredycargo.setOnClickListener(this);
//        mine_my_count.setOnClickListener(this);
        mine_consumption.setOnClickListener(this);
//        revenue_inquiry.setOnClickListener(this);
        mine_shopinto.setOnClickListener(this);
        my_merchant.setOnClickListener(this);
        mine_on_line.setOnClickListener(this);
        mine_safe_normal.setOnClickListener(this);
        mine_kefucenter.setOnClickListener(this);
        my_walletbalance_rl.setOnClickListener(this);
        my_con_rl.setOnClickListener(this);
        con_my_con_goback_rl.setOnClickListener(this);
        platform_announcement_rl.setOnClickListener(this);
        help_center_rl.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        boolean request = SPUtils.getInstance().getBoolean("request",true);
//        requestInfo();
//        requestData1();
        requestData();
        requestData1();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //退出
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent event) {
        String user_id = SPUtils.getInstance().getString("user_id", "");
        String uid = SPUtils.getInstance().getString("uid", "");
        Log.e("LOG","-----user_id------"+user_id);
        Log.e("LOG","---------uid------------"+uid);
    }
    //登陆
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LoginEvent event) {
        requestInfo();
    }

    /**
     * 收到广播的时候调用一下这个方法就行了
     */
    private void requestInfo() {

        Log.e("MineFragment", "----1--requestInfo------>>>>>>>");

        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid","");
            //重新登录后这些参数有么有问题？
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            Log.e("LOG","-------info:uid-------"+uid);
            params.put("uid",uid);
            OkGo.post(Urls.getllinfo)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<GetLLInfoBean>(getActivity()) {
                        @Override
                        public void onSuccess(GetLLInfoBean getLLInfoBean, Call call, Response response) {
                            Log.e("MineFragment", "--2----requestInfo------>>>>>>>");
                            //1.请求完数据成功
                            //看看这有啥问题？

                            decodeInfo(getLLInfoBean);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            Log.e("MineFragment", "----22--requestInfo------>>>>>>>");
                            TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeInfo(GetLLInfoBean getLLInfoBean) {
        String code = getLLInfoBean.getErr_code();//这个是错误码吧
        String msg = getLLInfoBean.getErr_msg();
//这个code 不是200了
        //你得跟踪一下 为什么不是200
        if("200".equals(code)){
            //登陆走到这了
            Log.e("MineFragment", "----3--requestInfo------>>>>>>>");
            //2.TODO
            String user_id = getLLInfoBean.getData().getData().getUser_id();
            String name_user = getLLInfoBean.getData().getData().getName_user();
            SPUtils.getInstance().putString("name_user", name_user);
            SPUtils.getInstance().putString("user_id", user_id);

//            requestData();

            requestData1();
        }else {
            //没有登录 走到这了
            //直接把账户余额设置为0，或者不显示就行了
            //重新登录账号后也走到这了
            con_my_con_balance.setText("0.00");
            String user_id = SPUtils.getInstance().getString("user_id", "");
            Log.e("MineFragment", "--4----requestInfo------>>>>>>>");
            Log.e("LOG","-----info:!!!!200:user_id--------"+user_id);
//            requestData();
        }
    }

    @Override
    public void onPause() {
        OkGo.getInstance().cancelTag(this);
        super.onPause();
    }

    private void requestData() {
        try {Log.e("MineFragment", "----5--requestInfo------>>>>>>>");

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
            Log.e("LOG","66666666666666666"+uid);
            OkGo.post(Urls.index)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<MineBean>(getActivity()) {
                        @Override
                        public void onSuccess(MineBean mineBean, Call call, Response response) {
                            Log.e("MineFragment", "----55--requestInfo------>>>>>>>");
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

    private void decodeData(MineBean mineBean) {
        Log.e("MineFragment", "----6--requestInfo------>>>>>>>");
        String code = mineBean.getErr_code();
        String msg = mineBean.getErr_msg();
        Log.e("LOG","decodeData-------"+code);
        if ("200".equals(code)) {
            avater = mineBean.getData().getAvater();
            SPUtils.getInstance().putString("ptoken", mineBean.getData().getPtoken());
            SPUtils.getInstance().putString("avater", avater);
            SPUtils.getInstance().putString("nickname", mineBean.getData().getNickname());
            SPUtils.getInstance().putString("uid", mineBean.getData().getUid());
            SPUtils.getInstance().putString("bankcard", mineBean.getData().getBankcard());
            String nickname = mineBean.getData().getNickname();

            Log.e("LOG", "------7------" + nickname);

            level = mineBean.getData().getLevel();
            String user_id = SPUtils.getInstance().getString("user_id", "");
            Log.e("LOG",user_id);
            if (user_id != null){
                if ("1".equals(level)) {
                    dengji.setImageResource(R.mipmap.dengji1);
                } else if ("2".equals(level)) {
                    dengji.setImageResource(R.mipmap.dengji2);
                } else if ("3".equals(level)) {
                    dengji.setImageResource(R.mipmap.dengji3);
                }
            }

            //账户余额
//            .setText(mineBean.getData().getAvailable_money());
            //剩余积分
//            my_con_wop.setText(mineBean.getData().getKintegral());
            //市场总收入
            my_con.setText(mineBean.getData().getAccumulated_income());
            //累计返还
            con_my_con_goback.setText(mineBean.getData().getYreturnmoney());
            //用户姓名
//            String name_user = SPUtils.getInstance().getString("name_user", "");
            String nickname1 = SPUtils.getInstance().getString("nickname1", "");
            if (nickname1 == ""){
                user_name_tv.setText(nickname);
            }
            else if (nickname != null & nickname1 != ""){
                user_name_tv.setText(nickname1);
            }
//            user_name_tv.setText(nickname);



//            if (TextUtils.isEmpty(nicheng_name)){
//                user_name_tv.setText(name_user);
//            }else
//            user_name_tv.setText(nicheng_name);
            //ID
            uid_tv.setText("ID:" + mineBean.getData().getUid());
            NineGridView.getImageLoader().onDisplayImage(getActivity(), profile_image, avater);

            /**
             * 设备唯一登陆
             */
            requestOnly();

        } else if ("103".equals(code)) {
            Log.e("MineFragment", "---8---requestInfo------>>>>>>>");
            intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            Log.e("MineFragment", "---9---requestInfo------>>>>>>>");
            TUtils.showShort(getActivity(), msg);
            intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("comefrom", "home");
            startActivity(intent);
        }
    }

    private void requestOnly() {
        String time = DateUtils.getLinuxTime();
        String token = MD5Utils.MD5(Constants.appKey + time);
        String uid = SPUtils.getInstance().getString("uid", "");
        String logintoken = SPUtils.getInstance().getString("logintoken", "");
        Log.e("LOG",logintoken);

        HttpParams params = new HttpParams();
        params.put("time", time);
        params.put("token", token);
        params.put("uid", uid);
        params.put("logintoken",logintoken);

        OkGo.post(Urls.loginverification)
                .tag(this)
                .params(params)
                .execute(new DialogCallback<RsultBean>(getActivity()) {
                    @Override
                    public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                        decodeOnly(rsultBean);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        TUtils.showShort(getActivity(), "数据获取失败，请检查网络后重试");
                    }
                });
    }

    private void decodeOnly(RsultBean rsultBean) {
        String err_code = rsultBean.getErr_code();
        String err_msg = rsultBean.getErr_msg();
        if (err_code.equals("100")){
            Log.e("LOG","Mine-----------------");
            showOnlyDialog();
        }
    }

    private void showOnlyDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();//创建对象
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_only_login_dialog,null);//自定义布局
        TextView query_tv = (TextView) dialogView.findViewById(R.id.query_tv);
        //把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        dialog.setView(dialogView,0,0,0,0);
        //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();//得到当前显示设备的宽度，单位是像素
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();//得到这个dialog界面的参数对象
//        params.width = width-(width/3);//设置dialog的界面宽度
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;//设置dialog的界面宽度
        params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;//设置dialog高度为包裹内容
        params.gravity = Gravity.CENTER;//设置dialog的重心
        dialog.getWindow().setAttributes(params);//最后把这个参数对象设置进去，即与dialog绑定
        query_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTabHost.setCurrentTab(0);
                dialog.dismiss();
                intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像->设置中心
            case R.id.profile_image:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            //设置
            case R.id.receiving_address:
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            //我的钱包
            case R.id.mine_wallet:
                String user_id = SPUtils.getInstance().getString("user_id", "");

                if (user_id != ""){
                    intent = new Intent(getActivity(), MyWalletActivity.class);
                    startActivity(intent);
                }else {
                    showDialog();
                }

                break;
            //我的联享票
            case R.id.mine_lianxiangpiao:
                String user_id1 = SPUtils.getInstance().getString("user_id", "");

                if (user_id1 != ""){
                    intent = new Intent(getActivity(), MyLianxpActivity.class);
                    startActivity(intent);
                }else {
                    showDialog();
                }
                break;
            //兑换专区
            case R.id.mine_duihuan:
//                TUtils.showShort(getActivity(), "即将开放，敬请期待");
//                intent = new Intent(getActivity(), ExchangeZoneActivity.class);
                intent = new Intent(getActivity(), NiceZoneActivity.class);
                startActivity(intent);
                break;
            //查看兑换明细
//            case R.id.mine_selectduihuan:
////                TUtils.showShort(getActivity(), "即将开放，敬请期待");
//                intent = new Intent(getActivity(), CheckForTheDetailActivity.class);
//                startActivity(intent);
//                break;
            //待发货
//            case R.id.mine_waitcargo:
////                TUtils.showShort(getActivity(), "即将开放，敬请期待");
////                intent = new Intent(getActivity(), TraceActivity.class);
//                intent = new Intent(getActivity(), DropShipingActivity.class);
//                startActivity(intent);
//                break;
            //已发货
//            case R.id.mine_alredycargo:
////                TUtils.showShort(getActivity(), "即将开放，敬请期待");
//                intent = new Intent(getActivity(), ShippedActivity.class);
//                startActivity(intent);
//                break;
            // 账户零钱
//            case R.id.mine_my_count:
//                String user_id2 = SPUtils.getInstance().getString("user_id", "");
//
//                if (user_id2 != ""){
//                    intent = new Intent(getActivity(), MyWalletBalanceActivity.class);
//                    startActivity(intent);
//                }else {
//                    showDialog();
//                }
//                break;
            //消费明细
            case R.id.mine_consumption:
                intent = new Intent(getActivity(), ConsumptionActivity.class);
                startActivity(intent);
                break;
            //市场收入
//            case R.id.revenue_inquiry:

            //商户入驻
            case R.id.mine_shopinto:
                intent = new Intent(getActivity(), MerchantsInActivity.class);
                startActivity(intent);
                break;
            //我的商户
            case R.id.my_merchant:
                intent = new Intent(getActivity(), MyMerchantActivity.class);
                startActivity(intent);
                break;
            //我的会员
            case R.id.mine_on_line:
                    intent = new Intent(getActivity(), OnLineActivity.class);
                    startActivity(intent);
                break;
            //安全设置
            case R.id.mine_safe_normal:
                String user_id4 = SPUtils.getInstance().getString("user_id", "");

                if (user_id4 != ""){
                    intent = new Intent(getActivity(), SecurityActivity.class);
                    startActivity(intent);
                }else {
                    showDialog();
                }
                break;
            //客服中心
            case R.id.mine_kefucenter:
                TUtils.showShort(getActivity(), "即将开放，敬请期待");
//                startActivity(new Intent(getActivity(), JXInitActivity.class));
                break;
            //我的余额
            case R.id.my_walletbalance_rl:
                String user_id7 = SPUtils.getInstance().getString("user_id", "");

                if (user_id7 != ""){
                    intent = new Intent(getActivity(), MyWalletBalanceActivity.class);
                    startActivity(intent);
                }else {
                    showDialog();
                }
                break;
            //市场总收入
            case R.id.my_con_rl:
                String user_id3 = SPUtils.getInstance().getString("user_id", "");

                if (user_id3 != ""){
                    intent = new Intent(getActivity(), QueryIncomeActivity.class);
                    startActivity(intent);
                }else {
                    showDialog();
                }
                break;
            //累计奖励
            case R.id.con_my_con_goback_rl:
                String user_id2 = SPUtils.getInstance().getString("user_id", "");
                if (user_id2 != ""){
                    intent = new Intent(getActivity(), SelectRedPayPacketActivity.class);
                    startActivity(intent);
                }else {
                    showDialog();
                }
                break;
            //帮助中心
            case R.id.help_center:
                intent = new Intent(getActivity(), HelpCenterActivity.class);
                startActivity(intent);
                break;
            //平台公告
            case R.id.platform_announcement:
                intent = new Intent(getActivity(),PlatformAnnouncementActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void  requestData1() {
        Log.e("MineFragment", "--xxx1----requestInfo------>>>>>>>");
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String uid = SPUtils.getInstance().getString("uid", "");
            String user_id = SPUtils.getInstance().getString("user_id", "");

            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("uid", uid);
            params.put("user_id", user_id);

            OkGo.post(Urls.singleuserquery)
                    .tag(this)
                    .params(params)
                    .execute(new DialogCallback<SingleUserQueryBean>(getActivity()) {
                        @Override
                        public void onSuccess(SingleUserQueryBean singleUserQueryBean, Call call, Response response) {
                            //3.TODO
                            decodeData(singleUserQueryBean);
                            OkGo.getInstance().debug("OkHttpUtils");

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

    //4.
    private void decodeData(SingleUserQueryBean singleUserQueryBean) {
        Log.e("MineFragment", "--xxxx2----requestInfo------>>>>>>>");
        String err_code = singleUserQueryBean.getErr_code();
        String err_msg = singleUserQueryBean.getErr_msg();
        String ret_code = singleUserQueryBean.getData().getData().getRet_code();
        String ret_msg = singleUserQueryBean.getData().getData().getRet_msg();

        //到这为止
        if ("0000".equals(ret_code)) {
            Log.e("MineFragment", "--x3----requestInfo------>>>>>>>");
            String balance = singleUserQueryBean.getData().getData().getBalance();
//            balance_tv.setText(balance);
            //账户余额
            con_my_con_balance.setText(balance);
        } else if ("1004".equals(ret_code)){Log.e("MineFragment", "----xxxx4--requestInfo------>>>>>>>");

            String uid = singleUserQueryBean.getData().getUid();
            if (uid != ""){
                showDialog();
            }
        }else {
//            TUtils.showShort(getActivity(),ret_msg);
        }
    }

    private void showDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();//创建对象
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_real_name_popup,null);//自定义布局
        TextView shouye_tv = (TextView) dialogView.findViewById(R.id.shouye_tv);
        TextView real_name_tv = (TextView) dialogView.findViewById(R.id.real_name_tv);
        //把自定义的布局设置到dialog中，注意，布局设置一定要在show之前。从第二个参数分别填充内容与边框之间左、上、右、下、的像素
        dialog.setView(dialogView,0,0,0,0);
        //一定要先show出来再设置dialog的参数，不然就不会改变dialog的大小了
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();//得到当前显示设备的宽度，单位是像素
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();//得到这个dialog界面的参数对象
        params.width = width-(width/3);//设置dialog的界面宽度
//        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;//设置dialog的界面宽度
        params.height =  ViewGroup.LayoutParams.WRAP_CONTENT;//设置dialog高度为包裹内容
        params.gravity = Gravity.CENTER;//设置dialog的重心
        dialog.getWindow().setAttributes(params);//最后把这个参数对象设置进去，即与dialog绑定
        shouye_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mTabHost.setCurrentTab(0);
                dialog.dismiss();
            }
        });
        real_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), RealNameFirstActivity.class);
                startActivity(intent);
            }
        });
    }

}
