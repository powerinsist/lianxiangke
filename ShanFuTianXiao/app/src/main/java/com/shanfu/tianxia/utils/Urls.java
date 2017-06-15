package com.shanfu.tianxia.utils;

/**
 * 接口地址
 * Created by qing on 2017/3/1.
 */
public class Urls {

    //static final String api_url = "https://api.xiaokang100.com/Api/";
    static final String api_url = "https://api.lianxiangke.cn/Api/";
    static final String shop_url = "https://api.lianxiangke.cn/Shop/";

//    https://api.lianxiangke.cn/Shop/Shopclass/getexchangeimage
    /**
     * 兑换专区
     */
    //首页轮播图
    public static final String zone_banner = shop_url+"Shopclass/getexchangeimage";
    //管理收货地址
    public static final String adress_manager = shop_url+"Address/address_list";
    //添加收货地址
    public static final String add_adress = shop_url+"Address/address_add";
    //地址修改接口 https://api.lianxiangke.cn/Shop/Address/address_save
    public static final String adress_save = shop_url+"Address/address_save";
    //首页下部商品列表 https://api.lianxiangke.cn/Shop/Shopclass/goodslist
    public static final String goodslist = shop_url+"Shopclass/goodslist";
    //连连支付发送短信验证接口  https://api.lianxiangke.cn/Api/Lianlian/smssend
    public static final String smssend = api_url+"Lianlian/smssend";
    //连联支付点击下一步（短信验证接口）https://api.lianxiangke.cn/Api/Lianlian/smscheck
    public static final String smscheck = api_url+"Lianlian/smscheck";
    //连联支付短信验证后下一步（钱包开户接口）https://api.lianxiangke.cn/Api/Lianlian/openuser
    public static final String openuser = api_url+"Lianlian/openuser";
    //银行卡绑定短信验证接口 https://api.lianxiangke.cn/Api/Lianlian/bankcardopenauth
    public static final String bankcardopenauth = api_url+"Lianlian/bankcardopenauth";
    //银行卡绑卡验证接口 https://api.lianxiangke.cn/Api/Lianlian/bankcardauthverfy
    public static final String bankcardauthverfy = api_url+"Lianlian/bankcardauthverfy";
    //签约查询接口（我的银行卡信息页面）https://api.lianxiangke.cn/Api/Lianlian/userbankcard
    public static final String userbankcard = api_url+"Lianlian/userbankcard";
    //银行卡解绑接口  https://api.lianxiangke.cn/Api/Lianlian/bankcardunbind
    public static final String bankcarddubind = api_url+"Lianlian/bankcardunbind";
    //钱包用户查询接口 https://api.lianxiangke.cn/Api/Lianlian/singleuserquery
    public static final String singleuserquery = api_url+"Lianlian/singleuserquery";
    //绑定银行卡授权申请接口  https://api.lianxiangke.cn/Api/Lianlian/bankpaypwdset
    public static final String bankpaypwdset = api_url+"Lianlian/bankpaypwdset";
    //绑定银行卡授权验证接口  https://api.lianxiangke.cn/Api/Lianlian/paypwdverify
    public static final String paypwdverify = api_url+"Lianlian/paypwdverify";
    //钱包支付密码找回 https://api.lianxiangke.cn/Api/Lianlian/paypwdset
    public static final String paypwdset = api_url+"Lianlian/paypwdset";
    //钱包支付密码修改  https://api.lianxiangke.cn/Api/Lianlian/paypwdmodify
    public static final String paypwdmodify = api_url+"Lianlian/paypwdmodify";
    //用户银行卡列表  https://api.lianxiangke.cn/Api/Lianlian/banklianlian
    public static final String banklianlian = api_url+"Lianlian/banklianlian";
    //用户收支明细查询接口 https://api.lianxiangke.cn/Api/Lianlian/userpayment_self
    public static final String userpayment_self = api_url+"Lianlian/userpayment_self";
    //签约支付预处理接口(4.1银行卡支付)  https://api.lianxiangke.cn/Api/Lianlian/bankcardprepay
    public static final String bankcardprepay = api_url+"Lianlian/bankcardprepay";
    //签约支付预处理接口(4.2银行卡支付)  https://api.lianxiangke.cn/Api/Lianlian/bankcardprepay_two
    public static final String bankcardprepay_two = api_url+"Lianlian/bankcardprepay_two";
    //余额支付1 https://api.lianxiangke.cn/Api/Lianlian/balancepwdpay
    public static final String balancepwdpay = api_url+"Lianlian/balancepwdpay";
    //余额支付2 (第一步失败 第二步请求此接口) https://api.lianxiangke.cn/Api/Lianlian/balancepwdpay_two
    public static final String balancepwdpay_two = api_url+"Lianlian/balancepwdpay_two";
    //余额充值第一步(银行到余额)  https://api.lianxiangke.cn/Api/Lianlian/balance_recharge
    public static final String balance_recharge = api_url+"Lianlian/balance_recharge";
    //余额充值第二步（银行到余额）  https://api.lianxiangke.cn/Api/Lianlian/balance_recharge_two
    public static final String balabce_recharge_two = api_url+"Lianlian/balance_recharge_two";
    //用户银行卡提现申请  https://api.lianxiangke.cn/Api/Lianlian/cashoutcombineapply
    public static final String cashoutcombineapply = api_url+"Lianlian/cashoutcombineapply";
    //扫码转账收付款1--余额(会员之间) https://api.lianxiangke.cn/Api/Lianlian/myreceivables
    public static final String myreceivables = api_url+"Lianlian/myreceivables";
    //扫码转账收付款2--余额(会员之间)  https://api.lianxiangke.cn/Api/Lianlian/myreceivables_two
    public static final String myreceivables_two = api_url+"Lianlian/myreceivables_two";
    //扫码转账--银行卡支付1(会员之间) https://api.lianxiangke.cn/Api/Lianlian/bankcardpaymentone
    public static final String bankcardpaymentone = api_url+"Lianlian/bankcardpaymentone";
    //扫码转账--银行卡支付2(会员之间) https://api.lianxiangke.cn/Api/Lianlian/bankcardpaymenttwo
    public static final String bankcardpaymenttwo = api_url+"Lianlian/bankcardpaymenttwo";
    //连连支付用户信息 https://api.lianxiangke.cn/Api/Lianlian/getllinfo
    public static final String getllinfo = api_url+"Lianlian/getllinfo";
    //联享票红包领取 https://api.lianxiangke.cn/Api/Lianlian/Bonus
    public static final String bonus = api_url+"Lianlian/Bonus";
    //返现红包领取 https://api.lianxiangke.cn/Api/Lianlian/Bonus_redpacket
    public static final String Bonus_redpacket = api_url+"Lianlian/Bonus_redpacket";
    //消费返现 红包列表接口 https://api.lianxiangke.cn/Api/Lianlian/redpacket
    public static final String redpacket = api_url+"Lianlian/redpacket";
    //我的联享票 https://api.lianxiangke.cn/Shop/Myticket/ticktlist1
    public static final String ticktlist = shop_url+"Myticket/ticktlist1";
    //验证密码 https://api.lianxiangke.cn/Api/Lianlian/verify_password
    public static final String verify_password = api_url+"Lianlian/verify_password";
    //商铺推荐人领取 https://api.lianxiangke.cn/Api/Lianlian/shop_recommender
    public static final String shop_recommender = api_url+"Lianlian/shop_recommender";
    //用户基本信息修改接口  https://api.lianxiangke.cn/Api/Lianlian/modifyuserinfo
    public static final String modifyuserinfo = api_url+"Lianlian/modifyuserinfo";
    //联享票(红包)领取列表 https://api.lianxiangke.cn/Shop/Myticket/already_received
    public static final String already_received = shop_url+"Myticket/already_received";
    //银行卡绑卡接口  https://api.lianxiangke.cn/Api/Lianlian/bindbankuser
    public static final String bindbankuser = api_url+"Lianlian/bindbankuser";
    //获取银行卡绑卡接口  https://api.lianxiangke.cn/Api/Lianlian/getbindbankuser
    public static final String getbindbankuser = api_url+"Lianlian/getbindbankuser";















    //-----------------------------------------------------------------------------------------------------

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
    public static final String buyInfo = api_url+"My/buyInfo1";
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
