/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 用户登录异常
 * 
 * @author elliott
 * @version $Id: FormLoginException.java, v 1.0 2014-1-1 下午9:13:20 elliott Exp $
 */
public class FormLoginException extends AuthenticationException {
	private static final long serialVersionUID = 5035280199247435280L;
	
	private String message;

	public FormLoginException(String msg, Throwable t) {
		super(msg, t);
		this.message=msg;
	}
	
	public FormLoginException(String msg){
		super(msg);
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
