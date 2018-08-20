/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.strategy;

import com.zjzmjr.common.util.MD5Util;


/**
 * MD5加点盐加密策略
 * 
 * @author elliott
 * @version $Id: MD5WithSaltPasswordEncodeStrategy.java, v 1.0 2013-12-26 上午12:14:29 elliott Exp $
 */
public class MD5WithSaltPasswordEncodeStrategy implements
		PasswordEncodeStrategy {
	private String salt = "www.yztz.com#1Pwd_salt@Default2+!`%Ok_here'The&End$";
	
	/** 
	 * @see com.yztz.web.security.authentication.strategy.PasswordEncodeStrategy#encodePassword(java.lang.String)
	 */
	@Override
	public String encodePassword(String pwd) {
		return pwd==null?null:MD5Util.getMD5WithSalt(pwd, salt);
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.PasswordEncodeStrategy#isPasswordValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPasswordValid(String pwd, String encodedPwd) {
		return pwd==null?false:MD5Util.getMD5WithSalt(pwd, salt).equalsIgnoreCase(encodedPwd);
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
