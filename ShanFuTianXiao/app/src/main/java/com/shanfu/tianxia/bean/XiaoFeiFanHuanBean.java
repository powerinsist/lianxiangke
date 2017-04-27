package com.shanfu.tianxia.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qing on 2017/4/1.
 */
public class XiaoFeiFanHuanBean implements Serializable {
    private String err_code;
    private String err_msg;
    private XiaoFeiData data;
    private CiaoFeiBean data_ciaofei;


    public CiaoFeiBean getData_ciaofei() {
        return data_ciaofei;
    }

    public void setData_ciaofei(CiaoFeiBean data_ciaofei) {
        this.data_ciaofei = data_ciaofei;
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

    public XiaoFeiData getData() {
        return data;
    }

    public void setData(XiaoFeiData data) {
        this.data = data;
    }

    public class XiaoFeiData{

        private List<CiaoFeiBean> list;
        private String ptoken;
        private String uid;

        public List<CiaoFeiBean> getList() {
            return list;
        }

        public void setList(List<CiaoFeiBean> list) {
            this.list = list;
        }

        public String getPtoken() {
            return ptoken;
        }

        public void setPtoken(String ptoken) {
            this.ptoken = ptoken;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }



        @Override
        public String toString() {
            return "XiaoFeiData{" +
                    "list=" + list +
                    ", ptoken='" + ptoken + '\'' +
                    ", uid='" + uid + '\'' +
                    '}';
        }
    }

    public class CiaoFeiBean{

        private String remission_time;
        private String remission_money;
        private String id;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemission_money() {
            return remission_money;
        }

        public void setRemission_money(String remission_money) {
            this.remission_money = remission_money;
        }

        public String getRemission_time() {
            return remission_time;
        }

        public void setRemission_time(String remission_time) {
            this.remission_time = remission_time;
        }

        @Override
        public String toString() {
            return "CiaoFeiBean{" +
                    "remission_time='" + remission_time + '\'' +
                    ", remission_money='" + remission_money + '\'' +
                    ", id='" + id + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "XiaoFeiFanHuanBean{" +
                "err_code='" + err_code + '\'' +
                ", err_msg='" + err_msg + '\'' +
                ", data=" + data +
                ", data_ciaofei=" + data_ciaofei +
                '}';
    }
}
