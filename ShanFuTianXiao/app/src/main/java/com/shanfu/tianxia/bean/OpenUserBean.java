package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/22.
 */

public class OpenUserBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"oid_userno":"2017061111381065","user_id":"13716899183","name_user":"测试专用","idcard":"130283199007280634","pwd_pay":"IeaR/PUZ2UkM8AFjNGJoZdw16bl5x2Da+64OdcFz87oG4J4wH23qtZ9w2dEqstmLc0kGoyU641l27+iFSrwNzDZwcZPm6rFh0D8rF8YoCUa3gY0/TVF4YyzwXY/L6Fn/jnfpepaUSrWiSNfjNTZ7Lb3RK6xxu7cuRQR3K0e4Mqs=","pwd_pay_two":"3d186804534370c3c817db0563f0e461","data":{"oid_partner":"201705101001719503","oid_userno":"2017061111381065","ret_code":"0000","ret_msg":"交易成功","sign":"EorOLbcqZpJQoh5Ay0Yv15XETUckrWhk9bxhXw8urFIgdz5UwkiMu/T1I/LfSocSoAZvssYKuvcK4Jf3FVlwtnPC5hUwUYi92hfW21uTNBjVF7ux5XH+VyfAab4w1gC/EIkb+O26wBhIB+RoZbXakLaUX4+vcOrNm52mArn8Fbw=","sign_type":"RSA","stat_user":"0","user_id":"13716899183"},"ptoken":"3fa7a9e5ba908bd2118cccd79a664676","uid":"10013139"}
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
         * oid_userno : 2017061111381065
         * user_id : 13716899183
         * name_user : 测试专用
         * idcard : 130283199007280634
         * pwd_pay : IeaR/PUZ2UkM8AFjNGJoZdw16bl5x2Da+64OdcFz87oG4J4wH23qtZ9w2dEqstmLc0kGoyU641l27+iFSrwNzDZwcZPm6rFh0D8rF8YoCUa3gY0/TVF4YyzwXY/L6Fn/jnfpepaUSrWiSNfjNTZ7Lb3RK6xxu7cuRQR3K0e4Mqs=
         * pwd_pay_two : 3d186804534370c3c817db0563f0e461
         * data : {"oid_partner":"201705101001719503","oid_userno":"2017061111381065","ret_code":"0000","ret_msg":"交易成功","sign":"EorOLbcqZpJQoh5Ay0Yv15XETUckrWhk9bxhXw8urFIgdz5UwkiMu/T1I/LfSocSoAZvssYKuvcK4Jf3FVlwtnPC5hUwUYi92hfW21uTNBjVF7ux5XH+VyfAab4w1gC/EIkb+O26wBhIB+RoZbXakLaUX4+vcOrNm52mArn8Fbw=","sign_type":"RSA","stat_user":"0","user_id":"13716899183"}
         * ptoken : 3fa7a9e5ba908bd2118cccd79a664676
         * uid : 10013139
         */

        private String oid_userno;
        private String user_id;
        private String name_user;
        private String idcard;
        private String pwd_pay;
        private String pwd_pay_two;
        private DataBean data;
        private String ptoken;
        private String uid;

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

        public String getPwd_pay_two() {
            return pwd_pay_two;
        }

        public void setPwd_pay_two(String pwd_pay_two) {
            this.pwd_pay_two = pwd_pay_two;
        }

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
             * oid_partner : 201705101001719503
             * oid_userno : 2017061111381065
             * ret_code : 0000
             * ret_msg : 交易成功
             * sign : EorOLbcqZpJQoh5Ay0Yv15XETUckrWhk9bxhXw8urFIgdz5UwkiMu/T1I/LfSocSoAZvssYKuvcK4Jf3FVlwtnPC5hUwUYi92hfW21uTNBjVF7ux5XH+VyfAab4w1gC/EIkb+O26wBhIB+RoZbXakLaUX4+vcOrNm52mArn8Fbw=
             * sign_type : RSA
             * stat_user : 0
             * user_id : 13716899183
             */

            private String oid_partner;
            private String oid_userno;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String stat_user;
            private String user_id;

            public String getOid_partner() {
                return oid_partner;
            }

            public void setOid_partner(String oid_partner) {
                this.oid_partner = oid_partner;
            }

            public String getOid_userno() {
                return oid_userno;
            }

            public void setOid_userno(String oid_userno) {
                this.oid_userno = oid_userno;
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

            public String getStat_user() {
                return stat_user;
            }

            public void setStat_user(String stat_user) {
                this.stat_user = stat_user;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
    }
}
