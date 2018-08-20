package com.zjzmjr.core.cache.redis;

import java.util.Map;

import redis.clients.jedis.JedisPool;

import com.zjzmjr.common.util.SpringContextUtil;
import com.zjzmjr.core.cache.redis.cluster.JedisClusterPull;

/**
 * 
 * @author oms
 * 
 */
final class JedisFinder {

	private static JedisFinder instance = null;

	/**
	 * 集群的redis操作接口类实例
	 */
	private JedisClusterPull redisCluster = null;

	/**
	 * 非集群的redis操作接口类实例
	 */
	private JedisPullUtil redisUtil = null;

	/**
	 * 判断是否是使用集群的标识
	 */
	private boolean isClusterFlg = false;

	private static final Object jedisKey = new Object();

	/**
	 * 初始化所有数据
	 */
	private JedisFinder() {
		JedisPool jedisPool = null;
		try {
			jedisPool = SpringContextUtil.getBean("jedisPool");
		} catch (Exception ex) {
			jedisPool = null;
		}
		if (JedisPull.isNull(jedisPool)) {
			isClusterFlg = true;
			redisCluster = new JedisClusterPull();
		} else {
			isClusterFlg = false;
			redisUtil = new JedisPullUtil();
		}
	}

	/**
	 * 获取单例实例对象
	 * 
	 * @return
	 */
	public static JedisFinder instance() {
		if (instance == null) {
			synchronized (jedisKey) {
				if (instance == null) {
					instance = new JedisFinder();
				}
			}
		}

		return instance;
	}

	/**
	 * 将对象存入redis
	 * 
	 * @param key
	 *            redisKEY
	 * @param object
	 * @return
	 */
	public String setObject(String key, Object object) {
		String retRedis = "";
		if (isClusterFlg) {
			retRedis = redisCluster.setObject(key, object);
		} else {
			retRedis = redisUtil.setObject(key, object);
		}
		return retRedis;
	}

	/**
	 * 将对象存入redis
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public String setObject(String key, Object object, int expireTime) {
		String retRedis = "";
		if (isClusterFlg) {
			retRedis = redisCluster.setObject(key, object, expireTime);
		} else {
			retRedis = redisUtil.setObject(key, object, expireTime);
		}
		return retRedis;
	}

	/**
	 * 删除对象
	 * 
	 * @param key
	 */
	public void delObject(String key) {
		if (isClusterFlg) {
			redisCluster.delObject(key);
		} else {
			redisUtil.delObject(key);
		}
	}

	/**
	 * 取redis值，转为对象
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T getObject(String key, Class<T> clazz) {
		T retRedis = null;
		if (isClusterFlg) {
			retRedis = redisCluster.getObject(key, clazz);
		} else {
			retRedis = redisUtil.getObject(key, clazz);
		}
		return retRedis;
	}

	/**
	 * 将map从redis读取
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getMap(String key) {
		if (isClusterFlg) {
		} else {
			return redisUtil.getMap(key);
		}
		return null;
	}

}
