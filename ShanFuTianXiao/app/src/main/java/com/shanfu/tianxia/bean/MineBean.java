package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by qing on 2017/3/30.
 */
public class MineBean implements Serializable {
    private String err_code;

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

    public MineData getData() {
        return data;
    }

    public void setData(MineData data) {
        this.data = data;
    }

    private String err_msg;
    private MineData data;
    public class MineData{
        private String available_money;
        private String avater;
        private String income;
        private String kintegral;
        private String nickname;
        private String ptoken;
        private String bankcard;
        private String level;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getBankcard() {
            return bankcard;
        }

        public void setBankcard(String bankcard) {
            this.bankcard = bankcard;
        }

        public String getAvailable_money() {
            return available_money;
        }

        public void setAvailable_money(String available_money) {
            this.available_money = available_money;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getKintegral() {
            return kintegral;
        }

        public void setKintegral(String kintegral) {
            this.kintegral = kintegral;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getYreturnmoney() {
            return yreturnmoney;
        }

        public void setYreturnmoney(String yreturnmoney) {
            this.yreturnmoney = yreturnmoney;
        }

        private String uid;
        private String yreturnmoney;
    }
}
