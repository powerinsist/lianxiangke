package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/6/2.
 */

public class TicktListBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 联享票列表
     * data : {"list1":[{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"20","money":0.01,"time":"2017-06-07","serial_number":"20"},{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"30","money":0.01,"time":"2017-06-07","serial_number":"30"},{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"100","money":0.01,"time":"2017-06-07","serial_number":"100"},{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"130","money":0.01,"time":"2017-06-07","serial_number":"130"}],"list2":[{"addtime":"1496815452","uid":"10012998","collection_time":"0","status":"1","id":"110","time":"1970-01-01","serial_number":"110"}],"list3":[],"ptoken":"64802c98dcfe4340f4e662885bf207fb","uid":"10012998","lingpiao":"0.999"}
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
         * list1 : [{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"20","money":0.01,"time":"2017-06-07","serial_number":"20"},{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"30","money":0.01,"time":"2017-06-07","serial_number":"30"},{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"100","money":0.01,"time":"2017-06-07","serial_number":"100"},{"deadline":1496815452,"uid":"10012998","status":"0","addtime":"1496815452","id":"130","money":0.01,"time":"2017-06-07","serial_number":"130"}]
         * list2 : [{"addtime":"1496815452","uid":"10012998","collection_time":"0","status":"1","id":"110","time":"1970-01-01","serial_number":"110"}]
         * list3 : []
         * ptoken : 64802c98dcfe4340f4e662885bf207fb
         * uid : 10012998
         * lingpiao : 0.999
         */

        private String ptoken;
        private String uid;
        private String lingpiao;
        private List<List1Bean> list1;
        private List<List2Bean> list2;
        private List<?> list3;

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

        public String getLingpiao() {
            return lingpiao;
        }

        public void setLingpiao(String lingpiao) {
            this.lingpiao = lingpiao;
        }

        public List<List1Bean> getList1() {
            return list1;
        }

        public void setList1(List<List1Bean> list1) {
            this.list1 = list1;
        }

        public List<List2Bean> getList2() {
            return list2;
        }

        public void setList2(List<List2Bean> list2) {
            this.list2 = list2;
        }

        public List<?> getList3() {
            return list3;
        }

        public void setList3(List<?> list3) {
            this.list3 = list3;
        }

        public static class List1Bean {
            /**
             * deadline : 1496815452
             * uid : 10012998
             * status : 0
             * addtime : 1496815452
             * id : 20
             * money : 0.01
             * time : 2017-06-07
             * serial_number : 20
             */

            private String deadline;
            private String uid;
            private String status;
            private String addtime;
            private String id;
            private String money;
            private String time;
            private String serial_number;

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSerial_number() {
                return serial_number;
            }

            public void setSerial_number(String serial_number) {
                this.serial_number = serial_number;
            }
        }

        public static class List2Bean {
            /**
             * addtime : 1496815452
             * uid : 10012998
             * collection_time : 0
             * status : 1
             * id : 110
             * time : 1970-01-01
             * serial_number : 110
             */

            private String addtime;
            private String uid;
            private String collection_time;
            private String status;
            private String id;
            private String time;
            private String serial_number;

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getCollection_time() {
                return collection_time;
            }

            public void setCollection_time(String collection_time) {
                this.collection_time = collection_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSerial_number() {
                return serial_number;
            }

            public void setSerial_number(String serial_number) {
                this.serial_number = serial_number;
            }
        }
    }
}
