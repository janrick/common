package com.zjzmjr.core.cache.redis.cluster;

import redis.clients.jedis.JedisCluster;
import redis.clients.util.SafeEncoder;

import com.zjzmjr.common.util.SpringContextUtil;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.cache.redis.SerializeUtil;

/**
 * jedis集群配置工具类
 * 
 * @author oms
 * 
 */
class JedisClusterUtil {

	/** 获取集群的对象 */
	private JedisCluster jedisCluster = SpringContextUtil.getBean("jedisCluster");

	/**
	 * 取redis值，转为对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(String key, Class<T> clazz) {
		byte[] bytes = jedisCluster.get(SafeEncoder.encode(key));
		if (!JedisPull.isNull(bytes)) {
			T t = (T) SerializeUtil.unserialize(bytes);
			return t;
		} else {
			bytes = jedisCluster.get(SerializeUtil.serialize(key));
			if (!JedisPull.isNull(bytes)) {
				T t = (T) SerializeUtil.unserialize(bytes);
				return t;
			}
		}
		return null;
	}

	/**
	 * 保存指定key值的value
	 * 
	 * @param key
	 * @param value
	 */
	public String set(String key, Object value) {
		byte[] bytes = SerializeUtil.serialize(value);
		return jedisCluster.set(SafeEncoder.encode(key), bytes);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 *            单位秒
	 */
	public String set(String key, Object value, int expireTime) {
		byte[] bytes = SerializeUtil.serialize(value);
		return jedisCluster.setex(SafeEncoder.encode(key), expireTime, bytes);
	}

	/**
	 * 删除指定key的value
	 * 
	 * @param key
	 */
	public Long del(String key) {
		return jedisCluster.del(SafeEncoder.encode(key));
	}

}
