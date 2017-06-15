package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/26.
 */

public class UserPayMentBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 用户收支列表
     * data : {"list":[{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060610250102","time":"2017-06-06 18:33:03","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060649555597","time":"2017-06-06 18:32:33","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060649555057","time":"2017-06-06 18:20:17","counterfee":null,"payment":"+"},{"money":"0.60","type":"2","source":"充值","orderid":"2017060697524998","time":"2017-06-06 18:04:59","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060649100102","time":"2017-06-06 17:57:22","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060651481004","time":"2017-06-06 17:54:11","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060655101515","time":"2017-06-06 17:51:04","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060650539710","time":"2017-06-06 17:49:54","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060648509756","time":"2017-06-06 17:42:56","counterfee":null,"payment":"+"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060654535550","time":"2017-06-06 17:35:51","counterfee":null,"payment":"-"},{"money":"0.03","type":"4","source":"消费","orderid":"2017060610156571","time":"2017-06-06 17:35:11","counterfee":null,"payment":"-"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060653525710","time":"2017-06-06 17:19:33","counterfee":null,"payment":"+"},{"money":"0.01","type":"2","source":"充值","orderid":"2017060649579710","time":"2017-06-06 17:16:19","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060657975051","time":"2017-06-06 16:52:10","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060652554849","time":"2017-06-06 16:45:08","counterfee":null,"payment":"+"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060610210055","time":"2017-06-06 16:42:56","counterfee":null,"payment":"-"},{"money":"0.01","type":"2","source":"充值","orderid":"2017060655485751","time":"2017-06-06 16:42:16","counterfee":null,"payment":"+"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060657521021","time":"2017-06-06 16:41:14","counterfee":null,"payment":"-"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060650981004","time":"2017-06-06 11:54:12","counterfee":null,"payment":"-"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060656559948","time":"2017-06-06 11:41:45","counterfee":null,"payment":"-"}],"page":3,"totalnumber ":"42","ptoken":"461be5df07610e0887e9269932e45f11","uid":"10012998"}
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
         * list : [{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060610250102","time":"2017-06-06 18:33:03","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060649555597","time":"2017-06-06 18:32:33","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060649555057","time":"2017-06-06 18:20:17","counterfee":null,"payment":"+"},{"money":"0.60","type":"2","source":"充值","orderid":"2017060697524998","time":"2017-06-06 18:04:59","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060649100102","time":"2017-06-06 17:57:22","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060651481004","time":"2017-06-06 17:54:11","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060655101515","time":"2017-06-06 17:51:04","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060650539710","time":"2017-06-06 17:49:54","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060648509756","time":"2017-06-06 17:42:56","counterfee":null,"payment":"+"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060654535550","time":"2017-06-06 17:35:51","counterfee":null,"payment":"-"},{"money":"0.03","type":"4","source":"消费","orderid":"2017060610156571","time":"2017-06-06 17:35:11","counterfee":null,"payment":"-"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060653525710","time":"2017-06-06 17:19:33","counterfee":null,"payment":"+"},{"money":"0.01","type":"2","source":"充值","orderid":"2017060649579710","time":"2017-06-06 17:16:19","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060657975051","time":"2017-06-06 16:52:10","counterfee":null,"payment":"+"},{"money":"0.01","type":"9","source":"联享票红包","orderid":"2017060652554849","time":"2017-06-06 16:45:08","counterfee":null,"payment":"+"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060610210055","time":"2017-06-06 16:42:56","counterfee":null,"payment":"-"},{"money":"0.01","type":"2","source":"充值","orderid":"2017060655485751","time":"2017-06-06 16:42:16","counterfee":null,"payment":"+"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060657521021","time":"2017-06-06 16:41:14","counterfee":null,"payment":"-"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060650981004","time":"2017-06-06 11:54:12","counterfee":null,"payment":"-"},{"money":"0.02","type":"4","source":"消费","orderid":"2017060656559948","time":"2017-06-06 11:41:45","counterfee":null,"payment":"-"}]
         * page : 3
         * totalnumber  : 42
         * ptoken : 461be5df07610e0887e9269932e45f11
         * uid : 10012998
         */

        private int page;
        private String totalnumber;
        private String ptoken;
        private String uid;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getTotalnumber() {
            return totalnumber;
        }

        public void setTotalnumber(String totalnumber) {
            this.totalnumber = totalnumber;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * money : 0.01
             * type : 9
             * source : 联享票红包
             * orderid : 2017060610250102
             * time : 2017-06-06 18:33:03
             * counterfee : null
             * payment : +
             */

            private String money;
            private String type;
            private String source;
            private String orderid;
            private String time;
            private String counterfee;
            private String payment;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getOrderid() {
                return orderid;
            }

            public void setOrderid(String orderid) {
                this.orderid = orderid;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCounterfee() {
                return counterfee;
            }

            public void setCounterfee(String counterfee) {
                this.counterfee = counterfee;
            }

            public String getPayment() {
                return payment;
            }

            public void setPayment(String payment) {
                this.payment = payment;
            }
        }
    }
}
