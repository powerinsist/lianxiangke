package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/4/21.
 */

public class MyInComeBean implements Serializable {
    private String err_code;
    private String err_msg;
    private MyInComeData data;
    private InComeBean data_income;

    @Override
    public String toString() {
        return "MyInComeBean{" +
                "err_code='" + err_code + '\'' +
                ", err_msg='" + err_msg + '\'' +
                ", data=" + data +
                ", data_income=" + data_income +
                '}';
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

    public MyInComeData getData() {
        return data;
    }

    public void setData(MyInComeData data) {
        this.data = data;
    }

    public InComeBean getData_income() {
        return data_income;
    }

    public void setData_income(InComeBean data_income) {
        this.data_income = data_income;
    }

    public class MyInComeData{

        private List<InComeBean> list;
        private String ptonken;
        private String uid;

        @Override
        public String toString() {
            return "MyInComeData{" +
                    "list=" + list +
                    ", ptonken='" + ptonken + '\'' +
                    ", uid='" + uid + '\'' +
                    '}';
        }

        public List<InComeBean> getList() {
            return list;
        }

        public void setList(List<InComeBean> list) {
            this.list = list;
        }

        public String getPtonken() {
            return ptonken;
        }

        public void setPtonken(String ptonken) {
            this.ptonken = ptonken;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public class InComeBean{

        private String add_time;
        private String money;
        private String orderid;
        private String status;

        @Override
        public String toString() {
            return "InComeBean{" +
                    "add_time='" + add_time + '\'' +
                    ", money='" + money + '\'' +
                    ", orderid='" + orderid + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
