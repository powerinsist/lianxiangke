package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/29.
 */

public class SaoMaCodeBean implements Serializable {

    /**
     * id : 100143
     * oid_userno : 2017052610968493
     * user_id : 18301496525
     * name_user : 杜婧
     * shopname : 联享客
     */

    private String id;
    private String oid_userno;
    private String user_id;
    private String name_user;
    private String shopname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOid_userno() {
        return oid_userno;
    }

    public void setOid_userno(String oid_userno) {
        this.oid_userno = oid_userno;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
}
