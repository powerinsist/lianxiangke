package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by qing on 2017/3/25.
 * 支付返回
 */
public class PayResultBean implements Serializable {
    private String err_code;
    private String err_msg;
    private PayBean data;

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

    public PayBean getData() {
        return data;
    }

    public void setData(PayBean data) {
        this.data = data;
    }

    public class PayBean{
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
