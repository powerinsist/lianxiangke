package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by qing on 2017/3/15.
 * 搜索排序的初始化
 */
public class InitSearchBean implements Serializable{

    private InitData data;
    private String err_code;

    public InitData getData() {
        return data;
    }

    public void setData(InitData data) {
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

    private String err_msg;
    public class InitData{
        public ArrayList<InitBean> getArea() {
            return area;
        }

        public void setArea(ArrayList<InitBean> area) {
            this.area = area;
        }

        public ArrayList<InitBean> getCategory() {
            return category;
        }

        public void setCategory(ArrayList<InitBean> category) {
            this.category = category;
        }

        public ArrayList<InitBean> getPaixue() {
            return paixue;
        }

        public void setPaixue(ArrayList<InitBean> paixue) {
            this.paixue = paixue;
        }

        private ArrayList<InitBean> area;
        private ArrayList<InitBean> category;
        private ArrayList<InitBean> paixue;
    }

    public class InitBean{
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String name;
    }

}
