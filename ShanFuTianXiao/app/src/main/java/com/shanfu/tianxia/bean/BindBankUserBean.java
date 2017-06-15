package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/6/7.
 */

public class BindBankUserBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"name_user":"秦艳辉","bankname":"中国工商银行(2433)","list":{"name_user":"秦艳辉","bankname":"中国工商银行(2433)"},"ptoken":"15e299f26df8a7ae95c0831c74488025","uid":"10012998"}
     */

    private String err_code;
    private String err_msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name_user : 秦艳辉
         * bankname : 中国工商银行(2433)
         * list : {"name_user":"秦艳辉","bankname":"中国工商银行(2433)"}
         * ptoken : 15e299f26df8a7ae95c0831c74488025
         * uid : 10012998
         */

        private String name_user;
        private String bankname;
        private ListBean list;
        private String ptoken;
        private String uid;

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

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
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

        public static class ListBean {
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
