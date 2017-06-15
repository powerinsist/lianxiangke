package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/19.
 */

public class RedPacketBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 使用列表
     * data : {"list":[{"status":"1","collectiontime":"2017-06-02 16:13:12","uid":"10012998","money":"0.05"},{"status":"1","collectiontime":"2017-06-05 12:20:34","uid":"10012998","money":"0.05"},{"status":"1","collectiontime":"2017-06-05 13:30:14","uid":"10012998","money":"0.06"},{"status":"1","collectiontime":"2017-06-05 15:13:39","uid":"10012998","money":"0.01"},{"status":"1","collectiontime":"2017-06-05 15:16:55","uid":"10012998","money":"0.06"},{"status":"1","collectiontime":"2017-06-05 15:30:35","uid":"10012998","money":"0.01"},{"status":"1","collectiontime":"2017-06-05 15:40:42","uid":"10012998","money":"0.01"},{"status":"1","collectiontime":"2017-06-05 15:47:41","uid":"10012998","money":"0.01"}],"countmoney":"0.26","ptoken":"ffead8ea5c6c26db63fff713f452af02","uid":"10012998"}
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
         * list : [{"status":"1","collectiontime":"2017-06-02 16:13:12","uid":"10012998","money":"0.05"},{"status":"1","collectiontime":"2017-06-05 12:20:34","uid":"10012998","money":"0.05"},{"status":"1","collectiontime":"2017-06-05 13:30:14","uid":"10012998","money":"0.06"},{"status":"1","collectiontime":"2017-06-05 15:13:39","uid":"10012998","money":"0.01"},{"status":"1","collectiontime":"2017-06-05 15:16:55","uid":"10012998","money":"0.06"},{"status":"1","collectiontime":"2017-06-05 15:30:35","uid":"10012998","money":"0.01"},{"status":"1","collectiontime":"2017-06-05 15:40:42","uid":"10012998","money":"0.01"},{"status":"1","collectiontime":"2017-06-05 15:47:41","uid":"10012998","money":"0.01"}]
         * countmoney : 0.26
         * ptoken : ffead8ea5c6c26db63fff713f452af02
         * uid : 10012998
         */

        private String countmoney;
        private String ptoken;
        private String uid;
        private List<ListBean> list;

        public String getCountmoney() {
            return countmoney;
        }

        public void setCountmoney(String countmoney) {
            this.countmoney = countmoney;
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
             * status : 1
             * collectiontime : 2017-06-02 16:13:12
             * uid : 10012998
             * money : 0.05
             */

            private String status;
            private String collectiontime;
            private String uid;
            private String money;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCollectiontime() {
                return collectiontime;
            }

            public void setCollectiontime(String collectiontime) {
                this.collectiontime = collectiontime;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
