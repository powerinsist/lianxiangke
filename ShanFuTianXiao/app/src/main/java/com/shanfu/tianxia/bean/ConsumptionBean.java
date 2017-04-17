package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/30.
 */
public class ConsumptionBean implements Serializable {
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

    public ConsumptionData getData() {
        return data;
    }

    public void setData(ConsumptionData data) {
        this.data = data;
    }

    private ConsumptionData data;


    public class ConsumptionData{

        private List<ConsumptionDataListBean> list;
        private String ptoken;
        private String uid;

        public List<ConsumptionDataListBean> getList() {
            return list;
        }

        public void setList(List<ConsumptionDataListBean> list) {
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

    public class ConsumptionDataListBean{
        private String consume_money;

        public String getConsume_money() {
            return consume_money;
        }

        public void setConsume_money(String consume_money) {
            this.consume_money = consume_money;
        }

        public String getConsume_shop() {
            return consume_shop;
        }

        public void setConsume_shop(String consume_shop) {
            this.consume_shop = consume_shop;
        }

        public String getConsume_time() {
            return consume_time;
        }

        public void setConsume_time(String consume_time) {
            this.consume_time = consume_time;
        }

        public String getRemission_coins() {
            return remission_coins;
        }

        public void setRemission_coins(String remission_coins) {
            this.remission_coins = remission_coins;
        }

        private String consume_shop;
        private String consume_time;
        private String remission_coins;
    }
}

