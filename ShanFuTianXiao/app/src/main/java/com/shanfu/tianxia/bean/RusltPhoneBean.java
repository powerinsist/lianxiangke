package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/4/26.
 */

public class RusltPhoneBean implements Serializable{
    private String err_code;
    private String err_msg;
    private PhoneBean data;

    public PhoneBean getData() {
        return data;
    }

    public void setData(PhoneBean data) {
        this.data = data;
    }

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

    public class PhoneBean{
        private String phone;
        private String uid;
        private String ptoken;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPtoken() {
            return ptoken;
        }

        public void setPtoken(String ptoken) {
            this.ptoken = ptoken;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
