package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/18.
 */

public class ZoneImageUrlBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 轮播列表
     * data : {"list":{"images0":"http://shop.lianxiangke.cn/Uploads/image/slider/55b63503364c0.jpg","images1":"http://shop.lianxiangke.cn/Uploads/image/slider/55b6350dee93f.jpg","images2":"http://shop.lianxiangke.cn/Uploads/image/slider/591b17eaa599f.jpg"},"ptoken":"b97d32899d465a03de5839da900c5fc7","uid":null}
     */

    private String err_code;
    private String err_msg;
    private ImageBean data;

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

    public ImageBean getData() {
        return data;
    }

    public void setData(ImageBean data) {
        this.data = data;
    }


    public  class ImageBean{
        private String ptoken;
        private String uid;
        private ImageUrls list;

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

        public ImageUrls getList() {
            return list;
        }

        public void setList(ImageUrls list) {
            this.list = list;
        }
    }

   public class ImageUrls{
        String images0;
        String images1;
        String images2;

        public String getImages0() {
            return images0;
        }

        public void setImages0(String images0) {
            this.images0 = images0;
        }

        public String getImages1() {
            return images1;
        }

        public void setImages1(String images1) {
            this.images1 = images1;
        }

        public String getImages2() {
            return images2;
        }

        public void setImages2(String images2) {
            this.images2 = images2;
        }

}


}