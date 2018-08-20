/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.bind;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.validation.BindingErrorProcessor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.zjzmjr.common.util.DateUtil;

/**
 * 简单的web参数解析器
 * 
 * <p>该类实现了对于{@link Date}类型的转换</p>
 * 
 * @author elliott
 * @version $Id: SimpleWebBindingInitializer.java, v 1.0 2013-12-23 下午11:16:19 elliott Exp $
 */
public class SimpleWebBindingInitializer implements WebBindingInitializer {
	private final String[] datePattern =  new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"};
	
	private BindingErrorProcessor bindingErrorProcessor;
	/** 
	 * @see org.springframework.web.bind.support.WebBindingInitializer#initBinder(org.springframework.web.bind.WebDataBinder, org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		if(bindingErrorProcessor!=null){
			binder.setBindingErrorProcessor(bindingErrorProcessor);
		}
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){

			@Override
			public String getAsText() {
				return getValue()==null?"":DateUtil.format((Date)getValue(), "yyyy-MM-dd");
			}

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if(StringUtils.isNotBlank(text)){
					 Date date = null;
					 try {
						date = DateUtils.parseDateStrictly(text, datePattern);
					} catch (ParseException e) {
						throw new IllegalArgumentException("日期格式错误");
					}
					setValue(date);
				}
			}
			
		});
	}
	public BindingErrorProcessor getBindingErrorProcessor() {
		return bindingErrorProcessor;
	}
	public void setBindingErrorProcessor(BindingErrorProcessor bindingErrorProcessor) {
		this.bindingErrorProcessor = bindingErrorProcessor;
	}
	
}
