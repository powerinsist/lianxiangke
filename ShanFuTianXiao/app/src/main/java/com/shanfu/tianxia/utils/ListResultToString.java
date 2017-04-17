package com.shanfu.tianxia.utils;

import java.util.List;

/**
 * 将数组toString 返回结果去掉中括号
 * @author Administrator
 *
 */
public class ListResultToString {

	public static String ToString(List<String> resultList){
		String result = resultList.toString();
		String result1 = result.replace("[", "");
		String result2 = result1.replace("]", "");
		return result2;
	}
}
