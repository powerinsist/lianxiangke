package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/29.
 */
public class QueryIncomeBean implements Serializable {
    private String err_code;
    private String err_msg;

    public QueryIncomeData getData() {
        return data;
    }

    public void setData(QueryIncomeData data) {
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


    private QueryIncomeData data;


    public class QueryIncomeData{

        private List<QueryBean> list;
        private String ptoken;
        private String uid;

        public List<QueryBean> getList() {
            return list;
        }

        public void setList(List<QueryBean> list) {
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

    public class QueryBean{
       private String income_money;
        private String income_source;
        private String income_time;

        public String getIncome_money() {
            return income_money;
        }

        public void setIncome_money(String income_money) {
            this.income_money = income_money;
        }

        public String getIncome_source() {
            return income_source;
        }

        public void setIncome_source(String income_source) {
            this.income_source = income_source;
        }

        public String getIncome_time() {
            return income_time;
        }

        public void setIncome_time(String income_time) {
            this.income_time = income_time;
        }

        public String getAccumulated_income() {
            return accumulated_income;
        }

        public void setAccumulated_income(String accumulated_income) {
            this.accumulated_income = accumulated_income;
        }

        private String accumulated_income;
    }
}
