package com.zjzmjr.core.cache.redis;

import java.util.Map;

import redis.clients.util.SafeEncoder;

/**
 * 对redis缓存操作的接口类
 * 
 * @author oms
 *
 */
final class JedisPullUtil {
	
	private final JedisUtil jedisUtil = new JedisUtil();
	
    /**
     * 将对象存入redis
     * 
     * @param key
     * @param object
     * @return
     */
    public String setObject(String key, Object object) {
        byte[] bytes = SerializeUtil.serialize(object);
        return jedisUtil.set(key, bytes);
    }

    /**
     * 将对象存入redis
     * 
     * @param key
     * @param object
     * @return
     */
    public String setObject(String key, Object object, int expireTime) {
        byte[] bytes = SerializeUtil.serialize(object);
        byte[] keys = SerializeUtil.serialize(key);
        return jedisUtil.setEx(keys, expireTime, bytes);
    }
    
    /**
     * 删除对象
     * 
     * @param key
     */
    public void delObject(String key){
        jedisUtil.del(SafeEncoder.encode(key));
        jedisUtil.del(SerializeUtil.serialize(key));
    }
    
    /**
     * 取redis值，转为对象
     * 
     * @param key
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(String key,Class<T> clazz) {
        byte[] bytes = jedisUtil.get(SafeEncoder.encode(key));
        if (!JedisPull.isNull(bytes)) {
            T t = (T) SerializeUtil.unserialize(bytes);
            return t;
        }else{
            bytes = jedisUtil.get(SerializeUtil.serialize(key));
            if (!JedisPull.isNull(bytes)) {
                T t = (T) SerializeUtil.unserialize(bytes);
                return t;
            }
        }
        return null;
    }

    /**
     * 将map从redis读取
     * 
     * @param key
     * @return
     */
    public Map<String, String> getMap(String key) {
        return jedisUtil.hgetAll(key);
    }
        
}
