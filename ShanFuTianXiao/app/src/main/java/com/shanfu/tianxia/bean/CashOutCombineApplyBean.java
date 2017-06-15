package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/27.
 */

public class CashOutCombineApplyBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"balance":"78.49","freeze_balance":"8.10","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"ZLX7ionIoGnujAiyEe8ThaxhNcNwSY/RVVAJx6t7Twa7SvWR86t94lkP/okTfDwNKtq06hEJP7gNS4AQNdc0ksfNJSmWv2xF0veWMPSPFb8bmwcS4kFc4G3iWJel5mPI7+8/e1ku3U9+8zu790zKjrjIyECGzkz0A8Q1EPIx02I=","sign_type":"RSA","user_id":"15373550655","counterfee":2},"ptoken":"27bac30f82514de5cf45578f2cc3758f","uid":"10012998"}
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
         * data : {"balance":"78.49","freeze_balance":"8.10","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"ZLX7ionIoGnujAiyEe8ThaxhNcNwSY/RVVAJx6t7Twa7SvWR86t94lkP/okTfDwNKtq06hEJP7gNS4AQNdc0ksfNJSmWv2xF0veWMPSPFb8bmwcS4kFc4G3iWJel5mPI7+8/e1ku3U9+8zu790zKjrjIyECGzkz0A8Q1EPIx02I=","sign_type":"RSA","user_id":"15373550655","counterfee":2}
         * ptoken : 27bac30f82514de5cf45578f2cc3758f
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
             * balance : 78.49
             * freeze_balance : 8.10
             * oid_partner : 201705101001719503
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : ZLX7ionIoGnujAiyEe8ThaxhNcNwSY/RVVAJx6t7Twa7SvWR86t94lkP/okTfDwNKtq06hEJP7gNS4AQNdc0ksfNJSmWv2xF0veWMPSPFb8bmwcS4kFc4G3iWJel5mPI7+8/e1ku3U9+8zu790zKjrjIyECGzkz0A8Q1EPIx02I=
             * sign_type : RSA
             * user_id : 15373550655
             * counterfee : 2
             */

            private String balance;
            private String freeze_balance;
            private String oid_partner;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String user_id;
            private String counterfee;

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getFreeze_balance() {
                return freeze_balance;
            }

            public void setFreeze_balance(String freeze_balance) {
                this.freeze_balance = freeze_balance;
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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getCounterfee() {
                return counterfee;
            }

            public void setCounterfee(String counterfee) {
                this.counterfee = counterfee;
            }
        }
    }
}
