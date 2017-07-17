package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/30.
 */
public class OnLineBean implements Serializable {

    /**
     * err_code : 200
     * err_msg : success
     * data : {"recommend_total":23,"new_people_num":"0","first_level_num":"12","second_level_num":"11","list":[{"member_name":"*","member_id":"10003686","reg_time":"2017-04-08","phone":"13801387010"},{"member_name":"*","member_id":"10005827","reg_time":"2017-04-12","phone":"13811199506"},{"member_name":"李平*","member_id":"10006461","reg_time":"2017-04-13","phone":"13405389879"},{"member_name":"*","member_id":"10009111","reg_time":"2017-04-24","phone":"18326891726"},{"member_name":"*","member_id":"10009112","reg_time":"2017-04-24","phone":"15726178068"},{"member_name":"张某*","member_id":"10009170","reg_time":"2017-04-24","phone":"18201424802"},{"member_name":"*","member_id":"10009478","reg_time":"2017-04-25","phone":"13716536883"},{"member_name":"党玉*","member_id":"10010837","reg_time":"2017-05-02","phone":"15321208718"},{"member_name":"史庆*","member_id":"10012345","reg_time":"2017-05-04","phone":"15864449797"},{"member_name":"张静*","member_id":"10012590","reg_time":"2017-05-06","phone":"15650725287"},{"member_name":"朱云*","member_id":"10012815","reg_time":"2017-05-11","phone":"18518185346"},{"member_name":"周德*","member_id":"10012873","reg_time":"2017-05-13","phone":"13363639754"},{"member_name":"秦艳*","member_id":"10012998","reg_time":"2017-05-22","phone":"15373550655"},{"member_name":"李东*","member_id":"10013026","reg_time":"2017-05-23","phone":"18514783739"},{"member_name":"*","member_id":"10013076","reg_time":"2017-05-26","phone":"18735168038"},{"member_name":"刘春*","member_id":"10013105","reg_time":"2017-06-05","phone":"18701634544"},{"member_name":"李凯*","member_id":"10013106","reg_time":"2017-06-06","phone":"15858132538"},{"member_name":"*","member_id":"10013107","reg_time":"2017-06-06","phone":"17606716561"},{"member_name":"徐振*","member_id":"10013110","reg_time":"2017-06-06","phone":"15830066956"},{"member_name":"周德*","member_id":"10013126","reg_time":"2017-06-10","phone":"13381014584"},{"member_name":"徐振*","member_id":"10013840","reg_time":"2017-07-04","phone":"17600149956           3333"},{"member_name":"崔亚*","member_id":"10014033","reg_time":"2017-07-07","phone":"15201619770"},{"member_name":"*","member_id":"10014122","reg_time":"2017-07-07","phone":"13315991583"}],"ptoken":"062064d29103fc030a4d2fb78b7dc493","uid":"10003086"}
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
         * recommend_total : 23
         * new_people_num : 0
         * first_level_num : 12
         * second_level_num : 11
         * list : [{"member_name":"*","member_id":"10003686","reg_time":"2017-04-08","phone":"13801387010"},{"member_name":"*","member_id":"10005827","reg_time":"2017-04-12","phone":"13811199506"},{"member_name":"李平*","member_id":"10006461","reg_time":"2017-04-13","phone":"13405389879"},{"member_name":"*","member_id":"10009111","reg_time":"2017-04-24","phone":"18326891726"},{"member_name":"*","member_id":"10009112","reg_time":"2017-04-24","phone":"15726178068"},{"member_name":"张某*","member_id":"10009170","reg_time":"2017-04-24","phone":"18201424802"},{"member_name":"*","member_id":"10009478","reg_time":"2017-04-25","phone":"13716536883"},{"member_name":"党玉*","member_id":"10010837","reg_time":"2017-05-02","phone":"15321208718"},{"member_name":"史庆*","member_id":"10012345","reg_time":"2017-05-04","phone":"15864449797"},{"member_name":"张静*","member_id":"10012590","reg_time":"2017-05-06","phone":"15650725287"},{"member_name":"朱云*","member_id":"10012815","reg_time":"2017-05-11","phone":"18518185346"},{"member_name":"周德*","member_id":"10012873","reg_time":"2017-05-13","phone":"13363639754"},{"member_name":"秦艳*","member_id":"10012998","reg_time":"2017-05-22","phone":"15373550655"},{"member_name":"李东*","member_id":"10013026","reg_time":"2017-05-23","phone":"18514783739"},{"member_name":"*","member_id":"10013076","reg_time":"2017-05-26","phone":"18735168038"},{"member_name":"刘春*","member_id":"10013105","reg_time":"2017-06-05","phone":"18701634544"},{"member_name":"李凯*","member_id":"10013106","reg_time":"2017-06-06","phone":"15858132538"},{"member_name":"*","member_id":"10013107","reg_time":"2017-06-06","phone":"17606716561"},{"member_name":"徐振*","member_id":"10013110","reg_time":"2017-06-06","phone":"15830066956"},{"member_name":"周德*","member_id":"10013126","reg_time":"2017-06-10","phone":"13381014584"},{"member_name":"徐振*","member_id":"10013840","reg_time":"2017-07-04","phone":"17600149956           3333"},{"member_name":"崔亚*","member_id":"10014033","reg_time":"2017-07-07","phone":"15201619770"},{"member_name":"*","member_id":"10014122","reg_time":"2017-07-07","phone":"13315991583"}]
         * ptoken : 062064d29103fc030a4d2fb78b7dc493
         * uid : 10003086
         */

        private int recommend_total;
        private String new_people_num;
        private String first_level_num;
        private String second_level_num;
        private String ptoken;
        private String uid;
        private List<ListBean> list;

        public int getRecommend_total() {
            return recommend_total;
        }

        public void setRecommend_total(int recommend_total) {
            this.recommend_total = recommend_total;
        }

        public String getNew_people_num() {
            return new_people_num;
        }

        public void setNew_people_num(String new_people_num) {
            this.new_people_num = new_people_num;
        }

        public String getFirst_level_num() {
            return first_level_num;
        }

        public void setFirst_level_num(String first_level_num) {
            this.first_level_num = first_level_num;
        }

        public String getSecond_level_num() {
            return second_level_num;
        }

        public void setSecond_level_num(String second_level_num) {
            this.second_level_num = second_level_num;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * member_name : *
             * member_id : 10003686
             * reg_time : 2017-04-08
             * phone : 13801387010
             */

            private String member_name;
            private String member_id;
            private String reg_time;
            private String phone;

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getReg_time() {
                return reg_time;
            }

            public void setReg_time(String reg_time) {
                this.reg_time = reg_time;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}

