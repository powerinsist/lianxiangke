package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/6/5.
 */

public class ModifyUserInfoBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"ret_code":"8314","ret_msg":"您已实名开户，请勿重复提交"},"para":"{\"oid_partner\":\"201705101001719503\",\"sign_type\":\"RSA\",\"user_id\":\"15373550655\",\"token\":\"BD9B6D00EA7E8997FC111782CA901B6F\",\"name_user\":\"\\u79e6\\u8f89\",\"type_idcard\":0,\"no_idcard\":\"130283199007280634\",\"risk_item\":\"{\\\"frms_client_chnl\\\":\\\"10\\\",\\\"frms_ip_addr\\\":\\\"183.172.12.108\\\",\\\"frms_imei\\\":\\\"423198429310234\\\",\\\"frms_mechine_id\\\":\\\"423198429310234\\\",\\\"frms_mac_addr\\\":\\\"7C:7D:3D:B8:F1:A9\\\"}\"}","ptoken":"b590308794286ccf4f991d5ccedb88ca","uid":"10012998"}
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
         * data : {"ret_code":"8314","ret_msg":"您已实名开户，请勿重复提交"}
         * para : {"oid_partner":"201705101001719503","sign_type":"RSA","user_id":"15373550655","token":"BD9B6D00EA7E8997FC111782CA901B6F","name_user":"\u79e6\u8f89","type_idcard":0,"no_idcard":"130283199007280634","risk_item":"{\"frms_client_chnl\":\"10\",\"frms_ip_addr\":\"183.172.12.108\",\"frms_imei\":\"423198429310234\",\"frms_mechine_id\":\"423198429310234\",\"frms_mac_addr\":\"7C:7D:3D:B8:F1:A9\"}"}
         * ptoken : b590308794286ccf4f991d5ccedb88ca
         * uid : 10012998
         */

        private DataBean data;
        private String para;
        private String ptoken;
        private String uid;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getPara() {
            return para;
        }

        public void setPara(String para) {
            this.para = para;
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
             * ret_code : 8314
             * ret_msg : 您已实名开户，请勿重复提交
             */

            private String ret_code;
            private String ret_msg;

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
        }
    }
}
