/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.bind;

import org.springframework.beans.PropertyAccessException;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.validation.BindingResult;

/**
 * 简单的form参数绑定出错解析器
 * 
 * @author elliott
 * @version $Id: SimpleBindingErrorProcessor.java, v 1.0 2013-12-23 下午11:32:15 elliott Exp $
 */
public class SimpleBindingErrorProcessor implements BindingErrorProcessor {

	/** 
	 * @see org.springframework.validation.BindingErrorProcessor#processMissingFieldError(java.lang.String, org.springframework.validation.BindingResult)
	 */
	@Override
	public void processMissingFieldError(String missingField,BindingResult bindingResult) {
		bindingResult.rejectValue(missingField, missingField,"不能为空");
	}

	/** 
	 * @see org.springframework.validation.BindingErrorProcessor#processPropertyAccessException(org.springframework.beans.PropertyAccessException, org.springframework.validation.BindingResult)
	 */
	@Override
	public void processPropertyAccessException(PropertyAccessException ex,
			BindingResult bindingResult) {
		bindingResult.rejectValue(ex.getPropertyName(), ex.getPropertyName(), "格式错误");
	}

}
