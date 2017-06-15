package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/31.
 */

public class MyReceivablesBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"balance":"0.66","dt_order":"20170531104814","freeze_balance":"0.00","money_order":"0.01","name_user":"秦艳辉","no_order":"20170531104814775253001108849027","oid_partner":"201705101001719503","oid_paybill":"2017053193315538","ret_code":"0000","ret_msg":"交易成功","sign":"Sfh4BBrmd8vNtww52bsheybgkwzFMuCp/15zJnvhwM3G0fW7ek/2RmIH6LPDnGejcb/G4jeZYx8Ppt2X8Bbmp6i5ozIMN1qAaM46WZ2UxV2Lgxk0EW4JB7g0l3vhU3imEtDi/ze8bXIb9bK8443yianaHQG2EHmeY2Jww43cJGY=","sign_type":"RSA"},"ptoken":"79e36e01eb2aef25fd739aa305f61b34","uid":"10010837"}
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
         * data : {"balance":"0.66","dt_order":"20170531104814","freeze_balance":"0.00","money_order":"0.01","name_user":"秦艳辉","no_order":"20170531104814775253001108849027","oid_partner":"201705101001719503","oid_paybill":"2017053193315538","ret_code":"0000","ret_msg":"交易成功","sign":"Sfh4BBrmd8vNtww52bsheybgkwzFMuCp/15zJnvhwM3G0fW7ek/2RmIH6LPDnGejcb/G4jeZYx8Ppt2X8Bbmp6i5ozIMN1qAaM46WZ2UxV2Lgxk0EW4JB7g0l3vhU3imEtDi/ze8bXIb9bK8443yianaHQG2EHmeY2Jww43cJGY=","sign_type":"RSA"}
         * ptoken : 79e36e01eb2aef25fd739aa305f61b34
         * uid : 10010837
         */

        private DataBean data;
        private String ptoken;
        private String uid;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
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
             * balance : 0.66
             * dt_order : 20170531104814
             * freeze_balance : 0.00
             * money_order : 0.01
             * name_user : 秦艳辉
             * no_order : 20170531104814775253001108849027
             * oid_partner : 201705101001719503
             * oid_paybill : 2017053193315538
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : Sfh4BBrmd8vNtww52bsheybgkwzFMuCp/15zJnvhwM3G0fW7ek/2RmIH6LPDnGejcb/G4jeZYx8Ppt2X8Bbmp6i5ozIMN1qAaM46WZ2UxV2Lgxk0EW4JB7g0l3vhU3imEtDi/ze8bXIb9bK8443yianaHQG2EHmeY2Jww43cJGY=
             * sign_type : RSA
             */

            private String balance;
            private String dt_order;
            private String freeze_balance;
            private String money_order;
            private String name_user;
            private String no_order;
            private String oid_partner;
            private String oid_paybill;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String token;
            private String user_id;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getDt_order() {
                return dt_order;
            }

            public void setDt_order(String dt_order) {
                this.dt_order = dt_order;
            }

            public String getFreeze_balance() {
                return freeze_balance;
            }

            public void setFreeze_balance(String freeze_balance) {
                this.freeze_balance = freeze_balance;
            }

            public String getMoney_order() {
                return money_order;
            }

            public void setMoney_order(String money_order) {
                this.money_order = money_order;
            }

            public String getName_user() {
                return name_user;
            }

            public void setName_user(String name_user) {
                this.name_user = name_user;
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
        }
    }
}
