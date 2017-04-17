package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/30.
 */
public class OnLineBean implements Serializable {
    private String err_code;
    private String err_msg;

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

    public OnLineData getData() {
        return data;
    }

    public void setData(OnLineData data) {
        this.data = data;
    }

    private OnLineData data;


    public class OnLineData{

        private List<OnLineListBean> list;
        private String ptoken;
        private String uid;
        private String recommend_total;

        public String getRecommend_total() {
            return recommend_total;
        }

        public void setRecommend_total(String recommend_total) {
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

        private String new_people_num;
        private String first_level_num;
        private String second_level_num;


        public List<OnLineListBean> getList() {
            return list;
        }

        public void setList(List<OnLineListBean> list) {
            this.list = list;
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
    }

    public class OnLineListBean{

        private String member_name;

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

        private String member_id;
        private String reg_time;

    }
}

