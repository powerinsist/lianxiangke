package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/30.
 */
public class MyMerchantBean implements Serializable {
    private String err_code;
    private String err_msg;

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

    public ConsumptionData getData() {
        return data;
    }

    public void setData(ConsumptionData data) {
        this.data = data;
    }

    private ConsumptionData data;


    public class ConsumptionData{

        private List<MyMerchantListBean> list;
        private String ptoken;
        private String uid;

        public List<MyMerchantListBean> getList() {
            return list;
        }

        public void setList(List<MyMerchantListBean> list) {
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
    }

    public class MyMerchantListBean{
        private String shop_name;
        private String sign_time;
        private String discount;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        private String recommend;
    }
}

