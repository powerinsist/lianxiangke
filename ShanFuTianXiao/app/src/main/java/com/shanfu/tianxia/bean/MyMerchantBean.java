package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/30.
 */
public class MyMerchantBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 已推荐的商铺
     * data : {"list":[{"shop_name":"admin123","recommend":"1231","discount":"","sign_time":"1970-01-01","shoptype":null},{"shop_name":"联想客","recommend":"1231","discount":"","sign_time":"2017-05-26","shoptype":null},{"shop_name":"上回","recommend":"1231","discount":null,"sign_time":"2017-06-06","shoptype":null},{"shop_name":"崔亚杰","recommend":"1231","discount":null,"sign_time":"1970-01-01","shoptype":null},{"shop_name":"12346","recommend":"1231","discount":null,"sign_time":"1970-01-01","shoptype":null},{"shop_name":"史庆雨","recommend":"1231","discount":null,"sign_time":"1970-01-01","shoptype":null}],"ptoken":"cb4677c72f50bfef6c10f974288c1164","uid":"10003086"}
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
         * list : [{"shop_name":"admin123","recommend":"1231","discount":"","sign_time":"1970-01-01","shoptype":null},{"shop_name":"联想客","recommend":"1231","discount":"","sign_time":"2017-05-26","shoptype":null},{"shop_name":"上回","recommend":"1231","discount":null,"sign_time":"2017-06-06","shoptype":null},{"shop_name":"崔亚杰","recommend":"1231","discount":null,"sign_time":"1970-01-01","shoptype":null},{"shop_name":"12346","recommend":"1231","discount":null,"sign_time":"1970-01-01","shoptype":null},{"shop_name":"史庆雨","recommend":"1231","discount":null,"sign_time":"1970-01-01","shoptype":null}]
         * ptoken : cb4677c72f50bfef6c10f974288c1164
         * uid : 10003086
         */

        private String ptoken;
        private String uid;
        private List<ListBean> list;

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
             * shop_name : admin123
             * recommend : 1231
             * discount :
             * sign_time : 1970-01-01
             * shoptype : null
             */

            private String shop_name;
            private String recommend;
            private String discount;
            private String sign_time;
            private String shoptype;

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getSign_time() {
                return sign_time;
            }

            public void setSign_time(String sign_time) {
                this.sign_time = sign_time;
            }

            public String getShoptype() {
                return shoptype;
            }

            public void setShoptype(String shoptype) {
                this.shoptype = shoptype;
            }
        }
    }
}

