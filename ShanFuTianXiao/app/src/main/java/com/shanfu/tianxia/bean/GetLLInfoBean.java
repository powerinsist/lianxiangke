package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/31.
 */

public class GetLLInfoBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"id":"10012998","oid_userno":"2017053111081752","user_id":"15373550655","name_user":"秦艳辉","idcard":"130283199007280634","pwd_pay":"AN/U7cXyBbf/4yqIrmBQ+P4+OBAVpr0mZOunuGsDXCshySqFJvndTjb+NJgp8WaD5uXJuIBy6MQuFWC7Sl8S/p4E0/ohyWTcE5ZEyA3/+Bz41QqrS9SuOpjjxr3s19dJaf6eWuySH2iHi6eC4a7p62pDyOPVvdrRn+7hHivrsDc="},"ptoken":"759a7403155842109d96fa31aa9031a8","uid":"10012998"}
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
         * data : {"id":"10012998","oid_userno":"2017053111081752","user_id":"15373550655","name_user":"秦艳辉","idcard":"130283199007280634","pwd_pay":"AN/U7cXyBbf/4yqIrmBQ+P4+OBAVpr0mZOunuGsDXCshySqFJvndTjb+NJgp8WaD5uXJuIBy6MQuFWC7Sl8S/p4E0/ohyWTcE5ZEyA3/+Bz41QqrS9SuOpjjxr3s19dJaf6eWuySH2iHi6eC4a7p62pDyOPVvdrRn+7hHivrsDc="}
         * ptoken : 759a7403155842109d96fa31aa9031a8
         * uid : 10012998
         */

        private DataBean data;
        private String ptoken;
        private String uid;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
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
             * id : 10012998
             * oid_userno : 2017053111081752
             * user_id : 15373550655
             * name_user : 秦艳辉
             * idcard : 130283199007280634
             * pwd_pay : AN/U7cXyBbf/4yqIrmBQ+P4+OBAVpr0mZOunuGsDXCshySqFJvndTjb+NJgp8WaD5uXJuIBy6MQuFWC7Sl8S/p4E0/ohyWTcE5ZEyA3/+Bz41QqrS9SuOpjjxr3s19dJaf6eWuySH2iHi6eC4a7p62pDyOPVvdrRn+7hHivrsDc=
             */

            private String id;
            private String oid_userno;
            private String user_id;
            private String name_user;
            private String idcard;
            private String pwd_pay;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOid_userno() {
                return oid_userno;
            }

            public void setOid_userno(String oid_userno) {
                this.oid_userno = oid_userno;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName_user() {
                return name_user;
            }

            public void setName_user(String name_user) {
                this.name_user = name_user;
            }

            public String getIdcard() {
                return idcard;
            }

            public void setIdcard(String idcard) {
                this.idcard = idcard;
            }

            public String getPwd_pay() {
                return pwd_pay;
            }

            public void setPwd_pay(String pwd_pay) {
                this.pwd_pay = pwd_pay;
            }
        }
    }
}
