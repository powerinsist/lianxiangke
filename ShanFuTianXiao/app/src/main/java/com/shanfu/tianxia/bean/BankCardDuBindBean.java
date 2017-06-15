package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/24.
 */

public class BankCardDuBindBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"ret_code":"8901","ret_msg":"没有记录"},"para":"{\"oid_partner\":\"201705101001719503\",\"sign_type\":\"RSA\",\"pay_type\":\"2\",\"user_id\":\"15373550655\",\"offset\":\"0\"}","ptoken":"fd5d193d8671fe7fb76559da6c7d516c","uid":"10012998"}
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
         * data : {"ret_code":"8901","ret_msg":"没有记录"}
         * para : {"oid_partner":"201705101001719503","sign_type":"RSA","pay_type":"2","user_id":"15373550655","offset":"0"}
         * ptoken : fd5d193d8671fe7fb76559da6c7d516c
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
             * ret_code : 8901
             * ret_msg : 没有记录
             */

            private String ret_code;
            private String ret_msg;

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
        }
    }
}
