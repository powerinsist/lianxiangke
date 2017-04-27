package com.shanfu.tianxia.bean;

/**
 * Created by qing on 2017/3/2.
 * 注册
 */
public class RegeditBean {
    private RegeData data;
    private String err_code;
    private String err_msg;

    public RegeData getData() {
        return data;
    }

    public void setData(RegeData data) {
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


    public class RegeData{
        private String ptoken;
        private String uid;
        private String phone;
        private String p_status;
        private String t_status;
        private String b_status;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPtoken() {
            return ptoken;
        }

        public void setPtoken(String ptoken) {
            this.ptoken = ptoken;
        }

        public String getB_status() {
            return b_status;
        }

        public void setB_status(String b_status) {
            this.b_status = b_status;
        }

        public String getP_status() {
            return p_status;
        }

        public void setP_status(String p_status) {
            this.p_status = p_status;
        }

        public String getT_status() {
            return t_status;
        }
        public void setT_status(String t_status) {
            this.t_status = t_status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
