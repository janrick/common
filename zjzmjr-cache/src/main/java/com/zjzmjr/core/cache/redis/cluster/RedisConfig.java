package com.zjzmjr.core.cache.redis.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * 解析redis集群配置
 * 
 * @author xiakai
 * 
 */
public class RedisConfig {

	public Map<String, List<RedisCluster>> redisGroupMap = null;

	// 有参构造函数
	public RedisConfig() {

	}

	// 获取所有clusterGroup组的键值对
	public Map<String, List<RedisCluster>> getRedisGroupMap() {
		// 读取xml文件
		Document document = readXmlFile();
		// 获得clusterGroup节点的key 和 list
		if (redisGroupMap == null) {
			redisGroupMap = getMapByItemsGroup(document);
		}

		return redisGroupMap;
	}

	// 读取redisConfig配置文件
	private Document readXmlFile() {
		// 创建读入对象
		SAXReader reader = new SAXReader();
		// 创建document实例
		Document doc = null;
		try {
			// 从类路径下加载文件redisConfig.xml  
			Resource resource = new ClassPathResource("META-INF/spring/zjzmjr-cluster-cache.xml");
//			Resource resource = new ClassPathResource("D:\\workspaces\\common\\zjzmjr-cache\\target\\classes\\META-INF\\spring\\redisClusterConfig.xml");
			// 指定文件资源对应的编码格式（UTF-8),这样才能正确读取文件的内容，而不会出现乱码
			EncodedResource encodeResource = new EncodedResource(resource,
					"UTF-8");
			doc = reader.read(encodeResource.getReader());
		} catch (IOException e) {
			System.out.println("无法读取系统配置文件redisConfig.xml,可能该文件不存在");

		} catch (DocumentException e) {
			System.out.println("解析redisConfig.xml文件出现异常");

		}
		return doc;

	}

	// 读取xml节点,返回节点为redisGroup的Map
	private Map<String, List<RedisCluster>> getMapByItemsGroup(
			Document document) {
		Map<String, List<RedisCluster>> itemmap = new HashMap<String, List<RedisCluster>>();
		try {
			// 获得根节点
			Element root = document.getRootElement();
			// 获得根节点下所有子节点clusterGroup的list
			List itemsList = root.selectNodes("./clusterGroup");
			for (int i = 0; i < itemsList.size(); i++) {
				// 获得节点Items
				Element items = (Element) itemsList.get(i);
				String groupName = items.attribute("name").getText();
				String selectdb = items.attribute("selectdb") == null ? ""
						: items.attribute("selectdb").getText();
				// if(groupName!=null&&groupName.equals(this.getGroupName())){
				// 获得clusterGroup下所有子节点service的list
				List itemList = items.elements();
				// 获得service节点的值
				List<RedisCluster> redisClusterList = getItemList(itemList,
						selectdb);

				itemmap.put(groupName, redisClusterList);
				// }

			}
		} catch (Exception e) {

		}
		return itemmap;
	}

	// 获得所有Item下节点的redis服务节点
	private List<RedisCluster> getItemList(List itemList, String selectdb) {

		List<RedisCluster> redisClusterList = new ArrayList<RedisCluster>();
		for (int i = 0; i < itemList.size(); i++) {
			// 获得节点server
			Element item = (Element) itemList.get(i);
			String hostIp = item.attribute("host").getText();
			String port = item.attribute("port").getText();

			RedisCluster redisCluster = new RedisCluster();
			redisCluster.setHostIp(hostIp);
			redisCluster.setPort(port);
			redisCluster.setSelectdb(selectdb);

			redisClusterList.add(redisCluster);
		}
		return redisClusterList;

	}

	public void main(String[] args) {
//		getRedisGroupMap();
//		// JedisUtil.insertPublicDataObject("user1", "张三",
//		// JedisUtil.ONLINE_USER);
//		// JedisUtil.insertPublicDataObject("user2", "李四",
//		// JedisUtil.ONLINE_USER);
//		// JedisUtil.insertPublicDataObject("user3", "王五",
//		// JedisUtil.ONLINE_USER);
//
//		Set s = RedisClusterUtil.getAllSet(RedisClusterUtil.ONLINE_USER);
//		Iterator it = s.iterator();
//		while (it.hasNext()) {
//			String key = (String) it.next();
//			String value = RedisClusterUtil.getString(key, RedisClusterUtil.ONLINE_USER);
//			System.out.println(key + value);
//		}
//		String test = RedisClusterUtil.getString("user1", RedisClusterUtil.ONLINE_USER);
//		System.out.println(test);

	}
}
