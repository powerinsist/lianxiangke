package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/24.
 */

public class SingleUserQueryBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"addr_conn":"浙江省杭州滨江区滨盛路3333号","balance":"0.00","cashout_amt":"0.00","dt_register":"2017-05-23 19:16:43","exp_idcard":"99990101","freeze_balance":"0.00","kyc_status":"1","mob_bind":"153****0655","name_user":"*艳辉","no_idcard":"1****************4","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"e0Y9sr72kCgM7CW1qzTNpGOV3hFa5CNwqZF3vLldqdE0PIAGu1e0U9KF0m5mbOeCO1UZhCXPu1HJZWuarxqejsvA/YSwFb0GdrW//ukIN3fpj3d7M2nz4qzYPFU7NxYuVAV7J5t716YkGNzYCp0ng1qa1wdYM+Gxm9Cp3ecRYUE=","sign_type":"RSA","stat_user":"1","type_idcard":"0","user_id":"15373550655"},"ptoken":"3096a755b3677bd5f4d5a53c6321b034","uid":"10012998"}
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
         * data : {"addr_conn":"浙江省杭州滨江区滨盛路3333号","balance":"0.00","cashout_amt":"0.00","dt_register":"2017-05-23 19:16:43","exp_idcard":"99990101","freeze_balance":"0.00","kyc_status":"1","mob_bind":"153****0655","name_user":"*艳辉","no_idcard":"1****************4","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"e0Y9sr72kCgM7CW1qzTNpGOV3hFa5CNwqZF3vLldqdE0PIAGu1e0U9KF0m5mbOeCO1UZhCXPu1HJZWuarxqejsvA/YSwFb0GdrW//ukIN3fpj3d7M2nz4qzYPFU7NxYuVAV7J5t716YkGNzYCp0ng1qa1wdYM+Gxm9Cp3ecRYUE=","sign_type":"RSA","stat_user":"1","type_idcard":"0","user_id":"15373550655"}
         * ptoken : 3096a755b3677bd5f4d5a53c6321b034
         * uid : 10012998
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
             * addr_conn : 浙江省杭州滨江区滨盛路3333号
             * balance : 0.00
             * cashout_amt : 0.00
             * dt_register : 2017-05-23 19:16:43
             * exp_idcard : 99990101
             * freeze_balance : 0.00
             * kyc_status : 1
             * mob_bind : 153****0655
             * name_user : *艳辉
             * no_idcard : 1****************4
             * oid_partner : 201705101001719503
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : e0Y9sr72kCgM7CW1qzTNpGOV3hFa5CNwqZF3vLldqdE0PIAGu1e0U9KF0m5mbOeCO1UZhCXPu1HJZWuarxqejsvA/YSwFb0GdrW//ukIN3fpj3d7M2nz4qzYPFU7NxYuVAV7J5t716YkGNzYCp0ng1qa1wdYM+Gxm9Cp3ecRYUE=
             * sign_type : RSA
             * stat_user : 1
             * type_idcard : 0
             * user_id : 15373550655
             */

            private String addr_conn;
            private String balance;
            private String cashout_amt;
            private String dt_register;
            private String exp_idcard;
            private String freeze_balance;
            private String kyc_status;
            private String mob_bind;
            private String name_user;
            private String no_idcard;
            private String oid_partner;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String stat_user;
            private String type_idcard;
            private String user_id;

            public String getAddr_conn() {
                return addr_conn;
            }

            public void setAddr_conn(String addr_conn) {
                this.addr_conn = addr_conn;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getCashout_amt() {
                return cashout_amt;
            }

            public void setCashout_amt(String cashout_amt) {
                this.cashout_amt = cashout_amt;
            }

            public String getDt_register() {
                return dt_register;
            }

            public void setDt_register(String dt_register) {
                this.dt_register = dt_register;
            }

            public String getExp_idcard() {
                return exp_idcard;
            }

            public void setExp_idcard(String exp_idcard) {
                this.exp_idcard = exp_idcard;
            }

            public String getFreeze_balance() {
                return freeze_balance;
            }

            public void setFreeze_balance(String freeze_balance) {
                this.freeze_balance = freeze_balance;
            }

            public String getKyc_status() {
                return kyc_status;
            }

            public void setKyc_status(String kyc_status) {
                this.kyc_status = kyc_status;
            }

            public String getMob_bind() {
                return mob_bind;
            }

            public void setMob_bind(String mob_bind) {
                this.mob_bind = mob_bind;
            }

            public String getName_user() {
                return name_user;
            }

            public void setName_user(String name_user) {
                this.name_user = name_user;
            }

            public String getNo_idcard() {
                return no_idcard;
            }

            public void setNo_idcard(String no_idcard) {
                this.no_idcard = no_idcard;
            }

            public String getOid_partner() {
                return oid_partner;
            }

            public void setOid_partner(String oid_partner) {
                this.oid_partner = oid_partner;
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

            public String getStat_user() {
                return stat_user;
            }

            public void setStat_user(String stat_user) {
                this.stat_user = stat_user;
            }

            public String getType_idcard() {
                return type_idcard;
            }

            public void setType_idcard(String type_idcard) {
                this.type_idcard = type_idcard;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
