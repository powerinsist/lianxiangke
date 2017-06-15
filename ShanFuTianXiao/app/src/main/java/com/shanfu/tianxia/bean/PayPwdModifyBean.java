package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/25.
 */

public class PayPwdModifyBean implements Serializable {


    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"h+9rgvxG9rEDFJ/lFu+v8/KdSOQzl7Lx6GBwrXLqCgYsaocP69iVBDgsXw5m7+8Fcaxja7nm8UI1xhxc1uL0JoN63jI9Ap8qS4dxxeTss3FSvQxW1RV5r6vyOA2K1MJDkI07zIUauYRrC4s/qmZ/gJhm/oftY/JbWIDnQ8rEpSg=","sign_type":"RSA"},"ptoken":"742f7dcbc537d38bd951613fc94918cd","uid":"10012998"}
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
         * data : {"oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"h+9rgvxG9rEDFJ/lFu+v8/KdSOQzl7Lx6GBwrXLqCgYsaocP69iVBDgsXw5m7+8Fcaxja7nm8UI1xhxc1uL0JoN63jI9Ap8qS4dxxeTss3FSvQxW1RV5r6vyOA2K1MJDkI07zIUauYRrC4s/qmZ/gJhm/oftY/JbWIDnQ8rEpSg=","sign_type":"RSA"}
         * ptoken : 742f7dcbc537d38bd951613fc94918cd
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
             * oid_partner : 201705101001719503
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : h+9rgvxG9rEDFJ/lFu+v8/KdSOQzl7Lx6GBwrXLqCgYsaocP69iVBDgsXw5m7+8Fcaxja7nm8UI1xhxc1uL0JoN63jI9Ap8qS4dxxeTss3FSvQxW1RV5r6vyOA2K1MJDkI07zIUauYRrC4s/qmZ/gJhm/oftY/JbWIDnQ8rEpSg=
             * sign_type : RSA
             */

            private String oid_partner;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;

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
        }
    }
}
