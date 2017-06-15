package com.shanfu.tianxia.model;

import com.shanfu.tianxia.bean.ZoneProductBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuchenchen on 2017/5/10.
 */

public class ZoneFloorData implements Serializable {
    private List<ZoneProductBean> productBeans;

    public List<ZoneProductBean> getProductBeans() {
        return productBeans;
    }

    public void setProductBeans(List<ZoneProductBean> productBeans) {
        this.productBeans = productBeans;
    }
}
