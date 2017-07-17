package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/7/12.
 */

public class TicketUserBean implements Serializable{

    /**
     * err_code : 200
     * err_msg : 联享票个数！
     * data : {"number":"0","ptoken":"f20b6b7766240f8f92232f323e7671ba","uid":"10012998"}
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
         * number : 0
         * ptoken : f20b6b7766240f8f92232f323e7671ba
         * uid : 10012998
         */

        private String number;
        private String ptoken;
        private String uid;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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
    }
}
