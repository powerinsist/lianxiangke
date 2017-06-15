package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/31.
 */
public class SelectCityBean implements Serializable {
    private String err_code;
    private String err_msg;

    public List<SelectCityData> getData() {
        return data;
    }

    public void setData(List<SelectCityData> data) {
        this.data = data;
    }

    private List<SelectCityData> data;

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





    public class SelectCityData{
        private String address;
        private String fencheng;
        private String grade;
        private String id;
        private String img;
        private String shoptype;
        private String juli;
        private String logo;
        private String name;
        private String phone;
        private String shopaddr;
        private String shopname;

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFencheng() {
            return fencheng;
        }

        public void setFencheng(String fencheng) {
            this.fencheng = fencheng;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getJuli() {
            return juli;
        }

        public void setJuli(String juli) {
            this.juli = juli;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getShopaddr() {
            return shopaddr;
        }

        public void setShopaddr(String shopaddr) {
            this.shopaddr = shopaddr;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }


    }
}
