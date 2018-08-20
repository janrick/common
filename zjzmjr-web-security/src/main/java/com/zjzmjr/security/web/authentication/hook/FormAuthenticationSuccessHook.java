/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.hook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

/**
 * 表单认证成功处理钩子
 * 
 * @author elliott
 * @version $Id: FormAuthenticationSuccessHook.java, v 1.0 2013-12-25 下午11:16:55 elliott Exp $
 */
public interface FormAuthenticationSuccessHook {
	
	/**
	 * 成功后的操作
	 * 
	 * @param req
	 * @param resp
	 * @param authentication
	 */
	public void onSuccess(HttpServletRequest req,
			HttpServletResponse resp, Authentication authentication);
}
