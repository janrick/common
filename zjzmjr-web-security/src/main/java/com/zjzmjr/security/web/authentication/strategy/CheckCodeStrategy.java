/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码验证策略
 * 
 * @author elliott
 * @version $Id: CheckCodeStrategy.java, v 1.0 2013-12-25 下午7:23:49 elliott Exp $
 */
public interface CheckCodeStrategy {
	
	/**
	 * 验证码是否正确
	 * 
	 * <p>根据指定的策略检查验证码是否正确，如果{@link #isCheckCodeRequired(HttpServletRequest)} 返回<code>true</code>那么必须验证验证码；否则直接返回<code>true</code></p>
	 * 
	 * @param req        Http请求
	 * @param checkCode  验证码
	 * @return
	 */
	public boolean isCheckCodeValid(HttpServletRequest req,String checkCode);
	
	/**
	 * 获取服务器端保存的验证码
	 * 
	 * @param req    Http请求
	 * @return
	 */
	public String getServerCheckCode(HttpServletRequest req);
	
	/**
	 * 将验证码放到服务器端
	 * 
	 * @param req        Http请求
	 * @param checkCode  验证码
	 */
	public void putServerCheckCode(HttpServletRequest req,String checkCode);
	
	/**
	 * 清除服务端的验证码
	 * 
	 * @param req   Http请求
	 */
	public void clearServerCheckCode(HttpServletRequest req);
	
	/**
	 * 是否需要验证
	 * 
	 * @param req       Http请求
	 * @return
	 */
	public boolean isCheckCodeRequired(HttpServletRequest req);
}
