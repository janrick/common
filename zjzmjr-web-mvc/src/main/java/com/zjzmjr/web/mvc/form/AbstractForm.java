/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.form;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 表单基础抽象类
 *  
 *  <p>该类带有一些基础功能</p>
 * 
 * @author elliott
 * @version $Id: AbstractForm.java, v 1.0 2013-12-23 下午7:51:06 elliott Exp $
 */
public abstract class AbstractForm implements Serializable {
	private static final long serialVersionUID = 6248690579112777634L;
	/**
	 * 表单令牌
	 */
	private String token;
	
	/**
	 * 表单字段名称转换
	 * 
	 *  <p>将表单的字段名映射成一个具有可读性的中文名称</p>
	 * 
	 * @param field
	 * @return
	 */
	public abstract String resolveFiled(String field);
	
	public BigDecimal getZero(){
		return BigDecimal.ZERO;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
