package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/7/12.
 */

public class ZoneSelectBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 商品列表
     * data : {"list":[{"goods_id":"35","name":"移动电源","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591be7df00eb1.jpg","shop_id":"3","now_price":"33","red":"1","sold":0,"number":4,"totalnumber ":"8"},{"goods_id":"141","name":"KF-TAS1601D 塔扇/落地扇/电风扇","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/594775b7352a2.png","shop_id":"2","now_price":"138","red":"2","sold":0,"number":4,"totalnumber ":"8"},{"goods_id":"36","name":"金刚侠金属超薄移动电源","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591e6a7602051.jpg","shop_id":"3","now_price":"36","red":"1","sold":0,"number":4,"totalnumber ":"8"},{"goods_id":"37","name":"力勤A9无线蓝牙音箱","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591bf5e16be6e.jpg","shop_id":"2","now_price":"78","red":"1","sold":0,"number":4,"totalnumber ":"8"}],"ptoken":"b4157aa1a2b351f014c52b5462ce00cc","uid":"10012998"}
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
         * list : [{"goods_id":"35","name":"移动电源","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591be7df00eb1.jpg","shop_id":"3","now_price":"33","red":"1","sold":0,"number":4,"totalnumber ":"8"},{"goods_id":"141","name":"KF-TAS1601D 塔扇/落地扇/电风扇","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/594775b7352a2.png","shop_id":"2","now_price":"138","red":"2","sold":0,"number":4,"totalnumber ":"8"},{"goods_id":"36","name":"金刚侠金属超薄移动电源","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591e6a7602051.jpg","shop_id":"3","now_price":"36","red":"1","sold":0,"number":4,"totalnumber ":"8"},{"goods_id":"37","name":"力勤A9无线蓝牙音箱","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591bf5e16be6e.jpg","shop_id":"2","now_price":"78","red":"1","sold":0,"number":4,"totalnumber ":"8"}]
         * ptoken : b4157aa1a2b351f014c52b5462ce00cc
         * uid : 10012998
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
             * goods_id : 35
             * name : 移动电源
             * shipping : 1
             * image : http://shop.lianxiangke.cn/Uploads/image/product/591be7df00eb1.jpg
             * shop_id : 3
             * now_price : 33
             * red : 1
             * sold : 0
             * number : 4
             * totalnumber  : 8
             */

            private String goods_id;
            private String name;
            private String shipping;
            private String image;
            private String shop_id;
            private String now_price;
            private String red;
            private String sold;
            private String number;
            private int totalnumber;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getShipping() {
                return shipping;
            }

            public void setShipping(String shipping) {
                this.shipping = shipping;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getShop_id() {
                return shop_id;
            }

            public void setShop_id(String shop_id) {
                this.shop_id = shop_id;
            }

            public String getNow_price() {
                return now_price;
            }

            public void setNow_price(String now_price) {
                this.now_price = now_price;
            }

            public String getRed() {
                return red;
            }

            public void setRed(String red) {
                this.red = red;
            }

            public String getSold() {
                return sold;
            }

            public void setSold(String sold) {
                this.sold = sold;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public int getTotalnumber() {
                return totalnumber;
            }

            public void setTotalnumber(int totalnumber) {
                this.totalnumber = totalnumber;
            }
        }
    }
}
