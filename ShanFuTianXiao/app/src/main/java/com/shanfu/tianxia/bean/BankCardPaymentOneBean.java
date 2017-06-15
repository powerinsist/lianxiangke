package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/6/1.
 */

public class BankCardPaymentOneBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"dt_order":"20170601163643","money_order":"0.50","no_order":"20170601163643138234001494544378","oid_partner":"201705101001719503","oid_paybill":"2017060195290900","ret_code":"8888","ret_msg":"需要短信验证","sign":"RO2ZWFbcsnwarI9Zh/VSQAXCm0AXEmU5Dtdomv5+bwip0A4AvffPMg6Xzt3g9T2ID7nUZT/HDcD4G0lv0c9XqSSPc3ekRG+9eu5etwpnd8mCtZViy2siForJigNXBDFnuNVK7+iCdgnAJyWD+JobuPGTKpJEqG1XpItaF7xUuoQ=","sign_type":"RSA","sms_flag":"1","token":"5651C7C6D30484AA636B6D816ECE6E78"},"para":"{\"oid_partner\":\"201705101001719503\",\"sign_type\":\"RSA\",\"user_id\":\"15373550655\",\"busi_partner\":101001,\"api_version\":\"1.1\",\"no_order\":\"20170601163643138234001494544378\",\"dt_order\":\"20170601163643\",\"name_goods\":\"\\u8ff7\\u4f60\\u5546\\u54c1\",\"money_order\":\"0.50\",\"notify_url\":\"https:\\/\\/api.lianxiangke.cn\\/Api\\/Notifyurl\",\"risk_item\":\"{\\\"frms_client_chnl\\\":\\\"10\\\",\\\"frms_ip_addr\\\":\\\"183.172.12.108\\\",\\\"frms_imei\\\":\\\"423198429310234\\\",\\\"frms_mechine_id\\\":\\\"423198429310234\\\",\\\"frms_mac_addr\\\":\\\"7C:7D:3D:B8:F1:A9\\\"}\",\"pay_type\":2,\"card_no\":\"6222020403032192433\",\"bind_mob\":\"15373550655\",\"no_agree\":\"2017060134793542\",\"pwd_pay\":\"MC+o7IUNTkqwBfYAVdZq0hnsMsCWQhpUCV9I2IeZOPhl6Qu0aNXqblHhSmtxk0JDLxNoa8sYJ9rdUBbtWnk2e0kOH4zYN51cC16Hyjb4WXLIhxJtiFbgsnmkgN1CRJ+naCofgOZHmZSAuLjeIZqGPC61WGk8EEInydewfn3kv5w=\",\"col_userid\":\"15321208718\"}","token":"5651C7C6D30484AA636B6D816ECE6E78","ptoken":"8901a245939f031586e5ef912f099e70","uid":"10010837"}
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
         * data : {"dt_order":"20170601163643","money_order":"0.50","no_order":"20170601163643138234001494544378","oid_partner":"201705101001719503","oid_paybill":"2017060195290900","ret_code":"8888","ret_msg":"需要短信验证","sign":"RO2ZWFbcsnwarI9Zh/VSQAXCm0AXEmU5Dtdomv5+bwip0A4AvffPMg6Xzt3g9T2ID7nUZT/HDcD4G0lv0c9XqSSPc3ekRG+9eu5etwpnd8mCtZViy2siForJigNXBDFnuNVK7+iCdgnAJyWD+JobuPGTKpJEqG1XpItaF7xUuoQ=","sign_type":"RSA","sms_flag":"1","token":"5651C7C6D30484AA636B6D816ECE6E78"}
         * para : {"oid_partner":"201705101001719503","sign_type":"RSA","user_id":"15373550655","busi_partner":101001,"api_version":"1.1","no_order":"20170601163643138234001494544378","dt_order":"20170601163643","name_goods":"\u8ff7\u4f60\u5546\u54c1","money_order":"0.50","notify_url":"https:\/\/api.lianxiangke.cn\/Api\/Notifyurl","risk_item":"{\"frms_client_chnl\":\"10\",\"frms_ip_addr\":\"183.172.12.108\",\"frms_imei\":\"423198429310234\",\"frms_mechine_id\":\"423198429310234\",\"frms_mac_addr\":\"7C:7D:3D:B8:F1:A9\"}","pay_type":2,"card_no":"6222020403032192433","bind_mob":"15373550655","no_agree":"2017060134793542","pwd_pay":"MC+o7IUNTkqwBfYAVdZq0hnsMsCWQhpUCV9I2IeZOPhl6Qu0aNXqblHhSmtxk0JDLxNoa8sYJ9rdUBbtWnk2e0kOH4zYN51cC16Hyjb4WXLIhxJtiFbgsnmkgN1CRJ+naCofgOZHmZSAuLjeIZqGPC61WGk8EEInydewfn3kv5w=","col_userid":"15321208718"}
         * token : 5651C7C6D30484AA636B6D816ECE6E78
         * ptoken : 8901a245939f031586e5ef912f099e70
         * uid : 10010837
         */

        private DataBean data;
        private String para;
        private String token;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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
             * dt_order : 20170601163643
             * money_order : 0.50
             * no_order : 20170601163643138234001494544378
             * oid_partner : 201705101001719503
             * oid_paybill : 2017060195290900
             * ret_code : 8888
             * ret_msg : 需要短信验证
             * sign : RO2ZWFbcsnwarI9Zh/VSQAXCm0AXEmU5Dtdomv5+bwip0A4AvffPMg6Xzt3g9T2ID7nUZT/HDcD4G0lv0c9XqSSPc3ekRG+9eu5etwpnd8mCtZViy2siForJigNXBDFnuNVK7+iCdgnAJyWD+JobuPGTKpJEqG1XpItaF7xUuoQ=
             * sign_type : RSA
             * sms_flag : 1
             * token : 5651C7C6D30484AA636B6D816ECE6E78
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
