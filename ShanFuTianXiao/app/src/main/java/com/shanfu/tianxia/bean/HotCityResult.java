package com.shanfu.tianxia.bean;

import java.util.List;

/**
 * Created by qing on 2017/3/1.
 * 热门城市近回值
 */
public class HotCityResult {

    public List<DataBean> data;
    public String message;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HotCityStatus getStatus() {
        return status;
    }

    public void setStatus(HotCityStatus status) {
        this.status = status;
    }

    public HotCityStatus status;
    public class HotCityStatus {
        public String getSucceed() {
            return succeed;
        }

        public void setSucceed(String succeed) {
            this.succeed = succeed;
        }

        public String succeed;
    }
    public class DataBean{
        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String area_id;
        public String area_name;
        public String initial;
    }

}
