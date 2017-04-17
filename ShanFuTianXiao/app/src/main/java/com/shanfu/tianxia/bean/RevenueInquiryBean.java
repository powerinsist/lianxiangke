package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/29.
 */
public class RevenueInquiryBean implements Serializable {
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

    public RevenueInquiryData getData() {
        return data;
    }

    public void setData(RevenueInquiryData data) {
        this.data = data;
    }

    private RevenueInquiryData data;


    public class RevenueInquiryData{

        private List<RevenueBean> list;
        private String ptoken;
        private String uid;

        public List<RevenueBean> getList() {
            return list;
        }

        public void setList(List<RevenueBean> list) {
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

    public class RevenueBean{
        private String balance;
        private String cases;
        private String changed_money;

        public String getIncome_time() {
            return income_time;
        }

        public void setIncome_time(String income_time) {
            this.income_time = income_time;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getCases() {
            return cases;
        }

        public void setCases(String cases) {
            this.cases = cases;
        }

        public String getChanged_money() {
            return changed_money;
        }

        public void setChanged_money(String changed_money) {
            this.changed_money = changed_money;
        }

        private String income_time;
    }
}
