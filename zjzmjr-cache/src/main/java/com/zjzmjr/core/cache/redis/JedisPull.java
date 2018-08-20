/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2016 All Rights Reserved.
 */
package com.zjzmjr.core.cache.redis;

import java.util.Collection;
import java.util.Map;

/**
 * jedis对外使用的工具接口类
 * 
 * @author Administrator
 * @version $Id: JedisPull.java, v 0.1 2015-6-26 下午4:50:02 Administrator Exp $
 */
public class JedisPull {
	
	/**
	 * 将对象存入redis
	 * 
     * @param system 标识当前使用的系统
	 * @param key  redisKEY
	 * @param object
	 * @return
	 */
	public static String setObject(String system, String key, Object object) {
		if(!isNull(system)){
			key = system.concat(key);
		}
		return JedisFinder.instance().setObject(key, object);
	}
	
    /**
     * 将对象存入redis
     * 
     * @param system 标识当前使用的系统
     * @param key
     * @param object
     * @return
     */
    public static String setObject(String system, String key, Object object, int expireTime) {
    	if(!isNull(system)){
			key = system.concat(key);
		}
        return JedisFinder.instance().setObject(key, object, expireTime);
    }
    
    /**
     * 删除对象
     * 
     * @param system 标识当前使用的系统
     * @param key
     */
    public static void delObject(String system, String key){
    	if(!isNull(system)){
			key = system.concat(key);
		}
    	JedisFinder.instance().delObject(key);
    }
    
    /**
     * 取redis值，转为对象
     * 
     * @param system 标识当前使用的系统
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T getObject(String system, String key,Class<T> clazz) {
    	if(!isNull(system)){
			key = system.concat(key);
		}
    	return JedisFinder.instance().getObject(key, clazz);
    }

    /**
     * 将map从redis读取
     * 
     * @param system 标识当前使用的系统
     * @param key
     * @return
     */
    public static Map<String, String> getMap(String system, String key) {
    	if(!isNull(system)){
			key = system.concat(key);
		}
    	
        return JedisFinder.instance().getMap(key);
    }
    
    /**
     * 判断对象为空
     * 
     * @param argObj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Object argObj) {
        if (argObj == null) {
            return true;
        }

        if (argObj instanceof String) {

            if (((String) argObj).trim().equals("")
                    || ((String) argObj).trim().equals(" ")
                    || ((String) argObj).trim().equals("null")) {
                return true;
            }
        }

        if (argObj instanceof Collection) {

            if (((Collection) argObj).size() == 0) {
                return true;
            }
        }

        if (argObj instanceof Map) {

            if (((Map) argObj).size() == 0) {
                return true;
            }
        }

        return false;
    }
    
    
}
