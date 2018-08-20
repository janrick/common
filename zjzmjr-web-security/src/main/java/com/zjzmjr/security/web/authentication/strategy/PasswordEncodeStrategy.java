/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.strategy;
/**
 * 密码的加密策略
 * 
 * @author elliott
 * @version $Id: PasswordEncodeStrategy.java, v 1.0 2013-12-26 上午12:05:56 elliott Exp $
 */
public interface PasswordEncodeStrategy {

	/**
	 * 获得加密后的密码
	 * 
	 * @param pwd
	 * @return
	 */
	public String encodePassword(String pwd);
	
	/**
	 * 密码是否正确
	 * 
	 * @param pwd        未加密的密码
	 * @param encodedPwd 加密的密码
	 * @return
	 */
	public boolean isPasswordValid(String pwd,String encodedPwd);
}
