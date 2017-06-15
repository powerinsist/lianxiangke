package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/27.
 */

public class BalancePwdPayTwoBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"balance":"1.25","freeze_balance":"0.00","money_order":"0.61","name_user":"秦艳辉","no_order":"20170605144801399746001430384795","oid_partner":"201705101001719503","oid_paybill":"2017060500930577","ret_code":"0000","ret_msg":"交易成功","sign":"UshhTasl4DrLTWQizgkcp5HkS6wuY+DN9y/sz/FuJn1hsvY+uedH5DUxl+pujmqHFw/1nBALeqZgArJZJCO7I7jl2rZ9To00Y09t4KO6e2yAO8xQAcz5wVH8b4XaJCRBiCh3S7LNOHrpy19eBkI90hzJJxYQn51KAxBUmYTIxUU=","sign_type":"RSA"},"redid":"56","shopspeople_redmoney":"0.06","shopstpeople":"no","ticketnum":0.0061,"ptoken":"3df49c4bab95057e8eb2ef71eb2edfac","uid":"10012998"}
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
         * data : {"balance":"1.25","freeze_balance":"0.00","money_order":"0.61","name_user":"秦艳辉","no_order":"20170605144801399746001430384795","oid_partner":"201705101001719503","oid_paybill":"2017060500930577","ret_code":"0000","ret_msg":"交易成功","sign":"UshhTasl4DrLTWQizgkcp5HkS6wuY+DN9y/sz/FuJn1hsvY+uedH5DUxl+pujmqHFw/1nBALeqZgArJZJCO7I7jl2rZ9To00Y09t4KO6e2yAO8xQAcz5wVH8b4XaJCRBiCh3S7LNOHrpy19eBkI90hzJJxYQn51KAxBUmYTIxUU=","sign_type":"RSA"}
         * redid : 56
         * shopspeople_redmoney : 0.06
         * shopstpeople : no
         * ticketnum : 0.0061
         * ptoken : 3df49c4bab95057e8eb2ef71eb2edfac
         * uid : 10012998
         */

        private DataBean data;
        private String redid;
        private String shopspeople_redmoney;
        private String shopstpeople;
        private String ticketnum;
        private String ptoken;
        private String uid;
        private String tui_auto_id;
        private String shoptid;
        private String shoptmoney;
        private String user_tid;

        public String getTui_auto_id() {
            return tui_auto_id;
        }

        public void setTui_auto_id(String tui_auto_id) {
            this.tui_auto_id = tui_auto_id;
        }

        public String getShoptid() {
            return shoptid;
        }

        public void setShoptid(String shoptid) {
            this.shoptid = shoptid;
        }

        public String getShoptmoney() {
            return shoptmoney;
        }

        public void setShoptmoney(String shoptmoney) {
            this.shoptmoney = shoptmoney;
        }

        public String getUser_tid() {
            return user_tid;
        }

        public void setUser_tid(String user_tid) {
            this.user_tid = user_tid;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getRedid() {
            return redid;
        }

        public void setRedid(String redid) {
            this.redid = redid;
        }

        public String getShopspeople_redmoney() {
            return shopspeople_redmoney;
        }

        public void setShopspeople_redmoney(String shopspeople_redmoney) {
            this.shopspeople_redmoney = shopspeople_redmoney;
        }

        public String getShopstpeople() {
            return shopstpeople;
        }

        public void setShopstpeople(String shopstpeople) {
            this.shopstpeople = shopstpeople;
        }

        public String getTicketnum() {
            return ticketnum;
        }

        public void setTicketnum(String ticketnum) {
            this.ticketnum = ticketnum;
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
             * balance : 1.25
             * freeze_balance : 0.00
             * money_order : 0.61
             * name_user : 秦艳辉
             * no_order : 20170605144801399746001430384795
             * oid_partner : 201705101001719503
             * oid_paybill : 2017060500930577
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : UshhTasl4DrLTWQizgkcp5HkS6wuY+DN9y/sz/FuJn1hsvY+uedH5DUxl+pujmqHFw/1nBALeqZgArJZJCO7I7jl2rZ9To00Y09t4KO6e2yAO8xQAcz5wVH8b4XaJCRBiCh3S7LNOHrpy19eBkI90hzJJxYQn51KAxBUmYTIxUU=
             * sign_type : RSA
             */

            private String balance;
            private String freeze_balance;
            private String money_order;
            private String name_user;
            private String no_order;
            private String oid_partner;
            private String oid_paybill;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getFreeze_balance() {
                return freeze_balance;
            }

            public void setFreeze_balance(String freeze_balance) {
                this.freeze_balance = freeze_balance;
            }

            public String getMoney_order() {
                return money_order;
            }

            public void setMoney_order(String money_order) {
                this.money_order = money_order;
            }

            public String getName_user() {
                return name_user;
            }

            public void setName_user(String name_user) {
                this.name_user = name_user;
            }

            public String getNo_order() {
                return no_order;
            }

            public void setNo_order(String no_order) {
                this.no_order = no_order;
            }

            public String getOid_partner() {
                return oid_partner;
            }

            public void setOid_partner(String oid_partner) {
                this.oid_partner = oid_partner;
            }

            public String getOid_paybill() {
                return oid_paybill;
            }

            public void setOid_paybill(String oid_paybill) {
                this.oid_paybill = oid_paybill;
            }

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

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }
        }
    }
}
