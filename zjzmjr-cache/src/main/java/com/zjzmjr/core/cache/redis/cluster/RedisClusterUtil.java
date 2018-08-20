package com.zjzmjr.core.cache.redis.cluster;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.zip.CRC32;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.google.gson.Gson;

class RedisClusterUtil {

	public final String ONLINE_USER = "1";

	// jedis服务组业务类型
	public final String CONT_CLUSTERNAME_PUBLICDATA = "publicData";
	public final String CONT_CLUSTERNAME_SESSIONROUTE = "sessionRoute";
	public final String CONT_CLUSTERNAME_USERROUTE = "userRoute";

	// 操作方式 0 插入 1获取 2 删除
	public final long INSERT_OPERATION = 0;
	public final long GET_OPERATION = 1;
	public final long DELETE_OPERATION = 2;

	private void closeJedis(Jedis jedis) {
		try {
			jedis.quit();
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		}
		jedis.disconnect();
	}

	/**
	 * 根据Key获取字符串
	 * 
	 * @param key
	 * @param jedisGroup
	 */
	public String getString(String key, String selectdb) {
		Jedis jedis = getPublicDataJedis(key, GET_OPERATION, selectdb);
		return jedis.get(key);
	}

	/**
	 * 获取所有数据set
	 * 
	 * @param selectdb
	 * @return
	 */
	public Set getAllSet(String selectdb) {
		Jedis jedis = getDataJedis(GET_OPERATION, selectdb);
		return jedis.keys("*");
	}

	/**
	 * 默认取配置文件的第一个数据库
	 * 
	 * @param operation
	 * @param selectdb
	 * @return
	 */
	private Jedis getDataJedis(long operation, String selectdb) {
//		if (RedisConfig.redisGroupMap == null) {
//			RedisConfig.redisGroupMap = RedisConfig.getRedisGroupMap();
//		}
//		List<RedisCluster> clustersList = RedisConfig.redisGroupMap
//				.get(CONT_CLUSTERNAME_PUBLICDATA);
//
//		int clusterNo = 0;// 默认存到第一个
//
//		RedisCluster cluster = clustersList.get(clusterNo);
//		Jedis jedis = new Jedis(cluster.getHostIp(), Integer.valueOf(cluster
//				.getPort()));
//		jedis.select(Integer.valueOf(selectdb));
//		return jedis;
		return null;
	}

	/**
	 * 删除数据
	 * 
	 * @param key
	 * @param jedisGroup
	 */
	public void deleteObject(String key, String jedisGroup) {
		Jedis jedis = getJedis(key, jedisGroup, DELETE_OPERATION);
		jedis.del(key);
		closeJedis(jedis);
	}

	/**
	 * 删除公共数据
	 * 
	 * @param key
	 * @param objClass
	 * @param selectdb
	 */
	public void deletePublicDataObject(String key, String selectdb) {
		Jedis jedis = getPublicDataJedis(key, DELETE_OPERATION, selectdb);
		jedis.del(key);
		closeJedis(jedis);
	}

	/**
	 * 获取jedis的库实例
	 * 
	 * @param key
	 * @param jedisGroup
	 * @param operation
	 * @return
	 */
	private Jedis getJedis(String key, String jedisGroup, long operation) {
//		if (RedisConfig.redisGroupMap == null) {
//			RedisConfig.redisGroupMap = RedisConfig.getRedisGroupMap();
//		}
//		List<RedisCluster> clustersList = RedisConfig.redisGroupMap
//				.get(jedisGroup);
//		int arrayLength = clustersList.size();
//		// 根据key值算出该信息应该存入到那个
//		int clusterNo = getRedisNo(key, arrayLength);
//
//		RedisCluster cluster = clustersList.get(clusterNo);
//		Jedis jedis = new Jedis(cluster.getHostIp(), Integer.valueOf(cluster
//				.getPort()));
//		jedis.select(Integer.valueOf(cluster.getSelectdb()));
//		return jedis;
		return null;
	}

	/**
	 * redis key值获取对象
	 * 
	 * @param key
	 * @param objClass
	 * @param jedisGroup
	 * @return
	 */
	public Object getObject(String key, Class objClass, String jedisGroup) {
		Jedis jedis = getJedis(key, jedisGroup, GET_OPERATION);

		String sObj = jedis.get(key);
		closeJedis(jedis);
		Gson gson = new Gson();
		return gson.fromJson(sObj, objClass);

	}

