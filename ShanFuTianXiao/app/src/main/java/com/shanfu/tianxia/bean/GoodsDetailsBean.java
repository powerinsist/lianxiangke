package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/7/4.
 */

public class GoodsDetailsBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 商品详情!
     * data : {"good_detail":{"jiben":{"goods_id":"25","shipping_price":"0","name":"藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠","model":"1-001","minimum":"1","quantity":"99","now_price":"50","price":"88.00","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg","shipping":"1","shop_id":"2","weight":"0.00000000","weight_class_id":"2","length_class_id":"1","length":"12.50000000","width":"12.50000000","height":"5.50000000","red":"1","blue":"0","yellow":"1","green":"0","brown":"0","location":"浙江金华","description":"&lt;p&gt;【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠&lt;br /&gt;\r\n【材质】合成绿松石+藏式松石配件&lt;br /&gt;\r\n【规格】6*108颗 总长46cm,绕手三至四圈。&lt;br /&gt;\r\n【功效】：招财、辟邪、护身、挡灾&lt;br /&gt;\r\n【礼物用途】：自戴，送人，收藏&lt;br /&gt;\r\n【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。&lt;br /&gt;\r\n【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。&lt;/p&gt;\r\n\r\n&lt;p&gt;&amp;nbsp;&lt;/p&gt;\r\n","summary":"【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠\r\n【材质】合成绿松石+藏式松石配件\r\n【规格】6*108颗 总长46cm,绕手三至四圈。\r\n【功效】：招财、辟邪、护身、挡灾\r\n【礼物用途】：自戴，送人，收藏\r\n【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。\r\n【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。","meta_description":"","meta_keyword":"","sold":"0"},"images":{"image0":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d65e22af.jpg","image1":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d829c973.jpg","image2":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d8eaba2a.jpg","image3":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d9c278e9.jpg","image4":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dac3d1af.jpg","image5":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dd35a489.jpg"},"sku_attr":[{"goods_option_id":"105","option_id":"7","name":"价格","type":"radio","option_value":[{"goods_option_value_id":"250","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"251","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"252","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"}],"required":"0"}]},"ptoken":"379d99fa5f5e99b10a4a8ca15db89eed","uid":"10012998"}
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
         * good_detail : {"jiben":{"goods_id":"25","shipping_price":"0","name":"藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠","model":"1-001","minimum":"1","quantity":"99","now_price":"50","price":"88.00","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg","shipping":"1","shop_id":"2","weight":"0.00000000","weight_class_id":"2","length_class_id":"1","length":"12.50000000","width":"12.50000000","height":"5.50000000","red":"1","blue":"0","yellow":"1","green":"0","brown":"0","location":"浙江金华","description":"&lt;p&gt;【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠&lt;br /&gt;\r\n【材质】合成绿松石+藏式松石配件&lt;br /&gt;\r\n【规格】6*108颗 总长46cm,绕手三至四圈。&lt;br /&gt;\r\n【功效】：招财、辟邪、护身、挡灾&lt;br /&gt;\r\n【礼物用途】：自戴，送人，收藏&lt;br /&gt;\r\n【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。&lt;br /&gt;\r\n【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。&lt;/p&gt;\r\n\r\n&lt;p&gt;&amp;nbsp;&lt;/p&gt;\r\n","summary":"【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠\r\n【材质】合成绿松石+藏式松石配件\r\n【规格】6*108颗 总长46cm,绕手三至四圈。\r\n【功效】：招财、辟邪、护身、挡灾\r\n【礼物用途】：自戴，送人，收藏\r\n【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。\r\n【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。","meta_description":"","meta_keyword":"","sold":"0"},"images":{"image0":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d65e22af.jpg","image1":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d829c973.jpg","image2":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d8eaba2a.jpg","image3":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d9c278e9.jpg","image4":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dac3d1af.jpg","image5":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dd35a489.jpg"},"sku_attr":[{"goods_option_id":"105","option_id":"7","name":"价格","type":"radio","option_value":[{"goods_option_value_id":"250","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"251","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"252","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"}],"required":"0"}]}
         * ptoken : 379d99fa5f5e99b10a4a8ca15db89eed
         * uid : 10012998
         */

        private GoodDetailBean good_detail;
        private String ptoken;
        private String uid;

        public GoodDetailBean getGood_detail() {
            return good_detail;
        }

        public void setGood_detail(GoodDetailBean good_detail) {
            this.good_detail = good_detail;
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

        public static class GoodDetailBean {
            /**
             * jiben : {"goods_id":"25","shipping_price":"0","name":"藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠","model":"1-001","minimum":"1","quantity":"99","now_price":"50","price":"88.00","image":"http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg","shipping":"1","shop_id":"2","weight":"0.00000000","weight_class_id":"2","length_class_id":"1","length":"12.50000000","width":"12.50000000","height":"5.50000000","red":"1","blue":"0","yellow":"1","green":"0","brown":"0","location":"浙江金华","description":"&lt;p&gt;【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠&lt;br /&gt;\r\n【材质】合成绿松石+藏式松石配件&lt;br /&gt;\r\n【规格】6*108颗 总长46cm,绕手三至四圈。&lt;br /&gt;\r\n【功效】：招财、辟邪、护身、挡灾&lt;br /&gt;\r\n【礼物用途】：自戴，送人，收藏&lt;br /&gt;\r\n【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。&lt;br /&gt;\r\n【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。&lt;/p&gt;\r\n\r\n&lt;p&gt;&amp;nbsp;&lt;/p&gt;\r\n","summary":"【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠\r\n【材质】合成绿松石+藏式松石配件\r\n【规格】6*108颗 总长46cm,绕手三至四圈。\r\n【功效】：招财、辟邪、护身、挡灾\r\n【礼物用途】：自戴，送人，收藏\r\n【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。\r\n【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。","meta_description":"","meta_keyword":"","sold":"0"}
             * images : {"image0":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d65e22af.jpg","image1":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d829c973.jpg","image2":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d8eaba2a.jpg","image3":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d9c278e9.jpg","image4":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dac3d1af.jpg","image5":"http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dd35a489.jpg"}
             * sku_attr : [{"goods_option_id":"105","option_id":"7","name":"价格","type":"radio","option_value":[{"goods_option_value_id":"250","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"251","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"252","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"}],"required":"0"}]
             */

            private JibenBean jiben;
            private ImagesBean images;
            private List<SkuAttrBean> sku_attr;

            public JibenBean getJiben() {
                return jiben;
            }

            public void setJiben(JibenBean jiben) {
                this.jiben = jiben;
            }

            public ImagesBean getImages() {
                return images;
            }

            public void setImages(ImagesBean images) {
                this.images = images;
            }

            public List<SkuAttrBean> getSku_attr() {
                return sku_attr;
            }

            public void setSku_attr(List<SkuAttrBean> sku_attr) {
                this.sku_attr = sku_attr;
            }

            public static class JibenBean {
                /**
                 * goods_id : 25
                 * shipping_price : 0
                 * name : 藏式民族风天然原矿绿松石108算盘珠手串手链水晶宝石本命年佛珠
                 * model : 1-001
                 * minimum : 1
                 * quantity : 99
                 * now_price : 50
                 * price : 88.00
                 * image : http://shop.lianxiangke.cn/Uploads/image/product/591a54b562e9a.jpg
                 * shipping : 1
                 * shop_id : 2
                 * weight : 0.00000000
                 * weight_class_id : 2
                 * length_class_id : 1
                 * length : 12.50000000
                 * width : 12.50000000
                 * height : 5.50000000
                 * red : 1
                 * blue : 0
                 * yellow : 1
                 * green : 0
                 * brown : 0
                 * location : 浙江金华
                 * description : &lt;p&gt;【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠&lt;br /&gt;
                 【材质】合成绿松石+藏式松石配件&lt;br /&gt;
                 【规格】6*108颗 总长46cm,绕手三至四圈。&lt;br /&gt;
                 【功效】：招财、辟邪、护身、挡灾&lt;br /&gt;
                 【礼物用途】：自戴，送人，收藏&lt;br /&gt;
                 【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。&lt;br /&gt;
                 【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。&lt;/p&gt;

                 &lt;p&gt;&amp;nbsp;&lt;/p&gt;

                 * summary : 【品名】6*108颗合成绿松石佛珠手链 配三眼天珠热销手链佛珠
                 【材质】合成绿松石+藏式松石配件
                 【规格】6*108颗 总长46cm,绕手三至四圈。
                 【功效】：招财、辟邪、护身、挡灾
                 【礼物用途】：自戴，送人，收藏
                 【产品用途】：6*108颗 绿松石佛珠手链，用以手饰配带，以达到辟邪保平安之效。
                 【推荐理由】6*108颗绿松石佛珠手链手串，此款式新颖，为独家所创，销量好。
                 * meta_description :
                 * meta_keyword :
                 * sold : 0
                 */

                private String goods_id;
                private String shipping_price;
                private String name;
                private String model;
                private String minimum;
                private String quantity;
                private String now_price;
                private String price;
                private String image;
                private String shipping;
                private String shop_id;
                private String weight;
                private String weight_class_id;
                private String length_class_id;
                private String length;
                private String width;
                private String height;
                private String red;
                private String blue;
                private String yellow;
                private String green;
                private String brown;
                private String location;
                private String description;
                private String summary;
                private String meta_description;
                private String meta_keyword;
                private String sold;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getShipping_price() {
                    return shipping_price;
                }

                public void setShipping_price(String shipping_price) {
                    this.shipping_price = shipping_price;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }

                public String getMinimum() {
                    return minimum;
                }

                public void setMinimum(String minimum) {
                    this.minimum = minimum;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getNow_price() {
                    return now_price;
                }

                public void setNow_price(String now_price) {
                    this.now_price = now_price;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getShipping() {
                    return shipping;
                }

                public void setShipping(String shipping) {
                    this.shipping = shipping;
                }

                public String getShop_id() {
                    return shop_id;
                }

                public void setShop_id(String shop_id) {
                    this.shop_id = shop_id;
                }

                public String getWeight() {
                    return weight;
                }

                public void setWeight(String weight) {
                    this.weight = weight;
                }

                public String getWeight_class_id() {
                    return weight_class_id;
                }

                public void setWeight_class_id(String weight_class_id) {
                    this.weight_class_id = weight_class_id;
                }

                public String getLength_class_id() {
                    return length_class_id;
                }

                public void setLength_class_id(String length_class_id) {
                    this.length_class_id = length_class_id;
                }

                public String getLength() {
                    return length;
                }

                public void setLength(String length) {
                    this.length = length;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getRed() {
                    return red;
                }

                public void setRed(String red) {
                    this.red = red;
                }

                public String getBlue() {
                    return blue;
                }

                public void setBlue(String blue) {
                    this.blue = blue;
                }

                public String getYellow() {
                    return yellow;
                }

                public void setYellow(String yellow) {
                    this.yellow = yellow;
                }

                public String getGreen() {
                    return green;
                }

                public void setGreen(String green) {
                    this.green = green;
                }

                public String getBrown() {
                    return brown;
                }

                public void setBrown(String brown) {
                    this.brown = brown;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public String getMeta_description() {
                    return meta_description;
                }

                public void setMeta_description(String meta_description) {
                    this.meta_description = meta_description;
                }

                public String getMeta_keyword() {
                    return meta_keyword;
                }

                public void setMeta_keyword(String meta_keyword) {
                    this.meta_keyword = meta_keyword;
                }

                public String getSold() {
                    return sold;
                }

                public void setSold(String sold) {
                    this.sold = sold;
                }
            }

            public static class ImagesBean {
                /**
                 * image0 : http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d65e22af.jpg
                 * image1 : http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d829c973.jpg
                 * image2 : http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d8eaba2a.jpg
                 * image3 : http://shop.lianxiangke.cn/Uploads/image/gallery/591a5d9c278e9.jpg
                 * image4 : http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dac3d1af.jpg
                 * image5 : http://shop.lianxiangke.cn/Uploads/image/gallery/591a5dd35a489.jpg
                 */

                private String image0;
                private String image1;
                private String image2;
                private String image3;
                private String image4;
                private String image5;

                public String getImage0() {
                    return image0;
                }

                public void setImage0(String image0) {
                    this.image0 = image0;
                }

                public String getImage1() {
                    return image1;
                }

                public void setImage1(String image1) {
                    this.image1 = image1;
                }

                public String getImage2() {
                    return image2;
                }

                public void setImage2(String image2) {
                    this.image2 = image2;
                }

                public String getImage3() {
                    return image3;
                }

                public void setImage3(String image3) {
                    this.image3 = image3;
                }

                public String getImage4() {
                    return image4;
                }

                public void setImage4(String image4) {
                    this.image4 = image4;
                }

                public String getImage5() {
                    return image5;
                }

                public void setImage5(String image5) {
                    this.image5 = image5;
                }
            }

            public static class SkuAttrBean {
                /**
                 * goods_option_id : 105
                 * option_id : 7
                 * name : 价格
                 * type : radio
                 * option_value : [{"goods_option_value_id":"250","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"251","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"},{"goods_option_value_id":"252","option_value_id":"24","name":"10000","image":"","price":"0.0000","price_prefix":"+"}]
                 * required : 0
                 */

                private String goods_option_id;
                private String option_id;
                private String name;
                private String type;
                private String required;
                private List<OptionValueBean> option_value;

                public String getGoods_option_id() {
                    return goods_option_id;
                }

                public void setGoods_option_id(String goods_option_id) {
                    this.goods_option_id = goods_option_id;
                }

                public String getOption_id() {
                    return option_id;
                }

                public void setOption_id(String option_id) {
                    this.option_id = option_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getRequired() {
                    return required;
                }

                public void setRequired(String required) {
                    this.required = required;
                }

                public List<OptionValueBean> getOption_value() {
                    return option_value;
                }

                public void setOption_value(List<OptionValueBean> option_value) {
                    this.option_value = option_value;
                }

                public static class OptionValueBean {
                    /**
                     * goods_option_value_id : 250
                     * option_value_id : 24
                     * name : 10000
                     * image :
                     * price : 0.0000
                     * price_prefix : +
                     */

                    private String goods_option_value_id;
                    private String option_value_id;
                    private String name;
                    private String image;
                    private String price;
                    private String price_prefix;

                    public String getGoods_option_value_id() {
                        return goods_option_value_id;
                    }

                    public void setGoods_option_value_id(String goods_option_value_id) {
                        this.goods_option_value_id = goods_option_value_id;
                    }

                    public String getOption_value_id() {
                        return option_value_id;
                    }

                    public void setOption_value_id(String option_value_id) {
                        this.option_value_id = option_value_id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getImage() {
                        return image;
                    }

                    public void setImage(String image) {
                        this.image = image;
                    }

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getPrice_prefix() {
                        return price_prefix;
                    }

                    public void setPrice_prefix(String price_prefix) {
                        this.price_prefix = price_prefix;
                    }
                }
            }
        }
    }
}
