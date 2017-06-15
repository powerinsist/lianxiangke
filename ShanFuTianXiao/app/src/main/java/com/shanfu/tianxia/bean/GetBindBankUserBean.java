package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/6/7.
 */

public class GetBindBankUserBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"name_user":"秦艳辉","bankname":"中国工商银行(2433)"},"ptoken":"dd2fc38d762040580bec9d8590fb7364","uid":"10012998"}
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
         * data : {"name_user":"秦艳辉","bankname":"中国工商银行(2433)"}
         * ptoken : dd2fc38d762040580bec9d8590fb7364
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
             * name_user : 秦艳辉
             * bankname : 中国工商银行(2433)
             */

            private String name_user;
            private String bankname;

            public String getName_user() {
                return name_user;
            }

            public void setName_user(String name_user) {
                this.name_user = name_user;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }
        }
    }
}
