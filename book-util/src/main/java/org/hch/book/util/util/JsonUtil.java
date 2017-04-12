package org.hch.book.util.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

	/**
	 * 对象转json字符串
	 * @Description (TODO
	 * @param obj
	 * @return
	 */
	public static String obj2JsonString(Object obj) {
		return JSONObject.toJSONString(obj);
	}
	
	/**
	 * json字符串转对象
	 * @Description (TODO
	 * @param str
	 * @param clazz
	 * @return
	 */
	public static <E> E jsonString2Obj(String str, Class<E> clazz) {
		return JSONObject.parseObject(str, clazz);
	}
}
