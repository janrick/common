package com.zjzmjr.loan.upload.web.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * 众易通API接口中hearder加密工具
 * 
 * @author oms
 * @version $Id: SignHelper.java, v 0.1 2017-5-22 上午11:10:32 oms Exp $
 */
public class ConetonSignHelper {

    public final static String CHARSET = "UTF-8";
    public final static String SIGNTYPE = "MD5"; // 默认签名类型
    public final static String SEPARATOR = "&"; // 默认分隔符

	/**
	 * 除去数组中的空值并以字母a到z的顺序排序
	 *
	 * @param dictArrayPre
	 *            过滤前的参数组
	 * @return
	 */
	public static HashMap<String, String> filterPara(SortedMap<String, String> dictArrayPre) {
		HashMap<String, String> dictArray = new LinkedHashMap<>();
		for (String key : dictArrayPre.keySet()) {
			String value = dictArrayPre.get(key);
			if (!value.isEmpty()) {
				dictArray.put(key.toLowerCase(), value);
			}
		}
		return dictArray;
	}

    /**
     * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param dictArray
     *            需要拼接的数组
     * @param separator
     *            分隔字符
     * @return
     */
    public static String createLinkString(Map<String, Object> dictArray) {
        return createLinkString(dictArray, SEPARATOR);
    }

	/**
	 * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param dictArray
	 *            需要拼接的数组
	 * @param separator
	 *            分隔字符
	 * @return
	 */
	private static String createLinkString(Map<String, Object> dictArray, String separator) {
		StringBuilder prestr = new StringBuilder();
		for (String key : dictArray.keySet()) {
		    Object value = dictArray.get(key);
			prestr.append(key + "=" + value + separator);
		}
		// 去掉最后一个&字符
		String value = prestr.toString();
		value = value.substring(0, value.length() - separator.length());
		return value;
	}

}
