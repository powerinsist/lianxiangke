package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by qing on 2017/3/1.
 * 注册短信验证码
 */
public class RegisterCode implements Serializable {



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public String message;
    public Status status;

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
