/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.strategy;

import com.zjzmjr.common.util.MD5Util;


/**
 * 
 * @author elliott
 * @version $Id: MD5PasswordEncodeStrategy.java, v 1.0 2013-12-26 上午12:11:44 elliott Exp $
 */
public class MD5PasswordEncodeStrategy implements PasswordEncodeStrategy {

	/** 
	 * @see com.yztz.web.security.authentication.strategy.PasswordEncodeStrategy#encodePassword(java.lang.String)
	 */
	@Override
	public String encodePassword(String pwd) {
		return pwd==null?null:MD5Util.getMD5(pwd);
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.PasswordEncodeStrategy#isPasswordValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPasswordValid(String pwd, String encodedPwd) {
		return pwd==null?false:MD5Util.getMD5(pwd).equalsIgnoreCase(encodedPwd);
	}

}
