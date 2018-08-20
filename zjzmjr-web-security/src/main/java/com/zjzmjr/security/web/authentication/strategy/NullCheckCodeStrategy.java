/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * 没有验证码验证的验证策略
 * <p>在该策略下，不进行验证码验证。适合没有验证码的验证</p>
 * 
 * @author elliott
 * @version $Id: NullCheckCodeStrategy.java, v 1.0 2013-12-25 下午11:08:26 elliott Exp $
 */
public class NullCheckCodeStrategy implements CheckCodeStrategy {

	/** 
	 * @see com.yztz.web.security.authentication.strategy.CheckCodeStrategy#isCheckCodeValid(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public boolean isCheckCodeValid(HttpServletRequest req, String checkCode) {
		return true;
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.CheckCodeStrategy#getServerCheckCode(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getServerCheckCode(HttpServletRequest req) {
		return null;
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.CheckCodeStrategy#putServerCheckCode(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public void putServerCheckCode(HttpServletRequest req, String checkCode) {
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.CheckCodeStrategy#clearServerCheckCode(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void clearServerCheckCode(HttpServletRequest req) {
	}

	/** 
	 * @see com.yztz.web.security.authentication.strategy.CheckCodeStrategy#isCheckCodeRequired(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean isCheckCodeRequired(HttpServletRequest req) {
		return false;
	}

}