	/**
	 * 获取公共数据jedis的库实例
	 * 
	 * @param key
	 * @param jedisGroup
	 * @param operation
	 * @return
	 */
	private Jedis getPublicDataJedis(String key, long operation,
			String selectdb) {
//		if (RedisConfig.redisGroupMap == null) {
//			RedisConfig.redisGroupMap = RedisConfig.getRedisGroupMap();
//		}
//		List<RedisCluster> clustersList = RedisConfig.redisGroupMap
//				.get(CONT_CLUSTERNAME_PUBLICDATA);
//		int arrayLength = clustersList.size();
//		// 根据key值算出该信息应该存入到那个
//		int clusterNo = getRedisNo(key, arrayLength);
//
//		RedisCluster cluster = clustersList.get(clusterNo);
//		Jedis jedis = new Jedis(cluster.getHostIp(), Integer.valueOf(cluster
//				.getPort()));
//		jedis.select(Integer.valueOf(selectdb));
//		return jedis;
		return null;
	}

	/**
	 * publicdata redis key值获取对象
	 * 
	 * @param key
	 * @param objClass
	 * @param jedisGroup
	 * @return
	 */
	public Object getPublicDataObject(String key, Class objClass,
			String selectdb) {
		Jedis jedis = getPublicDataJedis(key, GET_OPERATION, selectdb);

		String sObj = jedis.get(key);
		closeJedis(jedis);
		Gson gson = new Gson();
		return gson.fromJson(sObj, objClass);

	}

	/**
	 * publicdata redis key值获取对象 List<Entity>
	 * 
	 * @param key
	 * @param objClass
	 * @param jedisGroup
	 * @return
	 */
	public Object getPublicDataObjectByType(String key, Type type,
			String selectdb) {
		Jedis jedis = getPublicDataJedis(key, GET_OPERATION, selectdb);

		String sObj = jedis.get(key);
		closeJedis(jedis);
		Gson gson = new Gson();
		return gson.fromJson(sObj, type);
	}

	/**
	 * 获取redis服务器库编号
	 * 
	 * @param hashKey
	 * @return
	 */
	public int getRedisNo(String key, int arraySize) {
		long hashKey = hash(key);
		int redisNo = (int) (hashKey % arraySize);
		return redisNo;
	}

	/**
	 * 根据key值算出hash值
	 * 
	 * @param k
	 * @return
	 */
	public long hash(String k) {
		CRC32 crc32 = new CRC32();
		crc32.update(k.getBytes());
		return crc32.getValue();
	}

	/**
	 * redis 根据key值将对象插入到不同的库中
	 * 
	 * @param key
	 * @param insertObj
	 * @param jedisGroup
	 */
	public void insertObject(String key, Object insertObj,
			String jedisGroup) {

		Jedis jedis = getJedis(key, jedisGroup, INSERT_OPERATION);
		Gson gson = new Gson();
		jedis.set(key, gson.toJson(insertObj));
		closeJedis(jedis);
	}

	/**
	 * redis 根据key值将对象插入到不同的库中
	 * 
	 * @param key
	 * @param insertObj
	 * @param jedisGroup
	 * @param expire
	 */
	public void insertObject(String key, Object insertObj,
			String jedisGroup, int expireSeconds) {

		Jedis jedis = getJedis(key, jedisGroup, INSERT_OPERATION);
		Gson gson = new Gson();
		jedis.setex(key, expireSeconds, gson.toJson(insertObj));
		closeJedis(jedis);
	}

	/**
	 * publicdata redis 根据key值将对象插入到不同的库中
	 * 
	 * @param key
	 * @param insertObj
	 * @param jedisGroup
	 */
	public void insertPublicDataObject(String key, Object insertObj,
			String selectdb) {

		Jedis jedis = getPublicDataJedis(key, INSERT_OPERATION, selectdb);
		Gson gson = new Gson();
		jedis.set(key, gson.toJson(insertObj));
		closeJedis(jedis);
	}

	/**
	 * publicdata redis 根据key值将对象插入到不同的库中,
	 * 
	 * @param key
	 * @param insertObj
	 * @param jedisGroup
	 * @param expireSeconds
	 */
	public void insertPublicDataObject(String key, Object insertObj,
			String selectdb, int expireSeconds) {
		Jedis jedis = getPublicDataJedis(key, INSERT_OPERATION, selectdb);
		Gson gson = new Gson();
		jedis.setex(key, expireSeconds, gson.toJson(insertObj));
		closeJedis(jedis);
	}

	/**
	 * 更新redis中key的超时时间
	 * 
	 * @param key
	 * @param jedisGroup
	 * @param expireSeconds
	 */
	public void resetExpireSeconds(String key, String jedisGroup,
			int expireSeconds) {
		Jedis jedis = getJedis(key, jedisGroup, GET_OPERATION);
		jedis.expire(key, expireSeconds);
		closeJedis(jedis);
	}

}
