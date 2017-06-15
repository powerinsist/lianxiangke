package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/5/9.
 */

public class AdateBean implements Serializable{
    private String name;
    private String time;
    private String number;
    private String monney;

    public String getMonney() {
        return monney;
    }

    public void setMonney(String monney) {
        this.monney = monney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
