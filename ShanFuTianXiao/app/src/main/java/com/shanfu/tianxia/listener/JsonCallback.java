package com.shanfu.tianxia.listener;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：默认将返回的数据解析成需要的Bean,可以是 BaseBean，String，List，Map
 * 修订历史：
 * -
 * -
 * -
 * -
 * -我的注释都已经写的不能再多了,不要再来问我怎么获取数据对象,怎么解析集合数据了,你只要会 gson ,就会解析
 * -
 * -
 * -
 * ================================================
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertSuccess(Response response) throws Exception {
    	 Type type = this.getClass().getGenericSuperclass();  
         if (type instanceof ParameterizedType) {  
             //如果用户写了泛型，就会进入这里，否者不会执行  
             ParameterizedType parameterizedType = (ParameterizedType) type;  
             Type beanType = parameterizedType.getActualTypeArguments()[0];  
             if (beanType == String.class) {  
                 //如果是String类型，直接返回字符串  
                 return (T) response.body().string();  
             } else {  
                 //如果是 Bean List Map ，则解析完后返回  
                 return new Gson().fromJson(response.body().string(), beanType);  
             }  
         } else {  
             //如果没有写泛型，直接返回Response对象  
             return (T) response;  
         }
    }
}