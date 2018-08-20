/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zjzmjr.security.web.authentication.token.UsernamePasswordToken;
import com.zjzmjr.security.web.enums.SecurityActionEnum;


/**
 * 用户权限控制注解
 * 
 * @author elliott
 * @version $Id: Security.java, v 1.0 2013-12-26 上午12:44:44 elliott Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {
	
	/**
	 * 是否验证已经认证
	 * 
	 * @return
	 */
	public boolean value() default true;
	
	/**
	 * 是否检查来源
	 * <p>如果为<code>true</code>，将会从SpringSecurity安全上下文中获取{@link UsernamePasswordToken#getSource() source}与当前用户IP进行比较</p>
	 * 
	 * @return
	 */
	public boolean checkSource() default false;
	
	/**
	 * 权限
	 * 
	 * @return
	 */
	public String[] auth() default {};
	
	/**
	 * 异常处理方式,默认自动处理
	 * 
	 * @return
	 */
	public SecurityActionEnum action() default SecurityActionEnum.AUTO;
	
	/**
	 * 返回的视图名称
	 * <p>该字段只在controller中有用，生成错误信息后会跳转到该视图</p>
	 * 
	 * @return
	 */
	public String view() default "";
	
}
