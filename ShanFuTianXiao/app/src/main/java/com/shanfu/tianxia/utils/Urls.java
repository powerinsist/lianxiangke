package com.shanfu.tianxia.utils;

/**
 * 接口地址
 * Created by qing on 2017/3/1.
 */
public class Urls {

    //static final String api_url = "https://api.xiaokang100.com/Api/";
    static final String api_url = "https://api.lianxiangke.cn/Api/";

    //忘记密码的phone值
    public  static final String forget_phone = api_url+"Login/Withdrawalsuidyu";
    //我的提现（验证码判断）
    public  static final String tixian_yanzheng = api_url+"Login/Withdrawalscodeyu";
    //我的提现
    public static final String my_income = api_url+"My/details";
    //修改提现密码（发送验证码）
    public static final String tixian_code = api_url+"Login/modifyithdrawals";
    //修改提现密码（确认）
    public static final String tixian_requst = api_url+"Login/repass";
    //热门城市列表
    public static final String home_hotcity = api_url+"Home/hotcity";
    //所有城市列表
    public static final String allcity = api_url+"Home/allcity";
    //首页banner
    public static final String home_banner = api_url+"Home/banner";
    //发送验证码（注册）
    public static final String register_code = api_url+"login/sendcodeByregister";
    //发送验证码（提现）
    public static final String withDrawCode = api_url+"login/withDrawCode";
    //发送验证码（注册）
    public static final String sendcodeByfoundpassword = api_url+"login/sendcodeByfoundpassword";
    //登录
    public static final String login = api_url+"login/login";
    //注册
    public static final String register = api_url+"login/register";
    //首页中部广告位
    public static final String middlead = api_url+"Home/middlead";
    //首页下部商品热卖
    public static final String hotshops = api_url+"Home/hotshops";
    //首页分类
    public static final String classifyinfo = api_url+"Home/classifyinfo";
    //修改登录密码
    public static final String modifypassword = api_url+"My/modifypassword";
    //修改支付密码
    public static final String updatePaypassword = api_url+"My/updatePaypassword";
    //实名认证
    public static final String setTruename = api_url+"My/setTruename";
    //设置交易密码
    public static final String setPaypassword = api_url+"My/setPaypassword";
    //绑定银行卡
    public static final String bindBankCard = api_url+"My/bindBankCard";
    //设置登录密码（找回密码）
    public static final String setpasswordByreset = api_url+"login/setpasswordByreset";
    //我的银行卡
    public static final String bankInfo = api_url+"My/bankInfo";
    //账户流水
    public static final String accountFlow = api_url+"My/accountFlow";
    //获取店家详情
    public static final String shopDetail = api_url+"home/shopDetail";
    //评论商家
    public static final String addComment = api_url+"comment/addComment";
    //评论商家列表
    public static final String loadCommnetList = api_url+"comment/loadCommnetList";
    //商家搜索分类
    public static final String initSearch = api_url+"home/initSearch";
    //商家搜索
    public static final String Search = api_url+"home/Search";
    //支付  /Api/My/pay
    //public static final String Pay = "http://api.lianxiangke.cn/pay/h5/umpay/withdraw.php";
    public static final String Pay = api_url+"My/pay";
    //上传图片
    public static final String UploadPic = api_url+"Comment/uploadPic";
    //我的
    public static final String index = api_url+"My/index";
    //测试
    public static final String test = api_url+"index/test";
//    //我的收入
//    public static final String queryIncome = api_url+"My/queryIncome";
//    //我的收入
//    public static final String queryIncome = api_url+"Myincome/queryIncome";
    //我的收入
    public static final String queryIncome = api_url+"My/queryIncome2";
    //我的推广
    public static final String mySpread = api_url+"My/mySpread";
    //我的推广
    public static final String buyInfo = api_url+"My/buyInfo";
    //我的商户
    public static final String recomendShop = api_url+"My/recomendShop";
    //上传头像
    public static final String setAvatar = api_url+"My/setAvatar";
    //设置昵称
    public static final String setNickName = api_url+"My/setNickName";
    //分类店铺列表
    public static final String listShops = api_url+"home/listShops";
    //我的
    //public static final String index = api_url+"My/index";
    //消费返还明细
    public static final String remissionDetail = api_url+"My/remissionDetail";
    //提现
    public static final String withDraw = api_url+"my/withDraw";
    //提现
    public static final String modifyLoginPasswordSmsCode = api_url+"login/modifyLoginPasswordSmsCode";


//    https://api.lianxiangke.cn/Api/My/remissionDetail



}
