/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.security.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 网络工具类
 * 
 * @author elliott
 * @version $Id: WebUtil.java, v 1.0 2014-2-27 下午5:38:38 elliott Exp $
 */
public class WebUtil {
	
	/**
	 * 获取用户的IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserIp(HttpServletRequest request){
		return getUserIp(request, 1);
	}
	
	/**
	 * 获取用户IP
	 * 
	 * @param request	请求
	 * @param proxy		代理个数
	 * @return
	 */
	public static String getUserIp(HttpServletRequest request,int proxy){
		if(request!=null){
			if(proxy>=1){
				String fwd = StringUtils.trimToNull(request.getHeader("X-Forwarded-For"));
		        if (fwd == null) {
		            return request.getRemoteAddr();
		        } else {
		        	String[] ips = fwd.length()>80?fwd.substring(fwd.length()-80).split(","):fwd.split(",");
		        	if(proxy<=1||ips.length==1){
		        		String ip = StringUtils.trimToEmpty(ips[ips.length-1]);
		        		return ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")?ip:"";
		        	}
		        	String ip = StringUtils.trimToEmpty(ips[ips.length-Math.min(proxy, ips.length)]);
		        	return ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")?ip:"";
		        }
			}else{
				return request.getRemoteAddr();
			}
		}
		return null;
	}
	
	/**
	 * 往url地址后面拼接查询字符串
	 * 
	 * @param url
	 * @param query
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String addQueryParameter(String url,Map<String, String> param){
		if(StringUtils.isNotBlank(url)&&param!=null&&!param.isEmpty()){
			StringBuilder ps = new StringBuilder("");
			int pos = 0;
			for(Entry<String, String> entry:param.entrySet()){
				try {
					ps.append(pos++==0?"":"&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
			return url+(url.indexOf("?")<0?"?":"&")+ps.toString();
		}
		return url;
	}
	
	public static String getWebContent(String url,Map<String, String> param) throws IOException{
		URL u = null;
		if(param==null||param.isEmpty()){
			u = new URL(url);
		}else{
			u = new URL(addQueryParameter(url, param));
		}
		HttpURLConnection conn = (HttpURLConnection)u.openConnection();
		conn.connect();
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = null;
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
		} finally{
			IOUtils.closeQuietly(reader);
		}
		return sb.toString();
	}
	
}
