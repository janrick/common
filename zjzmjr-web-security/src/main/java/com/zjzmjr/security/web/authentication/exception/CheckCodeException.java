/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误异常
 * 
 * @author elliott
 * @version $Id: CheckCodeException.java, v 1.0 2013-12-25 下午8:50:18 elliott Exp $
 */
public class CheckCodeException extends AuthenticationException {
	private static final long serialVersionUID = -2621647824892037191L;

	public CheckCodeException(String msg) {
		super(msg);
	}

}
