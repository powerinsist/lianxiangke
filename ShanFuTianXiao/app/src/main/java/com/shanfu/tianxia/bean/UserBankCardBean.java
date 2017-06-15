package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/23.
 */

public class UserBankCardBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"agreement_list":[{"bank_code":"01020000","bank_name":"中国工商银行","bind_mobile":"15373550655","card_no":"2433","card_type":"2","no_agree":"2017052329646298"}],"count":"1","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"fBCfUidBYnQyuOX2uGSoiPBHnH7+wSvB4zg7LkleDr9TJB2r+ObwyGkV7RvllhBJ5NnD422tpaqtmzRpIMSpppzUjzqrPZKGLsbjOfNR/4QmYs8tsU5gFcWScHFkb1jikujiLPY9Dy92tsxv9OO7CfkLBKvpbQ7YCZoVWGGUOhw=","sign_type":"RSA","user_id":"15373550655"},"ptoken":"d9ab4baf2f55c020e9d53c476750aa03","uid":"10012998"}
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
         * data : {"agreement_list":[{"bank_code":"01020000","bank_name":"中国工商银行","bind_mobile":"15373550655","card_no":"2433","card_type":"2","no_agree":"2017052329646298"}],"count":"1","oid_partner":"201705101001719503","ret_code":"0000","ret_msg":"交易成功","sign":"fBCfUidBYnQyuOX2uGSoiPBHnH7+wSvB4zg7LkleDr9TJB2r+ObwyGkV7RvllhBJ5NnD422tpaqtmzRpIMSpppzUjzqrPZKGLsbjOfNR/4QmYs8tsU5gFcWScHFkb1jikujiLPY9Dy92tsxv9OO7CfkLBKvpbQ7YCZoVWGGUOhw=","sign_type":"RSA","user_id":"15373550655"}
         * ptoken : d9ab4baf2f55c020e9d53c476750aa03
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
             * agreement_list : [{"bank_code":"01020000","bank_name":"中国工商银行","bind_mobile":"15373550655","card_no":"2433","card_type":"2","no_agree":"2017052329646298"}]
             * count : 1
             * oid_partner : 201705101001719503
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : fBCfUidBYnQyuOX2uGSoiPBHnH7+wSvB4zg7LkleDr9TJB2r+ObwyGkV7RvllhBJ5NnD422tpaqtmzRpIMSpppzUjzqrPZKGLsbjOfNR/4QmYs8tsU5gFcWScHFkb1jikujiLPY9Dy92tsxv9OO7CfkLBKvpbQ7YCZoVWGGUOhw=
             * sign_type : RSA
             * user_id : 15373550655
             */



            private String count;
            private String oid_partner;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String user_id;
            private List<AgreementListBean> agreement_list;

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getOid_partner() {
                return oid_partner;
            }

            public void setOid_partner(String oid_partner) {
                this.oid_partner = oid_partner;
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

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public List<AgreementListBean> getAgreement_list() {
                return agreement_list;
            }

            public void setAgreement_list(List<AgreementListBean> agreement_list) {
                this.agreement_list = agreement_list;
            }

            public static class AgreementListBean {
                /**
                 * bank_code : 01020000
                 * bank_name : 中国工商银行
                 * bind_mobile : 15373550655
                 * card_no : 2433
                 * card_type : 2
                 * no_agree : 2017052329646298
                 */

                private String bank_code;
                private String bank_name;
                private String bind_mobile;
                private String card_no;
                private String card_type;
                private String no_agree;

                public String getBank_code() {
                    return bank_code;
                }

                public void setBank_code(String bank_code) {
                    this.bank_code = bank_code;
                }

                public String getBank_name() {
                    return bank_name;
                }

                public void setBank_name(String bank_name) {
                    this.bank_name = bank_name;
                }

                public String getBind_mobile() {
                    return bind_mobile;
                }

                public void setBind_mobile(String bind_mobile) {
                    this.bind_mobile = bind_mobile;
                }

                public String getCard_no() {
                    return card_no;
                }

                public void setCard_no(String card_no) {
                    this.card_no = card_no;
                }

                public String getCard_type() {
                    return card_type;
                }

                public void setCard_type(String card_type) {
                    this.card_type = card_type;
                }

                public String getNo_agree() {
                    return no_agree;
                }

                public void setNo_agree(String no_agree) {
                    this.no_agree = no_agree;
                }
            }
        }
    }
}
