package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/27.
 */

public class BalanceRechargeBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"dt_order":"20170527153008","money_order":"0.2","no_order":"20170527153008142423001271392741","oid_partner":"201705101001719503","oid_paybill":"2017052787811876","ret_code":"8888","ret_msg":"需要短信验证","sign":"CihsRd5X2XtyCIPiUKb80rtcTIuAmK9XVzfoQmrpXYBNCMb+poCpfEAZmlAaeiBucjHoRJsJNfB0PEbFoT7sQoecGYiJaUwXdgH+TGbc1ptye8bDIHAnHrNhnr5KPrnuiO4PiWi2ZUIVT7v2c9++WT6rj9qETWm77Chu1Wu/nmo=","sign_type":"RSA","sms_flag":"1","token":"E42EBDD7F30AE911E6162F2A38CA553E"},"para":"{\"oid_partner\":\"201705101001719503\",\"sign_type\":\"RSA\",\"user_id\":\"15373550655\",\"busi_partner\":\"110001\",\"api_version\":\"1.1\",\"no_order\":\"20170527153008142423001271392741\",\"dt_order\":\"20170527153008\",\"name_goods\":\"\\u8d26\\u6237\\u4f59\\u989d\\u5145\\u503c\",\"money_order\":\"0.2\",\"notify_url\":\"https:\\/\\/api.lianxiangke.cn\\/Api\\/Notifyurl\",\"risk_item\":\"{\\\"frms_client_chnl\\\":\\\"10\\\",\\\"frms_ip_addr\\\":\\\"183.172.12.108\\\",\\\"frms_imei\\\":\\\"423198429310234\\\",\\\"frms_mechine_id\\\":\\\"423198429310234\\\",\\\"frms_mac_addr\\\":\\\"7C:7D:3D:B8:F1:A9\\\"}\",\"pay_type\":\"2\",\"card_no\":\"6222020403032192433\",\"bind_mob\":\"15373550655\",\"no_agree\":\"2017052430033004\",\"pwd_pay\":\"AzZH7JTGGhSZ3Pn8Zv5i6ZJTzxTMLMgCm0pcvqWiTJfJqR5baKlL468xOgs54UU3Je5m2ZGmLGZFZjEZZzo6Yjn\\/h696g4CtNfwUR+U2MKV\\/tjRqTyvs0wQfNAeOq3sX\\/4Yo877FrMtL2uRkgE1m5djjmsWFXDVbDAovJ9NnOP0=\"}","ptoken":"f7550ca91e5e0f7eb418a7f7a3a19632","uid":"10012998"}
     */

    private String err_code;
    private String err_msg;
    private DataBeanX data;

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"dt_order":"20170527153008","money_order":"0.2","no_order":"20170527153008142423001271392741","oid_partner":"201705101001719503","oid_paybill":"2017052787811876","ret_code":"8888","ret_msg":"需要短信验证","sign":"CihsRd5X2XtyCIPiUKb80rtcTIuAmK9XVzfoQmrpXYBNCMb+poCpfEAZmlAaeiBucjHoRJsJNfB0PEbFoT7sQoecGYiJaUwXdgH+TGbc1ptye8bDIHAnHrNhnr5KPrnuiO4PiWi2ZUIVT7v2c9++WT6rj9qETWm77Chu1Wu/nmo=","sign_type":"RSA","sms_flag":"1","token":"E42EBDD7F30AE911E6162F2A38CA553E"}
         * para : {"oid_partner":"201705101001719503","sign_type":"RSA","user_id":"15373550655","busi_partner":"110001","api_version":"1.1","no_order":"20170527153008142423001271392741","dt_order":"20170527153008","name_goods":"\u8d26\u6237\u4f59\u989d\u5145\u503c","money_order":"0.2","notify_url":"https:\/\/api.lianxiangke.cn\/Api\/Notifyurl","risk_item":"{\"frms_client_chnl\":\"10\",\"frms_ip_addr\":\"183.172.12.108\",\"frms_imei\":\"423198429310234\",\"frms_mechine_id\":\"423198429310234\",\"frms_mac_addr\":\"7C:7D:3D:B8:F1:A9\"}","pay_type":"2","card_no":"6222020403032192433","bind_mob":"15373550655","no_agree":"2017052430033004","pwd_pay":"AzZH7JTGGhSZ3Pn8Zv5i6ZJTzxTMLMgCm0pcvqWiTJfJqR5baKlL468xOgs54UU3Je5m2ZGmLGZFZjEZZzo6Yjn\/h696g4CtNfwUR+U2MKV\/tjRqTyvs0wQfNAeOq3sX\/4Yo877FrMtL2uRkgE1m5djjmsWFXDVbDAovJ9NnOP0="}
         * ptoken : f7550ca91e5e0f7eb418a7f7a3a19632
         * uid : 10012998
         */

        private DataBean data;
        private String para;
        private String ptoken;
        private String uid;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getPara() {
            return para;
        }

        public void setPara(String para) {
            this.para = para;
        }

        public String getPtoken() {
            return ptoken;
        }

        public void setPtoken(String ptoken) {
            this.ptoken = ptoken;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public static class DataBean {
            /**
             * dt_order : 20170527153008
             * money_order : 0.2
             * no_order : 20170527153008142423001271392741
             * oid_partner : 201705101001719503
             * oid_paybill : 2017052787811876
             * ret_code : 8888
             * ret_msg : 需要短信验证
             * sign : CihsRd5X2XtyCIPiUKb80rtcTIuAmK9XVzfoQmrpXYBNCMb+poCpfEAZmlAaeiBucjHoRJsJNfB0PEbFoT7sQoecGYiJaUwXdgH+TGbc1ptye8bDIHAnHrNhnr5KPrnuiO4PiWi2ZUIVT7v2c9++WT6rj9qETWm77Chu1Wu/nmo=
             * sign_type : RSA
             * sms_flag : 1
             * token : E42EBDD7F30AE911E6162F2A38CA553E
             */

            private String dt_order;
            private String money_order;
            private String no_order;
            private String oid_partner;
            private String oid_paybill;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String sms_flag;
            private String token;

            public String getDt_order() {
                return dt_order;
            }

            public void setDt_order(String dt_order) {
                this.dt_order = dt_order;
            }

            public String getMoney_order() {
                return money_order;
            }

            public void setMoney_order(String money_order) {
                this.money_order = money_order;
            }

            public String getNo_order() {
                return no_order;
            }

            public void setNo_order(String no_order) {
                this.no_order = no_order;
            }

            public String getOid_partner() {
                return oid_partner;
            }

            public void setOid_partner(String oid_partner) {
                this.oid_partner = oid_partner;
            }

            public String getOid_paybill() {
                return oid_paybill;
            }

            public void setOid_paybill(String oid_paybill) {
                this.oid_paybill = oid_paybill;
            }

            public String getRet_code() {
                return ret_code;
            }

            public void setRet_code(String ret_code) {
                this.ret_code = ret_code;
            }

            public String getRet_msg() {
                return ret_msg;
            }

            public void setRet_msg(String ret_msg) {
                this.ret_msg = ret_msg;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getSms_flag() {
                return sms_flag;
            }

            public void setSms_flag(String sms_flag) {
                this.sms_flag = sms_flag;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
