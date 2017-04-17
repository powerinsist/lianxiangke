package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qing on 2017/3/14.
 * 店铺评论列表item bean
 */
public class CommodityDetailsItemBean implements Serializable{

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

    public ArrayList<ItemBean> getData() {
        return data;
    }

    public void setData(ArrayList<ItemBean> data) {
        this.data = data;
    }

    private ArrayList<ItemBean> data;


    public class ItemBean{
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        private String content;
        private String id;
        private String nickname;
        private String pics;
        private String score;
        private String time;
    }
}
