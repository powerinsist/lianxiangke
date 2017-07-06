package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/9.
 */

public class ZoneProductBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 首页商品列表
     * data : {"list":[{"goods_id":"25","name":"藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg","shop_id":"2","now_price":"50","red":"1","green":"0","yellow":"1","brown":"0","blue":"0","sold":0,"number":4,"totalnumber ":"103"},{"goods_id":"132","name":"谷幽兰维生素E男士保湿滋养霜","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/59446a05c5a6e.jpg","shop_id":"2","now_price":"22.44","red":"1","green":"0","yellow":"0","brown":"0","blue":"0","sold":"1","number":4,"totalnumber ":"103"},{"goods_id":"28","name":" 黑玛瑙配虎眼石水晶手链","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a64f0517fc.jpg","shop_id":"2","now_price":"30","red":"1","green":"0","yellow":"0","brown":"0","blue":"0","sold":0,"number":4,"totalnumber ":"103"},{"goods_id":"133","name":"谷幽兰天然维生素E-C精华霜","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/59446c0be3bb8.jpg","shop_id":"2","now_price":"29.04","red":"1","green":"0","yellow":"0","brown":"0","blue":"0","sold":0,"number":4,"totalnumber ":"103"}],"ptoken":"bacdf0b068e76d2d2cced9a8c77e4664","uid":null}
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
         * list : [{"goods_id":"25","name":"藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg","shop_id":"2","now_price":"50","red":"1","green":"0","yellow":"1","brown":"0","blue":"0","sold":0,"number":4,"totalnumber ":"103"},{"goods_id":"132","name":"谷幽兰维生素E男士保湿滋养霜","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/59446a05c5a6e.jpg","shop_id":"2","now_price":"22.44","red":"1","green":"0","yellow":"0","brown":"0","blue":"0","sold":"1","number":4,"totalnumber ":"103"},{"goods_id":"28","name":" 黑玛瑙配虎眼石水晶手链","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a64f0517fc.jpg","shop_id":"2","now_price":"30","red":"1","green":"0","yellow":"0","brown":"0","blue":"0","sold":0,"number":4,"totalnumber ":"103"},{"goods_id":"133","name":"谷幽兰天然维生素E-C精华霜","shipping":"1","image":"http://shop.lianxiangke.cn/Uploads/image/product/59446c0be3bb8.jpg","shop_id":"2","now_price":"29.04","red":"1","green":"0","yellow":"0","brown":"0","blue":"0","sold":0,"number":4,"totalnumber ":"103"}]
         * ptoken : bacdf0b068e76d2d2cced9a8c77e4664
         * uid : null
         */

        private String ptoken;
        private Object uid;
        private List<ListBean> list;
        private String totalnumber;

        public String getTotalnumber() {
            return totalnumber;
        }

        public void setTotalnumber(String totalnumber) {
            this.totalnumber = totalnumber;
        }

        public String getPtoken() {
            return ptoken;
        }

        public void setPtoken(String ptoken) {
            this.ptoken = ptoken;
        }

        public Object getUid() {
            return uid;
        }

        public void setUid(Object uid) {
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
             * goods_id : 25
             * name : 藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠
             * shipping : 1
             * image : http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg
             * shop_id : 2
             * now_price : 50
             * red : 1
             * green : 0
             * yellow : 1
             * brown : 0
             * blue : 0
             * sold : 0
             * number : 4
             * totalnumber  : 103
             */

            private String goods_id;
            private String name;
            private String shipping;
            private String image;
            private String shop_id;
            private String now_price;
            private String red;
            private String green;
            private String yellow;
            private String brown;
            private String blue;
            private int sold;
            private int number;
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

            public String getGreen() {
                return green;
            }

            public void setGreen(String green) {
                this.green = green;
            }

            public String getYellow() {
                return yellow;
            }

            public void setYellow(String yellow) {
                this.yellow = yellow;
            }

            public String getBrown() {
                return brown;
            }

            public void setBrown(String brown) {
                this.brown = brown;
            }

            public String getBlue() {
                return blue;
            }

            public void setBlue(String blue) {
                this.blue = blue;
            }

            public int getSold() {
                return sold;
            }

            public void setSold(int sold) {
                this.sold = sold;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
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
