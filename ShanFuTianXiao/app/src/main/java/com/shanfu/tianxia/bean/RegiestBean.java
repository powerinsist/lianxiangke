package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by qing on 2017/3/1.
 */
public class RegiestBean implements Serializable {
    private static final long serialVersionUID = 1L;
    //private String data;
    private String message;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Status status;



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




    public class Status{
        public String getSucceed() {
            return succeed;
        }

        public void setSucceed(String succeed) {
            this.succeed = succeed;
        }

        public String succeed;
    }
}
