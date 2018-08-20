/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.hook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

/**
 * 认证失败处理钩子
 * 
 * @author elliott
 * @version $Id: FormAuthenticationFailureHook.java, v 1.0 2013-12-25 下午11:21:02 elliott Exp $
 */
public interface FormAuthenticationFailureHook {

	/**
	 * 认证失败
	 * 
	 * @param req
	 * @param resp
	 * @param exception
	 */
	public void onFailure(HttpServletRequest req,
			HttpServletResponse resp, AuthenticationException exception);
}
