package com.shanfu.tianxia.bean;

/**
 * Created by xuchenchen on 2017/5/27.
 */

public class BalancePwdPayBean {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"data":{"money_order":"0.2","no_order":"20170527164825792006001170632570","oid_partner":"201705101001719503","ret_code":"8888","ret_msg":"需要短信验证","sign":"d3LhF4OxsKLrrJ951VCfBtOAkzP+VlBASRxVFCD+wgAVuU6wFIEYLTU2fkD7NfR+yUzofgINXv5T6129W963Pxt9QIcH8JdRQGhQVUwS68DYC8Rp6RtYhwB4C8ppdmY7R3QZ8jZhEV7nsdUiEAR28RTi6WnAu1gPkpOh8Ew5+C4=","sign_type":"RSA","token":"8E0D4A6699327033881140F9D7A8A1B9","user_id":"15373550655"},"para":"{\"oid_partner\":\"201705101001719503\",\"sign_type\":\"RSA\",\"user_id\":\"15373550655\",\"no_order\":\"20170527164825792006001170632570\",\"dt_order\":\"20170527164825\",\"valid_order\":4320,\"busi_partner\":101001,\"name_goods\":\"\\u8ff7\\u4f60\\u5546\\u54c1\",\"money_order\":\"0.2\",\"pwd_pay\":\"CTOha5OQO5OTFrU5HmPW5BrRqfWA7aF+RPnlofHTTN7gCrFZlX9N4micTiuuSeWOg2nTRhsG\\/rsLPcd\\/7nUBEXwXuslTjd8cn9562Ha2329c244YclxIRpRnweffcQCNju\\/F\\/bASiSFWSMwPiFEsn5mCX44V9nn3dD2K712DMKk=\",\"notify_url\":\"https:\\/\\/api.lianxiangke.cn\\/Api\\/Notifyurltwo\",\"risk_item\":\"{\\\"frms_client_chnl\\\":\\\"10\\\",\\\"frms_ip_addr\\\":\\\"183.172.12.108\\\",\\\"frms_imei\\\":\\\"423198429310234\\\",\\\"frms_mechine_id\\\":\\\"423198429310234\\\",\\\"frms_mac_addr\\\":\\\"7C:7D:3D:B8:F1:A9\\\"}\"}","ptoken":"682619cee9deb73930eeeb84496827cc","uid":"10012998"}
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
         * data : {"money_order":"0.2","no_order":"20170527164825792006001170632570","oid_partner":"201705101001719503","ret_code":"8888","ret_msg":"需要短信验证","sign":"d3LhF4OxsKLrrJ951VCfBtOAkzP+VlBASRxVFCD+wgAVuU6wFIEYLTU2fkD7NfR+yUzofgINXv5T6129W963Pxt9QIcH8JdRQGhQVUwS68DYC8Rp6RtYhwB4C8ppdmY7R3QZ8jZhEV7nsdUiEAR28RTi6WnAu1gPkpOh8Ew5+C4=","sign_type":"RSA","token":"8E0D4A6699327033881140F9D7A8A1B9","user_id":"15373550655"}
         * para : {"oid_partner":"201705101001719503","sign_type":"RSA","user_id":"15373550655","no_order":"20170527164825792006001170632570","dt_order":"20170527164825","valid_order":4320,"busi_partner":101001,"name_goods":"\u8ff7\u4f60\u5546\u54c1","money_order":"0.2","pwd_pay":"CTOha5OQO5OTFrU5HmPW5BrRqfWA7aF+RPnlofHTTN7gCrFZlX9N4micTiuuSeWOg2nTRhsG\/rsLPcd\/7nUBEXwXuslTjd8cn9562Ha2329c244YclxIRpRnweffcQCNju\/F\/bASiSFWSMwPiFEsn5mCX44V9nn3dD2K712DMKk=","notify_url":"https:\/\/api.lianxiangke.cn\/Api\/Notifyurltwo","risk_item":"{\"frms_client_chnl\":\"10\",\"frms_ip_addr\":\"183.172.12.108\",\"frms_imei\":\"423198429310234\",\"frms_mechine_id\":\"423198429310234\",\"frms_mac_addr\":\"7C:7D:3D:B8:F1:A9\"}"}
         * ptoken : 682619cee9deb73930eeeb84496827cc
         * uid : 10012998
         */

        private DataBean data;
        private String ptoken;
        private String uid;
        private String oid_paybill;
        private String no_order;
        private String redid;
        private String shopspeople_redmoney;
        private String shopstpeople;
        private String tui_auto_id;
        private String shoptid;
        private String shoptmoney;
        private String user_tid;

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

        public String getRedid() {
            return redid;
        }

        public void setRedid(String redid) {
            this.redid = redid;
        }

        public String getShopspeople_redmoney() {
            return shopspeople_redmoney;
        }

        public void setShopspeople_redmoney(String shopspeople_redmoney) {
            this.shopspeople_redmoney = shopspeople_redmoney;
        }

        public String getShopstpeople() {
            return shopstpeople;
        }

        public void setShopstpeople(String shopstpeople) {
            this.shopstpeople = shopstpeople;
        }

        public String getTui_auto_id() {
            return tui_auto_id;
        }

        public void setTui_auto_id(String tui_auto_id) {
            this.tui_auto_id = tui_auto_id;
        }

        public String getShoptid() {
            return shoptid;
        }

        public void setShoptid(String shoptid) {
            this.shoptid = shoptid;
        }

        public String getShoptmoney() {
            return shoptmoney;
        }

        public void setShoptmoney(String shoptmoney) {
            this.shoptmoney = shoptmoney;
        }

        public String getUser_tid() {
            return user_tid;
        }

        public void setUser_tid(String user_tid) {
            this.user_tid = user_tid;
        }

        public String getTicketnum() {
            return ticketnum;
        }

        public void setTicketnum(String ticketnum) {
            this.ticketnum = ticketnum;
        }

        private String ticketnum;

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
             * money_order : 0.2
             * no_order : 20170527164825792006001170632570
             * oid_partner : 201705101001719503
             * ret_code : 8888
             * ret_msg : 需要短信验证
             * sign : d3LhF4OxsKLrrJ951VCfBtOAkzP+VlBASRxVFCD+wgAVuU6wFIEYLTU2fkD7NfR+yUzofgINXv5T6129W963Pxt9QIcH8JdRQGhQVUwS68DYC8Rp6RtYhwB4C8ppdmY7R3QZ8jZhEV7nsdUiEAR28RTi6WnAu1gPkpOh8Ew5+C4=
             * sign_type : RSA
             * token : 8E0D4A6699327033881140F9D7A8A1B9
             * user_id : 15373550655
             */

            private String money_order;
            private String no_order;
            private String oid_partner;
            private String ret_code;
            private String ret_msg;
            private String sign;
            private String sign_type;
            private String token;
            private String user_id;

            public String getMoney_order() {
                return money_order;
            }

            public void setMoney_order(String money_order) {
                this.money_order = money_order;
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

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
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
