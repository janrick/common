/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.web.mvc.convert;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.zjzmjr.common.util.DateUtil;

/**
 * 日期格式转换
 * 
 * @author elliott
 * @version $Id: DateConverter.java, v 1.0 2014-3-1 下午9:41:13 elliott Exp $
 */
public class DateConverter implements Converter<String, Date> {
	private List<String> patterns;
	
	@Override
	public Date convert(String source) {
		if(StringUtils.isNotBlank(source)){
			Date date =  DateUtil.parseDate(StringUtils.trimToEmpty(source), patterns==null||patterns.isEmpty()?new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"}:patterns.toArray(new String[patterns.size()]));
			if(date==null){
				throw new IllegalArgumentException("日期["+source+"]格式错误");
			}
			return date;
		}
		return null;
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}

}
