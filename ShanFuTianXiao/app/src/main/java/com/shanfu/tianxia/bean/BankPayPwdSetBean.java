package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/24.
 */

public class BankPayPwdSetBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"bank_code":"01020000","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"CjCwlMGOXElrYB0+QrpfX8aViNOI30Ir4SIUFFDhEZaPm2DZTaKUwKjNErNic1B2HMVgDOdHv96kUtS9IzG6VfbmLlE/0H5lbOw+AuikBhaDGUHTBrIEzhl4dvTSzU4giFuUWlc4RtC3vIVU/PlDEYSTX12DkFHBpmFND0VZduU=","sign_type":"RSA","token":"336AD2803FF92DCEEA55C8F99718124C","user_id":"15373550655"},"ptoken":"ab23e5c23593e9dc605d8e48c3fd1e68","uid":"10012998"}
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
         * data : {"bank_code":"01020000","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"CjCwlMGOXElrYB0+QrpfX8aViNOI30Ir4SIUFFDhEZaPm2DZTaKUwKjNErNic1B2HMVgDOdHv96kUtS9IzG6VfbmLlE/0H5lbOw+AuikBhaDGUHTBrIEzhl4dvTSzU4giFuUWlc4RtC3vIVU/PlDEYSTX12DkFHBpmFND0VZduU=","sign_type":"RSA","token":"336AD2803FF92DCEEA55C8F99718124C","user_id":"15373550655"}
         * ptoken : ab23e5c23593e9dc605d8e48c3fd1e68
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
             * bank_code : 01020000
             * oid_partner : 201705101001719503
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : CjCwlMGOXElrYB0+QrpfX8aViNOI30Ir4SIUFFDhEZaPm2DZTaKUwKjNErNic1B2HMVgDOdHv96kUtS9IzG6VfbmLlE/0H5lbOw+AuikBhaDGUHTBrIEzhl4dvTSzU4giFuUWlc4RtC3vIVU/PlDEYSTX12DkFHBpmFND0VZduU=
             * sign_type : RSA
             * token : 336AD2803FF92DCEEA55C8F99718124C
             * user_id : 15373550655
             */

            private String bank_code;
            private String oid_partner;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String token;
            private String user_id;

            public String getBank_code() {
                return bank_code;
            }

            public void setBank_code(String bank_code) {
                this.bank_code = bank_code;
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
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
