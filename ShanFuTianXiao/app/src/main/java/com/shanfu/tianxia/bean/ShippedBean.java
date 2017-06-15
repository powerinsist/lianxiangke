package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/16.
 */

public class ShippedBean implements Serializable {
    private String status;
    private String imageurl;
    private String shopname;
    private String shopcount;
    private String lxpcount;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopcount() {
        return shopcount;
    }

    public void setShopcount(String shopcount) {
        this.shopcount = shopcount;
    }

    public String getLxpcount() {
        return lxpcount;
    }

    public void setLxpcount(String lxpcount) {
        this.lxpcount = lxpcount;
    }
}
