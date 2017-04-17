package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by qing on 2017/4/4.
 */
public class WithdrawalsBean implements Serializable {

    private String err_code;

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

    public WithdrawalsData getData() {
        return data;
    }

    public void setData(WithdrawalsData data) {
        this.data = data;
    }

    private String err_msg;
    private WithdrawalsData data;

    public class WithdrawalsData{
        private String ptoken;
        private String uid;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
