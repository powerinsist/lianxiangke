package com.shanfu.tianxia.bean;

import java.util.List;

/**
 * @创建者：Fox
 * @创建时间：2016/2/17
 * @描述： ${TODO}
 * @修改者：$TODO
 * @修改时间：2016/2/17 11:11
 */
public class HomeBean {
    public List<HomeTopicPicBean> homeTopic;   //Array

    public String response;    //home,topic,limitbuy

    public List<HomeTopicSaleItemBean> topic;
    public class HomeTopicPicBean {
        public int    id; //130
        public String pic; ///images/home/topic1.jpg
        public String title;    //活动
    }
    public class HomeTopicSaleItemBean{
        public int    id;
        public String name;
        public String pic;
    }
}
