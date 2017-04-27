package com.shanfu.tianxia.bean;

/**
 * Created by qing on 2017/3/1.
 * 首页中间广告位
 */
public class MiddleAdvertisementBean {


    public MiddleData data;
    private String err_code;
    private String err_msg;

    public MiddleData getData() {
        return data;
    }

    public void setData(MiddleData data) {
        this.data = data;
    }

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

    public class MiddleData{
        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String aid;
        public String description;
        public String url;
        public String version;
        public String img;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }


    }

}
