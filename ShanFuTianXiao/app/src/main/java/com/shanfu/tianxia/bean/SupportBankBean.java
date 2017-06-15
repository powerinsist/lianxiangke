package com.shanfu.tianxia.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/17.
 */

public class SupportBankBean implements Serializable {
    private int imageRes;
    private String bankName;

    public int getBankImage() {
        return imageRes;
    }

    public void setBankImage(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
