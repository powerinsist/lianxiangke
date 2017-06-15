package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/6/1.
 */

public class BankCardPaymentTwoBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"dt_order":"20170601174122","money_order":"0.62","no_agree":"2017060134793542","no_order":"20170601174122277376001272935508","oid_partner":"201705101001719503","oid_paybill":"2017060195373109","result_pay":"SUCCESS","ret_code":"0000","ret_msg":"交易成功","settle_date":"20170601","sign":"WM/ncKi/uuUft+sDqM9sk1Ob4CsQkLlDPGPXv04eepHrEkvgvhpqv0XZvVr1gMvahXPWJzQwQ6cyVZo4KDwQSkhAyjV7iLvN95vh4pXO1pOm2w/dbBMTYAocQV8M64vOqS0+bVIT2EIg/as/pnhu0NeKSB6HszsQ+owf2bTLabw=","sign_type":"RSA"},"oid_paybill":"2017060195373109","no_order":"20170601174122277376001272935508","ptoken":"e065417ead1ee37d01cc80a6483a4f6b","uid":"10010837"}
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
         * data : {"dt_order":"20170601174122","money_order":"0.62","no_agree":"2017060134793542","no_order":"20170601174122277376001272935508","oid_partner":"201705101001719503","oid_paybill":"2017060195373109","result_pay":"SUCCESS","ret_code":"0000","ret_msg":"交易成功","settle_date":"20170601","sign":"WM/ncKi/uuUft+sDqM9sk1Ob4CsQkLlDPGPXv04eepHrEkvgvhpqv0XZvVr1gMvahXPWJzQwQ6cyVZo4KDwQSkhAyjV7iLvN95vh4pXO1pOm2w/dbBMTYAocQV8M64vOqS0+bVIT2EIg/as/pnhu0NeKSB6HszsQ+owf2bTLabw=","sign_type":"RSA"}
         * oid_paybill : 2017060195373109
         * no_order : 20170601174122277376001272935508
         * ptoken : e065417ead1ee37d01cc80a6483a4f6b
         * uid : 10010837
         */

        private DataBean data;
        private String oid_paybill;
        private String no_order;
        private String ptoken;
        private String uid;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getOid_paybill() {
            return oid_paybill;
        }

        public void setOid_paybill(String oid_paybill) {
            this.oid_paybill = oid_paybill;
        }

        public String getNo_order() {
            return no_order;
        }

        public void setNo_order(String no_order) {
            this.no_order = no_order;
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
             * dt_order : 20170601174122
             * money_order : 0.62
             * no_agree : 2017060134793542
             * no_order : 20170601174122277376001272935508
             * oid_partner : 201705101001719503
             * oid_paybill : 2017060195373109
             * result_pay : SUCCESS
             * ret_code : 0000
             * ret_msg : 交易成功
             * settle_date : 20170601
             * sign : WM/ncKi/uuUft+sDqM9sk1Ob4CsQkLlDPGPXv04eepHrEkvgvhpqv0XZvVr1gMvahXPWJzQwQ6cyVZo4KDwQSkhAyjV7iLvN95vh4pXO1pOm2w/dbBMTYAocQV8M64vOqS0+bVIT2EIg/as/pnhu0NeKSB6HszsQ+owf2bTLabw=
             * sign_type : RSA
             */

            private String dt_order;
            private String money_order;
            private String no_agree;
            private String no_order;
            private String oid_partner;
            private String oid_paybill;
            private String result_pay;
            private String ret_code;
            private String ret_msg;
            private String settle_date;
            private String sign;
            private String sign_type;

            public String getDt_order() {
                return dt_order;
            }

            public void setDt_order(String dt_order) {
                this.dt_order = dt_order;
            }

            public String getMoney_order() {
                return money_order;
            }

            public void setMoney_order(String money_order) {
                this.money_order = money_order;
            }

            public String getNo_agree() {
                return no_agree;
            }

            public void setNo_agree(String no_agree) {
                this.no_agree = no_agree;
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

            public String getResult_pay() {
                return result_pay;
            }

            public void setResult_pay(String result_pay) {
                this.result_pay = result_pay;
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

            public String getSettle_date() {
                return settle_date;
            }

            public void setSettle_date(String settle_date) {
                this.settle_date = settle_date;
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
