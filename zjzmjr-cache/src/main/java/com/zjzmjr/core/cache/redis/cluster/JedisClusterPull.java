package com.zjzmjr.core.cache.redis.cluster;


/**
 * 
 * jedis集群存储数据
 * 
 * @author oms
 *
 */
public class JedisClusterPull {

	private final JedisClusterUtil jedisUtil = new JedisClusterUtil();
	
    /**
     * 将对象存入redis
     * 
     * @param key
     * @param object
     * @return
     */
    public String setObject(String key, Object object) {
        return jedisUtil.set(key, object);
    }

    /**
     * 将对象存入redis
     * 
     * @param key
     * @param object
     * @return
     */
    public String setObject(String key, Object object, int expireTime) {
        return jedisUtil.set(key, object, expireTime);
    }
    
    /**
     * 删除对象
     * 
     * @param key
     */
    public void delObject(String key){
    	jedisUtil.del(key);
    }
    
    /**
     * 取redis值，转为对象
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getObject(String key,Class<T> clazz) {
    	return jedisUtil.getObject(key, clazz);
    }

}
