package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/6/6.
 */

public class AlreadyReceiverBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 领取列表
     * data : {"data":[{"id":"10","money":"0.01","collection_time":"2017-06-06 16:45:08"},{"id":"20","money":"0","collection_time":"2017-06-05 16:34:52"},{"id":"50","money":"0.01","collection_time":"2017-06-06 16:52:10"},{"id":"80","money":"0.01","collection_time":"2017-06-06 17:19:33"},{"id":"100","money":"0.01","collection_time":"2017-06-06 17:42:56"}],"ptoken":"95df1af0af876dcaa94fda0a81f941dc","uid":"10012998","totalmoney":"0.04"}
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
         * data : [{"id":"10","money":"0.01","collection_time":"2017-06-06 16:45:08"},{"id":"20","money":"0","collection_time":"2017-06-05 16:34:52"},{"id":"50","money":"0.01","collection_time":"2017-06-06 16:52:10"},{"id":"80","money":"0.01","collection_time":"2017-06-06 17:19:33"},{"id":"100","money":"0.01","collection_time":"2017-06-06 17:42:56"}]
         * ptoken : 95df1af0af876dcaa94fda0a81f941dc
         * uid : 10012998
         * totalmoney : 0.04
         */

        private String ptoken;
        private String uid;
        private String totalmoney;
        private List<DataBean> data;

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

        public String getTotalmoney() {
            return totalmoney;
        }

        public void setTotalmoney(String totalmoney) {
            this.totalmoney = totalmoney;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 10
             * money : 0.01
             * collection_time : 2017-06-06 16:45:08
             */

            private String id;
            private String money;
            private String collection_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCollection_time() {
                return collection_time;
            }

            public void setCollection_time(String collection_time) {
                this.collection_time = collection_time;
            }
        }
    }
}
