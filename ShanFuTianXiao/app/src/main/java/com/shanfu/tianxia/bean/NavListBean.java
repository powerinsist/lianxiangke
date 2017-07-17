package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/7/10.
 */

public class NavListBean implements Serializable{

    /**
     * err_code : 200
     * err_msg : 类别列表
     * data : {"list":[{"id":"1","name":"优品精选","meta_description":"优品精选分类","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/56643d27e0841.jpg"},{"id":"2","name":"数码手机","meta_description":"数码家电","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/94615832153582316.png"},{"id":"3","name":"家居厨具","meta_description":"家居百货","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/818385164081607993.png"},{"id":"4","name":"服装箱包","meta_description":"服饰箱包","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/94167755862604407.png"},{"id":"5","name":"食品保健","meta_description":"食品保健","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/321694676105123195.png"},{"id":"8","name":"运动玩具","meta_description":"运动玩具","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/5664401375ec6.jpg"},{"id":"9","name":"美妆个护","meta_description":"美妆个护","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/127358580977518328.png"},{"id":"10","name":"珠宝艺术品","meta_description":"珠宝艺术品","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/367776774149090858.png"}],"ptoken":"f51c2dba562c7688a7d6f5822fb4cb5a","uid":"10012998"}
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
         * list : [{"id":"1","name":"优品精选","meta_description":"优品精选分类","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/56643d27e0841.jpg"},{"id":"2","name":"数码手机","meta_description":"数码家电","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/94615832153582316.png"},{"id":"3","name":"家居厨具","meta_description":"家居百货","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/818385164081607993.png"},{"id":"4","name":"服装箱包","meta_description":"服饰箱包","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/94167755862604407.png"},{"id":"5","name":"食品保健","meta_description":"食品保健","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/321694676105123195.png"},{"id":"8","name":"运动玩具","meta_description":"运动玩具","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/5664401375ec6.jpg"},{"id":"9","name":"美妆个护","meta_description":"美妆个护","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/127358580977518328.png"},{"id":"10","name":"珠宝艺术品","meta_description":"珠宝艺术品","pid":"0","images":"http://shop.lianxiangke.cn/Uploads/image/product/367776774149090858.png"}]
         * ptoken : f51c2dba562c7688a7d6f5822fb4cb5a
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
             * id : 1
             * name : 优品精选
             * meta_description : 优品精选分类
             * pid : 0
             * images : http://shop.lianxiangke.cn/Uploads/image/product/56643d27e0841.jpg
             */

            private String id;
            private String name;
            private String meta_description;
            private String pid;
            private String images;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMeta_description() {
                return meta_description;
            }

            public void setMeta_description(String meta_description) {
                this.meta_description = meta_description;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }
        }
    }
}
