/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.strategy;

/**
 * 明文密码加密策略
 * 
 * <p>密码不做任何加密</p>
 * 
 * @author elliott
 * @version $Id: NullPasswordEncodeStrategy.java, v 1.0 2013-12-26 上午12:09:59 elliott Exp $
 */
public class NullPasswordEncodeStrategy implements PasswordEncodeStrategy {

	/** 
	 * @see com.yztz.web.security.authentication.strategy.PasswordEncodeStrategy#encodePassword(java.lang.String)
	 */
	@Override
	public String encodePassword(String pwd) {
		return pwd;
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.PasswordEncodeStrategy#isPasswordValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPasswordValid(String pwd, String encodedPwd) {
		return pwd==null?encodedPwd==null:pwd.equals(encodedPwd);
	}

}
