package com.shanfu.tianxia.utils;

/**
 * Created by qing on 2017/3/16.
 * 数据类型转换
 */
public class DateTransformation {
    public static int getint(String str){
        Double dd=Double.valueOf(str.substring(0, str.length()-1));
        int i = (int)(dd*100)/100;
        return i;

    }

    public static int getDoubleForInt(String doublestr){
        String result;
        if(doublestr.contains(".")){
            result = doublestr.substring(0,doublestr.indexOf("."));
        }else{
            result = doublestr;
        }


       /* Double d = Double.parseDouble(doublestr); // 先转换成double类型
        Integer i = d.intValue(); // 再转换成int类型（会损失精度）*/
       return Integer.parseInt(result);
    }



}
