package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/7/7.
 */

public class PlatformAnnouncementBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 公告列表
     * data : {"list":[{"title":"联享客消费商考核管理公告","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/announcement.html"},{"title":"联享客新版APP上线公告","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/app_online_bulletin.html"},{"title":"联享客升级2.0公告","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/notice2.0.html"},{"title":"联享客升级期间注意事项","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/upgrade_note.html"}],"ptoken":"9d9bf81bb7510ea329b8ceb27cecdae6","uid":"10012998"}
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
         * list : [{"title":"联享客消费商考核管理公告","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/announcement.html"},{"title":"联享客新版APP上线公告","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/app_online_bulletin.html"},{"title":"联享客升级2.0公告","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/notice2.0.html"},{"title":"联享客升级期间注意事项","pic":"","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/upgrade_note.html"}]
         * ptoken : 9d9bf81bb7510ea329b8ceb27cecdae6
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
             * title : 联享客消费商考核管理公告
             * pic :
             * url : http://shop.lianxiangke.cn/Uploads/image/cache/html/announcement.html
             */

            private String title;
            private String pic;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
