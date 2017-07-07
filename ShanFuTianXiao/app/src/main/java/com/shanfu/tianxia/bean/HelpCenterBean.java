package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/7/7.
 */

public class HelpCenterBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : 帮助列表
     * data : {"list":[{"title":"联系我们","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/contactus.html"},{"title":"费用明细","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/cost.html"},{"title":"公司简介","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/intro.html"},{"title":"联享客介绍","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/introduce.html"},{"title":"常见问题","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/question.html"},{"title":"联享客会员注册指南","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/registration_process.html"},{"title":"商户注册指南","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/user_registration_process.html"}],"ptoken":"66ad43e1f3bec680a1dd6b519c317c59","uid":"10012998"}
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
         * list : [{"title":"联系我们","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/contactus.html"},{"title":"费用明细","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/cost.html"},{"title":"公司简介","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/intro.html"},{"title":"联享客介绍","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/introduce.html"},{"title":"常见问题","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/question.html"},{"title":"联享客会员注册指南","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/registration_process.html"},{"title":"商户注册指南","url":"http://shop.lianxiangke.cn/Uploads/image/cache/html/user_registration_process.html"}]
         * ptoken : 66ad43e1f3bec680a1dd6b519c317c59
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
             * title : 联系我们
             * url : http://shop.lianxiangke.cn/Uploads/image/cache/html/contactus.html
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
