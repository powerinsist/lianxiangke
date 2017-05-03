package com.shanfu.tianxia.bean;

import java.io.Serializable;

/**
 * Created by xuchenchen on 2017/4/26.
 */

public class RsultCodeBean implements Serializable {
    private String err_code;
    private String err_msg;
    private YanZhengBrean data;

    public YanZhengBrean getData() {
        return data;
    }

    public void setData(YanZhengBrean data) {
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

    public class YanZhengBrean{
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
