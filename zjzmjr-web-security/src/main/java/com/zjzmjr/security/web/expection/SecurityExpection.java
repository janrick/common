/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.expection;

/**
 * 权限验证错误
 * 
 * @author elliott
 * @version $Id: SecurityExpection.java, v 1.0 2013-12-26 上午12:49:17 elliott Exp $
 */
public class SecurityExpection extends RuntimeException {
	private static final long serialVersionUID = -4847038546143075230L;
	
	public SecurityExpection(String msg){
		super(msg);
	}
	public SecurityExpection(String msg,Throwable e){
		super(msg, e);
	}

}
