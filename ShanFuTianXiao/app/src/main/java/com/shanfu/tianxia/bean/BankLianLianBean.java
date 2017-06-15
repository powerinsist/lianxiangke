package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/25.
 */

public class BankLianLianBean implements Serializable {


    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":[{"id":"10","source":"0","pay_type":"2","bank_code":"01020000","bankname":"中国工商银行","card_no":"6222020403032192433","user_id":"15373550655","no_agree":"2017052430033004","stat_user":"1","addtime":"2017-05-24 15:31:15"}],"ptoken":"b3b9cf3d8f29ed2022587d9d35ca01d3","uid":"10012998"}
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
         * data : [{"id":"10","source":"0","pay_type":"2","bank_code":"01020000","bankname":"中国工商银行","card_no":"6222020403032192433","user_id":"15373550655","no_agree":"2017052430033004","stat_user":"1","addtime":"2017-05-24 15:31:15"}]
         * ptoken : b3b9cf3d8f29ed2022587d9d35ca01d3
         * uid : 10012998
         */

        private String ptoken;
        private String uid;
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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 10
             * source : 0
             * pay_type : 2
             * bank_code : 01020000
             * bankname : 中国工商银行
             * card_no : 6222020403032192433
             * user_id : 15373550655
             * no_agree : 2017052430033004
             * stat_user : 1
             * addtime : 2017-05-24 15:31:15
             */

            private String id;
            private String source;
            private String pay_type;
            private String bank_code;
            private String bankname;
            private String card_no;
            private String user_id;
            private String no_agree;
            private String stat_user;
            private String addtime;
            private String bind_mob;
            private String vali_date;
            private String cvv2;

            public String getBind_mob() {
                return bind_mob;
            }

            public void setBind_mob(String bind_mob) {
                this.bind_mob = bind_mob;
            }

            public String getVali_date() {
                return vali_date;
            }

            public void setVali_date(String vali_date) {
                this.vali_date = vali_date;
            }

            public String getCvv2() {
                return cvv2;
            }

            public void setCvv2(String cvv2) {
                this.cvv2 = cvv2;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
            }

            public String getBank_code() {
                return bank_code;
            }

            public void setBank_code(String bank_code) {
                this.bank_code = bank_code;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }

            public String getCard_no() {
                return card_no;
            }

            public void setCard_no(String card_no) {
                this.card_no = card_no;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getNo_agree() {
                return no_agree;
            }

            public void setNo_agree(String no_agree) {
                this.no_agree = no_agree;
            }

            public String getStat_user() {
                return stat_user;
            }

            public void setStat_user(String stat_user) {
                this.stat_user = stat_user;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
