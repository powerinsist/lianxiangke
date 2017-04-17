package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by qing on 2017/3/14.
 */
public class ShopDetailBean implements Serializable {

    private ShopData data;

    public ShopData getData() {
        return data;
    }

    public void setData(ShopData data) {
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

    private String err_code;
    private String err_msg;

    public class ShopData{
        private String address;
        private String banner;
        private String comment_num;
        private String label;
        private String lx;

        private String fw;
        private String hj;

        public String getFw() {
            return fw;
        }

        public void setFw(String fw) {
            this.fw = fw;
        }

        public String getHj() {
            return hj;
        }

        public void setHj(String hj) {
            this.hj = hj;
        }

        public String getJg() {
            return jg;
        }

        public void setJg(String jg) {
            this.jg = jg;
        }

        public String getWd() {
            return wd;
        }

        public void setWd(String wd) {
            this.wd = wd;
        }

        private String jg;
        private String wd;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLx() {
            return lx;
        }

        public void setLx(String lx) {
            this.lx = lx;
        }

        public String getLy() {
            return ly;
        }

        public void setLy(String ly) {
            this.ly = ly;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        private String ly;
        private String phone;
        private String score;
        private String shop_name;
    }
}
