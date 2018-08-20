/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zjzmjr.web.mvc.constants.WebConstants;
import com.zjzmjr.web.mvc.enums.DuplicateCommitActionEnum;

/**
 * 表单重复提交注解类
 * 
 * <p>该注解类主要用于标注需要防表单重复提交的action方法,</p>
 * 
 * @author elliott
 * @version $Id: DuplicateCommit.java, v 1.0 2013-12-24 上午11:11:12 elliott Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateCommit {
	/**
	 * 默认的操作
	 * 
	 * @return
	 */
	public DuplicateCommitActionEnum action() default DuplicateCommitActionEnum.PUT_TOKEN;
	/**
	 * token令牌名称
	 * 
	 * @return
	 */
	public String tokenName() default WebConstants.DEFAULT_FORM_TOKEN_NAME;
	
	/**
	 * 错误信息
	 * 
	 * @return
	 */
	public String errorMsg() default "";
	
	/**
	 * 失败时跳转的视图
	 * 
	 * @return
	 */
	public String value() default "";
}
