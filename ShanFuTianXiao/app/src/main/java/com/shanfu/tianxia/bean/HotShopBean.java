package com.shanfu.tianxia.bean;

import com.shanfu.tianxia.model.ProductData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/3/2.
 * 首页下部商品热卖
 */
public class HotShopBean implements Serializable{

    private List<ProductData> data;


    public List<ProductData> getData() {
        return data;
    }

    public void setData(List<ProductData> data) {
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

    private String err_code;
    private String err_msg;







}
