/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.security.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author elliott
 * @version $Id: CookieUtil.java, v 1.0 2014-2-27 下午10:12:57 elliott Exp $
 */
public class CookieUtil {

	/**
	 * 获取cookie值
	 * 
	 * @param req
	 * @param cookieName
	 * @return
	 */
	public static String getCookie(HttpServletRequest req,String cookieName){
		if(req!=null&&StringUtils.isNotBlank(cookieName)){
			Cookie[] cookies = req.getCookies();
			if(cookies!=null&&cookies.length>0){
				for(Cookie ck:cookies){
					if(cookieName.equals(ck.getName())){
						String value = null;
						try {
							value = URLDecoder.decode(StringUtils.trimToEmpty(ck.getValue()), "UTF-8");
						} catch (UnsupportedEncodingException e) {
						}
						return value;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 添加cookie
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param expire
	 * @param resp
	 */
	public static void addCookie(String name,String value,String domain,int expire,HttpServletResponse resp){
		addCookie(name, value, domain, expire, "UTF-8", resp);
	}
	
	/**
	 * 添加Cookie
	 * 
	 * @param name
	 * @param value
	 * @param domain
	 * @param expire
	 * @param charset
	 * @param resp
	 */
	public static void addCookie(String name,String value,String domain,int expire,String charset,HttpServletResponse resp){
		if(StringUtils.isBlank(name)){
			throw new IllegalArgumentException("cookie name不能为空");
		}
		if(StringUtils.isBlank(charset)){
			throw new IllegalArgumentException("charset 字符编码不能为空");
		}
		String encodedValue = "";
		try {
			encodedValue = URLEncoder.encode(StringUtils.trimToEmpty(value), charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("编码错误");
		}
		Cookie cookie = new Cookie(name, encodedValue);
		if(StringUtils.isNotBlank(domain)){
			cookie.setDomain(StringUtils.trimToEmpty(domain));
		}
		cookie.setMaxAge(expire);
		cookie.setPath("/");
		resp.addCookie(cookie);
	}
	
	/**
	 * 删除cookie
	 * 
	 * @param cookieName
	 * @param resp
	 */
	public static void deleteCookie(String cookieName,HttpServletResponse resp){
		deleteCookie(cookieName, null, resp);
	}
	
	/**
	 * 删除cookie
	 * 
	 * @param cookieName
	 * @param domain
	 * @param resp
	 */
	public static void deleteCookie(String cookieName,String domain,HttpServletResponse resp){
		if(StringUtils.isNotBlank(cookieName)&&resp!=null){
			Cookie cookie = new Cookie(StringUtils.trimToEmpty(cookieName), "");
			if(StringUtils.isNotBlank(domain)){
				cookie.setDomain(StringUtils.trimToEmpty(domain));
			}
			cookie.setPath("/");
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
		}
	}
}
